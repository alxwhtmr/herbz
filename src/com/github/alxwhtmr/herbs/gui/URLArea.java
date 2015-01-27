package com.github.alxwhtmr.herbs.gui;

import com.github.alxwhtmr.herbs.Constants;

import javax.swing.*;


/**
 * The {@code URLArea} class contains area for user's url-links
 *
 * @since 24.12.2014
 */
public class URLArea extends JPanel {
    private JTextArea urlArea;

    public URLArea() {
        urlArea = new JTextArea(15, 70);
        urlArea.setEditable(true);
        urlArea.setToolTipText(Constants.GUI.URL_TIP);
        JScrollPane scrollPane = new JScrollPane(urlArea);
//        urlArea.append("http://www.iherb.com/Rainbow-Light-Gummy-Vitamin-C-Slices-Tangy-Orange-Flavor-90-Gummies/40005\n");
        urlArea.append("http://www.iherb.com/Nature-s-Way-Alive-Whole-Food-Energizer-Multi-Vitamin-Max-Potency-No-Iron-Added-180-Tablets/4120\n");
        urlArea.append("http://www.iherb.com/Jarrow-Formulas-Multi-1-to-3-with-Lutein-Iron-Free-100-Tablets/245\n");
        urlArea.append("http://www.iherb.com/Now-Foods-Special-Two-Multi-Vitamin-90-Tablets/39668\n");
//        urlArea.append("http://www.iherb.com/FutureBiotics-Vitomega-Women-90-Veggie-Tabs/7297\n");
        add(scrollPane);
    }

    /**
     * Returns {@code String} with all the text from {@code URLArea} object
     * @return the {@code String} that contains links
     */
    public String getText() {
        return urlArea.getText();
    }

    /**
     * Erases url-area
     */
    public void empty() {
        urlArea.setText(null);
    }
}
