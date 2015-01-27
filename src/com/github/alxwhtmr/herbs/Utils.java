package com.github.alxwhtmr.herbs;

import java.util.Date;
import com.github.alxwhtmr.herbs.Utils;


/**
 * The {@code Utils} class represents utilities
 * methods that are widely used by program modules
 *
 * @since 25.12.2014
 */
public class Utils {
    private static final boolean LOGS = true;

    public static void logNewObj(Object o) {
        if (LOGS == true) {
            System.out.println(new Date() + " : NEW OBJECT : " + o.getClass());
        }
    }

    public static void log(Object o) {
        if (LOGS == true) {
            System.out.println("[LOG] " + o);
        }
    }

    public static String extractStr(String str, String begin, String end) {
        StringBuffer tmp = new StringBuffer(str.substring(str.indexOf(begin)+begin.length()));
        System.out.println("tmp="+tmp);
        String result = tmp.substring(0, tmp.indexOf(end));
        return result;
    }

    public static String extractStr(String str, String begin) {
        return str.substring(str.indexOf(begin)).replaceAll(Constants.Regex.HTML_SPECIAL, "'");
    }

    public static int countCharSequence(String str, String seq) {
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
           if (str.charAt(i) == seq.charAt(0)) {
               int j = i;
               StringBuffer buf = new StringBuffer();
               for (int k = 0; k < seq.length(); k++) {
                   buf.append(str.charAt(j++));
               }
               if (buf.toString().equals(seq)) {
                   count++;
               }
           }
        }

        return count;
    }

    // wordNumber - word number from the column
    public static String extractFromHTMLTags(String str, String openTag, String closeTag, int groupIndex, int wordNumber) {
        String extracted = null;
        int openTagIndex = 0;
        int closeTagIndex = 0;
        int beginIndex = 0;
        int endIndex = 0;

        for (int currentGroup = 0; currentGroup < groupIndex; currentGroup++) {
            openTagIndex = getSequenceIndex(str, openTag, openTagIndex + openTag.length());
            closeTagIndex = getSequenceIndex(str, closeTag, closeTagIndex + closeTag.length());
        }
        beginIndex = openTagIndex + openTag.length();
        endIndex = closeTagIndex;

        StringBuffer buf = new StringBuffer();
        int index = beginIndex;
        int wordsCount = 0;
        while (index < endIndex) {
            if (wordNumber == 0) {
                buf.append(str.charAt(index));
                index++;
                continue;
            }
            if (wordsCount == wordNumber-1) {
                buf.append(str.charAt(index));
            }
            if (str.charAt(index) == ' ') {
                wordsCount++;
            }
            index++;
        }

        extracted = buf.toString().trim();

        if (extracted.contains("</strong>")) {
//            extracted = deleteSubstringAfterMark(extracted, "</strong>");
            extracted = getSubstringBeforeMark(extracted, "</strong>");
        }
        extracted = extracted.replaceAll("<br />|<sub>|</sub>|<strong>|\\*", "");
        return extracted;
    }

    public static String getSubstringBeforeMark(String str, String mark) {
        StringBuffer buffer = new StringBuffer();
        int index = str.indexOf(mark);

        for (int i = 0; i < index; i++) {
            buffer.append(str.charAt(i));
        }
        return buffer.toString();
    }

    public static String deleteSubstringBeforeMark(String str, String mark) {
        StringBuffer buffer = new StringBuffer();
        int index = str.indexOf(mark);

        for (int i = 0; i < index; i++) {
            buffer.append(str.charAt(i));
        }
        String newString = str.replaceAll(buffer.toString(), "");

        return newString;
    }

    private static String deleteSubstringAfterMark(String str, String mark) {
        StringBuffer buffer = new StringBuffer();
        int index = str.indexOf(mark);
        Utils.log(mark + " index=" + index);

        while (index < str.length()) {
            buffer.append(str.charAt(index++));
        }
        Utils.log("buffer=" + buffer);
        String newString = str.replaceAll(buffer.toString(), "");
        Utils.log("newString=" + newString);
        return newString;
    }

    public static int getSequenceIndex(String str, String seq, int beginIndex) {
        int index = 0;

        for (int i = beginIndex; i < str.length(); i++) {
            if (str.charAt(i) == seq.charAt(0)) {
                int j = i;
                StringBuffer buf = new StringBuffer();
                for (int k = 0; k < seq.length(); k++) {
                    buf.append(str.charAt(j++));
                }
                if (buf.toString().equals(seq)) {
                    return i;
                }
            }

        }
        return index;
    }
}