/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author q
 */
public class StringUtils {

    public static String turnStr(String str) {
        //str="<html>"+str;
        while (str.indexOf("\n") != -1) {
            str = str.substring(0, str.indexOf("\n")) + "\12" + str.substring(str.indexOf("\n") + 1);
        }
//        while (str.indexOf(" ") != -1) {
//            str = str.substring(0, str.indexOf(" ")) + "" + str.substring(str.indexOf("") + 1);
//        }
        //str=str+"</html>";
        return str;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.replaceBlank("just do it!"));
    }
    /*
     * -----------------------------------
     *
     * Stupid way: String s = "you want to remove the string";
     *
     * 1.Remove the space: s = s.replace does ('\ \ s','');
     *
     * 2.Removal of the carriage return: s = s.replace does ('\ n','');
     *
     * This can also remove the spaces and carriage returns to the other according to this.
     *
     * Note: \n enter (\ u000a) \t horizontal tab (\u0009) \s spaces (\ u0008) \r newline (\ u000d)
     */
}
