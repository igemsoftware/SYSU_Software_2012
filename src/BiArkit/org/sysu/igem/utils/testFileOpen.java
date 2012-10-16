/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.io.File;

/**
 *
 * @author q
 */
public class testFileOpen {

    public static void main(String[] args){
        testAllList(new File("biobreak"));
    }
    
    public static void testAllList(File allList) {
        // 
        File[] fileArray = allList.listFiles();
        if (null == fileArray) {
            return;
        }
        for (int i = 0; i < fileArray.length; i++) {
            if (fileArray[i].isDirectory()) {
                System.out.println("Folder: " + fileArray[i].getAbsolutePath());
                testAllList(fileArray[i].getAbsoluteFile());

            } else if (fileArray[i].isFile()) {

                System.out.println("Folder: " + fileArray[i].getAbsolutePath());
            } else {
                System.out.println("File name-->other file");
            }
        }
    }
}