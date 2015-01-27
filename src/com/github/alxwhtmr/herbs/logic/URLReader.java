package com.github.alxwhtmr.herbs.logic;

import com.github.alxwhtmr.herbs.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;



/**
 * The {@code URLReader} class represents class
 * to get the content of the specified {@code URL} resource
 *
 * @since 25.12.2014
 */
public class URLReader {
    private String urlString;

    public URLReader(String url) {
        Utils.logNewObj(this);
        urlString = url;
    }

    public String getHtml() throws IOException {
        StringBuffer b = new StringBuffer();
        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            b.append(inputLine);
        }
        in.close();

        return b.toString();
    }
}
