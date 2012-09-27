/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.GCircle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that can read Signal File automatically(when created or setFilePath)
 * @author Guo Jiexin
 */
public class SignalsRead {

    private String filePath = null;
    private List<String> ID_REF = new ArrayList<String>();
    private List<Double> value = new ArrayList<Double>();

    /**
     * Automatically read the signal file of path
     * @param path
     * @throws FileNotFoundException
     * @throws IOException 
     */
    SignalsRead(String path) throws FileNotFoundException, IOException {
        if(!this.readSignalsFile(path))
            System.out.println("reading signal file error!file format error?");
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) throws FileNotFoundException, IOException {
        this.filePath = filePath;
        if(!this.readSignalsFile(filePath))
            System.out.println("reading signal file error!file format error?");
    }

    /**
     * To read signal file
     * @param path
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private boolean readSignalsFile(String path) throws FileNotFoundException, IOException {
        boolean readSuccess = false;
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return readSuccess;
        }
        this.filePath = path;
        if (!this.ID_REF.isEmpty()) {
            this.ID_REF.clear();
        }
        if (!this.value.isEmpty()) {
            this.value.clear();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        temp = br.readLine();
        String[] strs = null;
        while (temp != null) {
            strs = temp.split("\t");
            if (strs[0].equals("ID_REF") && strs[1].equals("VALUE")) {
                temp=br.readLine();
                readSuccess=this.startReadSignals(br);
            }
            temp = br.readLine();
        }
        
        return readSuccess;
    }

    private boolean startReadSignals(BufferedReader br) throws IOException {
        boolean readSuccess = false;
        String temp = br.readLine();
        String[] strs = null;
        while (temp != null) {
            strs = temp.split("\t");
            if (strs.length == 5) {
                if (!readSuccess) {
                    readSuccess = true;
                }
                this.ID_REF.add(strs[0]);
                this.value.add(Double.parseDouble(strs[1]));
            }
            temp = br.readLine();
        }
        //br.close();
        return readSuccess;
    }

    /**
     * Get signal value by id
     * @param id
     * @return 
     */
    public double getSignalById(String id) {
        int i = this.ID_REF.indexOf(id);
        if (i >= 0) {
            return this.value.get(i);
        } else {
            return 0;
        }
    }

    /**
     * Only for testting
     * @param args
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException {
        SignalsRead read = new SignalsRead("circos\\GSM510401.txt");
        //System.out.println(read.ID_REF.size());
//        read.readSignalsFile("circos\\GSM510401.txt");
//        System.out.println(read.getSignalById("c4728"));
    }
}
