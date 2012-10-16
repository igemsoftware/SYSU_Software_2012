/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author q
 */
public class ReadFileByLine {
    String fileName;
    List<String> list;
    int size;
    String content;
    String header;
    
    public ReadFileByLine(String fileName){
        size = 0;
        this.content = "";
        this.fileName = fileName;
    }
    
    public int getSize(){
        return size;
    }
    
    public String getHeader() throws Exception{
        if(header==null)
            throw new Exception();
        return header;
    }
    
    public String getContent() throws Exception{
        if(content==null)
            throw new Exception();
        return content;
    }
    
    
    public List<String> readFileByLines() {
        list = new ArrayList();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println(ReadFileByLine.class + ": Read By Line：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            
            // Pre-reading one line, but it is unclear whether it is useful. So it is counted to the list in
            header = reader.readLine();     //Cleared the first line
            
            while ((tempString = reader.readLine()) != null) {
                size += tempString.length();    //Calculate the size
                list.add(tempString);
                //content += tempString;
                //System.out.println(content);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        //System.out.println(size);
        return list;
    }
    
//    public static void main(String[] args) throws Exception
//    {
//        ReadFileByLine file = new ReadFileByLine("file/genome.fasta");
//        System.out.println(file.readFileByLines());
//        /*
//        for(String l : list)
//        {
//            System.out.println(l);
//        }
//        */
//        
//    }
//    
    /* Useage
        //
        try {
            obj = new Object[1][readFileList.size()];
            head = new String[readFileList.size()];
        } catch (Exception ex) {
            Logger.getLogger(TestBrowserView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int i = 0;
        for(String r : readFileList){
            obj[0][i] = r;
            head[i++] = Integer.toString(i);
        }
     */
}
