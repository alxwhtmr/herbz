package com.github.alxwhtmr.herbs.logic;


import com.github.alxwhtmr.herbs.Constants;
import com.github.alxwhtmr.herbs.Utils;
import com.github.alxwhtmr.herbzdbdatamanager.DataManager;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The {@code Reporter} class that
 * builds the html report that contains
 * a comparable table with goods that
 * the user have entered
 *
 * @since 25.12.2014
 */
public class Reporter {
    private List<String> links;
    private ArrayList<Goods> goodsArray;
    private int tableWidth = 600;
    private SubstanceRows substanceRows;
    private DataManager dataManager;


    public Reporter(String links) {
        Utils.logNewObj(this);
        this.links = Arrays.asList(links.split("\n"));
        dataManager = new com.github.alxwhtmr.herbzdbdatamanager.DataManager();
    }

    public boolean createReport() {
        goodsArray = new ArrayList<Goods>();
        substanceRows = new SubstanceRows();
        for (String link : links) {
            HTMLParser htmlParser = new HTMLParser();
            Goods goods = htmlParser.parseGoodsFromHTML(link);
            goodsArray.add(goods);
            substanceRows.appendRows(goods.getSubstanceArrayList());

            System.out.println("Reporter: " + goods);
        }
        String styleSheetContent = makeStyleSheet();
        writeToFile(Constants.Files.STYLESHEET, styleSheetContent);
        String reportContent = makeReportContent();
        writeToFile(Constants.Files.REPORT_FILE, reportContent);
        return true;
    }


    private String makeStyleSheet() {
        StringBuffer styleSheet = new StringBuffer();

        styleSheet.append("table th,td {\nwidth:" + Constants.HTML_CSS.CELL_WIDTH + "px;\n" +
                "font-family: \"Verdana\";\n}\n");
        styleSheet.append("th {\ntext-align: center;\n}\n");
        styleSheet.append("." + Constants.HTML_CSS.SUBST_TD_CLASS + " {\nfont-family: \"Verdana\";\n" +
                "width:" + Constants.HTML_CSS.SUBST_TD_WIDTH + "px;\n" +
                "font-size:" + Constants.HTML_CSS.SUBST_FONT_SIZE + ";\n}\n");
        styleSheet.append("div.pop-up {\n" +
                "  display: none;\n" +
                "  position: absolute;\n" +
                "  width: 280px;\n" +
                "  padding: 10px;\n" +
                "  background: #eeeeee;\n" +
                "  color: #000000;\n" +
                "  border: 1px solid #1a1a1a;\n" +
                "  font-size: 90%;\n" +
                "}\n\n");

        return styleSheet.toString();
    }

    private String makeReportContent() {
        StringBuffer report = new StringBuffer();

        report.append(makeReportHead());
        report.append(makeReportTableHead());
        report.append(makeReportTableBody());
        report.append(makeReportEnd());

        return report.toString();
    }

    private String makeReportHead() {
        StringBuffer reportHead = new StringBuffer();

        reportHead.append("<!DOCTYPE html>\n");
        reportHead.append("<html>\n");
        reportHead.append("<head lang=\"en\">\n");
        reportHead.append("<meta charset=\"UTF-8\">\n");
        reportHead.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.js\"></script>");
        reportHead.append("<link rel=\"stylesheet\" href=" + Constants.Files.BOOTSTRAP_CSS + ">\n");
        reportHead.append("<script src=" + Constants.Files.BOOTSTRAP + "></script>\n");

        reportHead.append("<script src=\"popup.js\"></script>");
        reportHead.append("<link rel=\"stylesheet\" href=" + Constants.Files.STYLESHEET + ">\n");
        reportHead.append("<title></title>\n");
        reportHead.append("</head>\n");
        reportHead.append("<body>\n");

        return reportHead.toString();
    }

    private String makeReportTableHead() {
        StringBuffer reportTableHead = new StringBuffer();

        tableWidth = Constants.HTML_CSS.SUBST_TD_WIDTH + Constants.HTML_CSS.CELL_WIDTH * (goodsArray.size() * 2);
        reportTableHead.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=" + tableWidth + "px>\n");
        reportTableHead.append("<tr>\n");
        reportTableHead.append("<td>\n");
        reportTableHead.append("<table cellspacing=\"0\" cellpadding=\"1\" border=\"1\" width=" + tableWidth + "px>\n");
        reportTableHead.append("<tr>\n");


        reportTableHead.append("<td class=" + Constants.HTML_CSS.SUBST_TD_CLASS + "></td>\n");
        for (Goods goods : goodsArray) {
            reportTableHead.append("<th colspan=\"2\"><font size=4><a href=" + goods.getUrl() + " target=\"_blank\">" + goods.getName() + "</a></font></th>\n");
        }
        reportTableHead.append("</tr>\n");


        reportTableHead.append("<td class=" + Constants.HTML_CSS.SUBST_TD_CLASS + "></td>\n");
        for (Goods goods : goodsArray) {
            reportTableHead.append("<th><font size=4>$" + goods.getPrice() + "</font></th><th><small>" + goods.getServingSize() + " " +
                    goods.getServingSizeName() + "<br />" + goods.getServingsPerContainer() + " <a href=\"\" title=\"Servings Per Container\">SPC</a></small></th>");
        }
        reportTableHead.append("</tr>\n");


        reportTableHead.append("<tr>\n");
        reportTableHead.append("<td class=" + Constants.HTML_CSS.SUBST_TD_CLASS + "><strong><font size=4>Substance</font></strong></td>\n");
        for (int i = 0; i < goodsArray.size(); i++) {
            reportTableHead.append("<th>" + Constants.HTML_CSS.AMOUNT_PER_SERVING +
                    "</th><th>" + Constants.HTML_CSS.DAILY_VALUE + "</th>\n");
        }
        reportTableHead.append("</tr>\n");


        reportTableHead.append("</table>\n");
        reportTableHead.append("</td>\n");
        reportTableHead.append("</tr>\n");

        return reportTableHead.toString();
    }

    private String makeReportTableBody() {
        StringBuffer reportTableBody = new StringBuffer();
        int realWidth = tableWidth + 25;

        reportTableBody.append("<tr>\n");
        reportTableBody.append("<td>\n");
        reportTableBody.append("<div style=\"width:" + realWidth + "px; height:100vh; overflow:auto;\">\n");
        reportTableBody.append("<table cellspacing=\"0\" cellpadding=\"1\" border=\"1\" width=" + tableWidth + "px>\n");


        int colorist = 2;
        for (String substanceName : substanceRows.getAllRows()) {
            ArrayList<Substance> s = new ArrayList<Substance>();

            for (Goods goods : goodsArray) {
                s.add(goods.getSubstanceByName(substanceName));
            }
            int max = 0;
            for (int i = 0; i < goodsArray.size(); i++) {
                double verifiableAmount = goodsArray.get(i).getSubstanceByName(s.get(i).getName()).getAmountPerServing();
                double maxAmount = goodsArray.get(max).getSubstanceByName(s.get(max).getName()).getAmountPerServing();
                if (verifiableAmount > maxAmount) {
                    max = i;
                }
            }

            String bgcolor;
            if (colorist % 2 == 0) {
                bgcolor = "#D0D0D0";
            } else {
                bgcolor = "#FFFFFF";
            }

            try {
                String description = dataManager.getSubstDescription(substanceName);
                if (description != null) {
                    reportTableBody.append("\n<tr>\n<td class=\""+Constants.HTML_CSS.SUBST_TD_CLASS+"\" bgcolor=" + bgcolor +
                            "><font size=3><strong><a href=\"#\" id=\"trigger\" itemid=\""+substanceName.trim().replaceAll(" ", "_")+
                            "\">" + substanceName + "</a></strong></font>");

                    reportTableBody.append("\n<div class=\"pop-up\" id=\"" + substanceName.trim().replaceAll(" ", "_") + "\">\n" +
                            "      <p>" + description+ "</p>\n" +
                            "    </div></td>\n");
                } else {
                    reportTableBody.append("\n<tr>\n<td class=\""+Constants.HTML_CSS.SUBST_TD_CLASS+"\" bgcolor=" + bgcolor +
                            "><font size=3><strong>" + substanceName + "</strong></font></td>\n");
                }
            } catch (SQLException e) {
                Utils.log(e);
            }

            for (int i = 0; i < goodsArray.size(); i++) {
                String innerbgcolor = bgcolor;
                Goods goods = goodsArray.get(i);
                Substance substance = goods.getSubstanceByName(substanceName);
                String amountPerServing = substance.getAmountPerServing() > 0 ? substance.getAmountPerServing() + " " + substance.getMeasurement() : "*";
                String dailyValue = substance.getDailyValue() > 0 ? substance.getDailyValue() + "%" : "*";
                if (i == max && !substance.getName().equalsIgnoreCase("Calories") && !substance.getName().equalsIgnoreCase("Total Carbohydrate")) {
                    innerbgcolor = "#A9E2F3";
                }
                reportTableBody.append("<td bgcolor=" + innerbgcolor + ">" + amountPerServing + "</td><td bgcolor=" + innerbgcolor + ">" + dailyValue + "</td>");
            }
            colorist++;
            reportTableBody.append("\n</tr>\n");
        }

        reportTableBody.append("</table>\n");
        reportTableBody.append("</div>\n");
        reportTableBody.append("</td>\n");
        reportTableBody.append("</tr>\n");
        reportTableBody.append("</table>\n");

        return reportTableBody.toString();
    }

    private String makeReportEnd() {
        StringBuffer reportEnd = new StringBuffer();
        reportEnd.append("\n</body>\n</html>");

        return reportEnd.toString();
    }

    public void writeToFile(String fileName, String data) {
        Path fileP = Paths.get(fileName);
        try {
            java.nio.file.Files.write(fileP, data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
