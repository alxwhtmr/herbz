package com.github.alxwhtmr.herbs.logic;

import com.github.alxwhtmr.herbs.Utils;

import java.util.ArrayList;


/**
 * The {@code Goods} class represents class
 * that contains goods from parsed html page
 *
 * @since 25.12.2014
 */
public class Goods {
    private String url = "#";
    private String name = "unknown goods";
    private double price = 99.99;
    private int servingSize = 2;
    private String servingSizeName = "caps";
    private int servingsPerContainer = 60;
    private ArrayList<Substance> substanceArrayList = new ArrayList<Substance>();


    public Goods(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s | price=$%.2f, servingSize=%d %s | perContainer=%d\n", name, price, servingSize, servingSizeName, servingsPerContainer);
    }

    public Substance getSubstanceByName(String substanceName) {
        Substance substance = new Substance();

        for (Substance s : substanceArrayList) {
            if (s.getName().equalsIgnoreCase(substanceName)) {
                substance = s;
                break;
            }
        }

        return substance;
    }

    public int getServingSize() {
        return servingSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingSizeName() {
        return servingSizeName;
    }

    public void setServingSizeName(String servingSizeName) {
        this.servingSizeName = servingSizeName;
    }

    public int getServingsPerContainer() {
        return servingsPerContainer;
    }

    public void setServingsPerContainer(int servingsPerContainer) {
        this.servingsPerContainer = servingsPerContainer;
    }

    public ArrayList<Substance> getSubstanceArrayList() {
        return substanceArrayList;
    }

    public void setSubstanceArrayList(String substancesDetail) {
        substancesDetail = Utils.deleteSubstringBeforeMark(substancesDetail, "<tr>");
        ArrayList<String> substancesArray = fillSubstancesArray(substancesDetail);

        for (String s : substancesArray) {
            Substance substance = new Substance();
            String name = Utils.extractFromHTMLTags(s, "<td>", "</td>", 1, 0).trim();
            name = name.replaceAll("\\(.*", "").trim();
            double amountPerServing = 0.0;
            try {
                amountPerServing = Double.parseDouble(Utils.extractFromHTMLTags(s, "<td>", "</td>", 2, 1).replaceAll(",|&nbsp;", ""));
            } catch (NumberFormatException e) {
                System.out.println("#Error amountPerServing " + e);
            }
            String measurement = Utils.extractFromHTMLTags(s, "<td>", "</td>", 2, 2).trim();
            int dailyValue = 0;
            try {
                dailyValue = Integer.parseInt(Utils.extractFromHTMLTags(s, "<td>", "</td>", 3, 1).replaceAll("[%&;,a-z\\*]", ""));
            } catch (NumberFormatException e) {
//                System.out.println(e);
            }
            substance.setName(name);
            substance.setAmountPerServing(amountPerServing);
            substance.setMeasurement(measurement);
            substance.setDailyValue(dailyValue);
            this.substanceArrayList.add(substance);
        }
    }

    private ArrayList<String> fillSubstancesArray(String substancesDetail) {
        substancesDetail = substancesDetail.replaceAll("</tr>|</table>|</tbody>", "\n");
        ArrayList<String> substancesArray = new ArrayList<String>();
        int index = 0;

        for (int i = 0; i < substancesDetail.length(); i++) {
            StringBuffer buf = new StringBuffer();
            while (substancesDetail.charAt(i) != '\n') {
                if (i == substancesDetail.length() - 1) break;
                buf.append(substancesDetail.charAt(i++));
            }
            if (Utils.countCharSequence(buf.toString(), "<td") == 3) {
                substancesArray.add(index++, buf.toString().replaceAll(" +", " ").trim());
            }
        }

        return substancesArray;
    }
}
