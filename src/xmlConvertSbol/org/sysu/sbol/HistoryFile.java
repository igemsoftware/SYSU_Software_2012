/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.sbol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that can set path to a history file and reload it
 * @author Guo Jiexin
 */
public class HistoryFile {
    /**
     * To get path from File(path)
     * @param path
     * @return
     * @throws IOException 
     */
    public static String getPathFromHistoryFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return System.getProperty("user.dir");
        } else {
            BufferedReader br = null;
            String temp = null;
            try {
                br = new BufferedReader(new FileReader(file));
                temp = br.readLine();
                System.out.println(temp);
                if (temp == null||!(new File(temp).exists())) {
                    br.close();
                    return System.getProperty("user.dir");
                } else {
                    br.close();
                    return temp;
                }
            } catch (IOException ex) {
                Logger.getLogger(HistoryFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            br.close();
            return System.getProperty("user.dir");
        }
    }
    /**
     * To save a userPath to the history file(filePath)
     * @param filePath
     * @param userPath
     * @throws IOException 
     */
    public static void setPathToHistoryFile(String userPath, String filePath) throws IOException {
        File file = new File(filePath);
        FileWriter fw = new FileWriter(file);
        fw.write(userPath);
        fw.close();
    }
}
