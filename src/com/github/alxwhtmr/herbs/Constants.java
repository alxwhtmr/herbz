package com.github.alxwhtmr.herbs;


/**
 * The {@code Constants} contains all the constants that are
 * used in a program
 *
 * @since 24.12.2014
 */
public class Constants {
    public class GUI {
        public static final int MAIN_WINDOW_WIDTH = 500;
        public static final int MAIN_WINDOW_HEIGHT = 100;
        public static final int DIFF = 100;
        public static final int TEXT_AREA_WIDTH = MAIN_WINDOW_WIDTH - DIFF;
        public static final String URL_TIP = "Insert here your links for stuff (up to 5) from iherb.com";
    }

    public class Regex {
        public static final String HTML_SPECIAL = "&#[0-9]*;";
        public static final String PRICE = "Our Price|Наша цена";
        public static final String DAILY_VALUE = "%.?DV[^\\)].?|%.?Daily Value/*";
        public static final String SERVINGS = "Serving Per Container";
        public static final String SERVINGS_AND_TAG = "Servings Per Container: </strong>";
        public static final String SERVING_SIZE = "Serving Size: </strong>";
        public static final String VITAMIN_B6 = "B-6";
        public static final String VITAMIN_B12 = "B-12";
    }

    public class Replacements {
        public static final String PRICE = "our-price b fLeft";
        public static final String DAILY_VALUE_MARK_TO_REPLACE = "%?DV";
        public static final String DAILY_VALUE = "% Daily Value";
        public static final String SERVINGS = "Servings Per Container";
        public static final String SERVINGS_AND_TAG = "Servings Per Container:</strong>";
        public static final String SERVING_SIZE = "Serving Size:</strong>";
        public static final String VITAMIN_B6 = "B6";
        public static final String VITAMIN_B12 = "B12";
    }

    public class Files {
        public static final String REPORT_FILE = "report.html";
        public static final String LOAD_IMAGE = "ajax-loader.gif";
        public static final String IHERB_LOGO = "iherb.png";
        public static final String STYLESHEET = "style.css";
        public static final String BOOTSTRAP = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js";
        public static final String BOOTSTRAP_CSS = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css";
    }

    public class HTML_CSS {
        public static final int CELL_WIDTH = 110;
        public static final int SUBST_TD_WIDTH = 185;
        public static final String SUBST_TD_CLASS = "substance";
        public static final String AMOUNT_PER_SERVING = "Amount Per Serving";
        public static final String DAILY_VALUE = "Daily Value %";
        public static final String SUBST_FONT_SIZE = "medium";
    }
}
