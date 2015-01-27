package com.github.alxwhtmr.herbs.logic;

import com.github.alxwhtmr.herbs.Constants;
import com.github.alxwhtmr.herbs.Utils;

import java.io.IOException;
import java.util.Arrays;


/**
 * A {@code HTMLParser} represents class to parse html content
 *
 * @since 25.12.2014
 */
public class HTMLParser {
    private URLReader urlReader;
    private Goods goods = null;

    public HTMLParser() {
        Utils.logNewObj(this);
    }

    /**
     * Returns the {@code String} that contains
     * content from specified url, which is passed
     * as a {@code String} parameter
     *
     * @param url a {@code String} that contains a url link
     * @return the {@code String} value that contains
     * html page content
     */
    public String getHtml(String url) {
        String content = "nothing";
        urlReader = new URLReader(url);
        try {
            content = urlReader.getHtml();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public Goods parseGoodsFromHTML(String url) {
        String html = getHtml(url);
        System.out.println(url);
        try {
            // Some necessary replacements
            html = html.replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
            html = html.replaceAll("\"", "");
            html = html.replaceAll(" <", "<").replaceAll("> ", ">");
            html = html.replaceAll(Constants.Regex.SERVINGS, Constants.Replacements.SERVINGS);
            html = html.replaceAll(Constants.Regex.PRICE, Constants.Replacements.PRICE);
            html = html.replaceAll(Constants.Regex.SERVING_SIZE, Constants.Replacements.SERVING_SIZE);
            html = html.replaceAll(Constants.Regex.DAILY_VALUE, Constants.Replacements.DAILY_VALUE);
            html = html.replaceAll(Constants.Regex.VITAMIN_B6, Constants.Replacements.VITAMIN_B6);
            html = html.replaceAll(Constants.Regex.VITAMIN_B12, Constants.Replacements.VITAMIN_B12);
            html = html.replaceAll("<tr align=left valign=top>", "<tr>");

            this.setGoods(url, html);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return goods;
    }

    private void setGoods(String url, String html) {
        goods = new Goods(url);
        String name = Utils.extractStr(html, "<meta name=description content=", "/>");
        Utils.log("html=" + html);
        Utils.log("name=" + name);

        goods.setName(name);

//        String price = Utils.extractStr(html, Constants.Replacements.PRICE, "</span>");
//        String price = Utils.extractStr(html, Constants.Replacements.PRICE, "</span>");
        Utils.log("html=" + html);
        String price = html.substring(html.indexOf("<div class=our-price b fLeft>") + "<div class=our-price b fLeft>".length(), html.length()).trim();
        price = price.substring(0, price.indexOf("</div>"));
        Utils.log("[1]price=" + price);
//        price = price.substring(price.indexOf("$"), price.length()).trim();
        price = price.replaceAll("\\$", "");
        Utils.log("[2]price=" + price);
//        price = Utils.extractStr(price, price, "</div>");
//        Utils.log("[3]price="+price);
        goods.setPrice(Double.parseDouble(price));

        String prodOverviewDetail = Utils.extractStr(html, "Supplement Facts", "</div>");
        Utils.log("prodOverviewDetail=" + prodOverviewDetail);
        String ss[] = Utils.extractStr(prodOverviewDetail, "Serving Size:</strong>", "</td>").trim().split(" ");
        goods.setServingSize(Integer.parseInt(ss[0]));
        goods.setServingSizeName(ss[1]);
        prodOverviewDetail = prodOverviewDetail.replaceAll(Constants.Regex.SERVINGS_AND_TAG, Constants.Replacements.SERVINGS_AND_TAG);
        Utils.log("prodOverviewDetail=" + prodOverviewDetail);
        String spc = Utils.extractStr(prodOverviewDetail, Constants.Replacements.SERVINGS_AND_TAG, "</td>");
        goods.setServingsPerContainer(Integer.parseInt(spc.trim()));

        String substancesDetail = Utils.extractStr(prodOverviewDetail, Constants.Replacements.DAILY_VALUE);

        goods.setSubstanceArrayList(substancesDetail);
    }
}
