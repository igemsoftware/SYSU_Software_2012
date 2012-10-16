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
 * A class that can read file in soft mode
 * @author Guo Jiexin
 */
public class SoftFileRead {
    /**
     * Soft file 's path
     */
    private String filePath = null;
    private List<String> ID = new ArrayList<String>();
    private List<String> geneTitle = new ArrayList<String>();
    private List<String> geneSymbol = new ArrayList<String>();
    private SignalsRead signalsread = null;

    public SignalsRead getSignalsRead() {
        return signalsread;
    }

    /**
     * Clear all varibles in the class
     */
    public void clearAll() {
        this.filePath = null;
        this.ID.clear();
        this.geneTitle.clear();
        this.geneSymbol.clear();
        this.signalsread = null;
    }

    /**
     * To read a Soft Mode File
     * @param path
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public SoftFileRead(String path) throws FileNotFoundException, IOException {
        this.filePath = path;
        if (!this.startReadingFile()) {
            System.out.println("reading soft file error!");
        }
    }

    public void readSignalsFile(String path) throws FileNotFoundException, IOException {
        this.signalsread = new SignalsRead(path);
    }

    /**
     * Get genome's place by its ID
     * @param id
     * @return the place of genome in the file's platform table's all genomes
     */
    private int getGenePlaceByID(String id) {
        for(int i=0;i<this.geneTitle.size();i++)
            if(this.ID.get(i).toLowerCase().indexOf(id.toLowerCase())>=0)
                return i;
        return -1;
    }

    /**
     * Get genome's place by its Title
     * @param geneTitle
     * @return 
     */
    private int getGenePlaceByGeneTitle(String geneTitle) {
        for(int i=0;i<this.geneTitle.size();i++)
        {
            String[]temp=this.geneTitle.get(i).split("///");
            for(int j=0;j<temp.length;j++)
                if(temp[j].toLowerCase().equals(geneTitle.toLowerCase()))
                    return i;
        }
        return -1;
    }

    /**
     * Get genome's place by its Symbol
     * @param symbol
     * @return 
     */
    private int getGenePlaceByGeneSymbol(String symbol) {
        for(int i=0;i<this.geneSymbol.size();i++)
            if(this.geneSymbol.get(i).toLowerCase().indexOf(symbol.toLowerCase())>=0)
                return i;
        return -1;
    }

    /**
     * Get genome's signalValue by its Symbol
     * @param id
     * @return 
     */
    public double getSignalById(String id) {
        if (this.signalsread == null) {
            System.out.println("You have not read Signals Yet!");
            return 0;
        }
        return this.signalsread.getSignalById(id);//this.signalsread.getSignalById(id);
    }

    public double getSignalByGeneTitle(String title) {
        if (this.signalsread == null) {
            System.out.println("You have not read Signals Yet!");
            return 0;
        }
        int i = this.getGenePlaceByGeneTitle(title);
        if (i < 0) {
            return 0;
        }
        return this.signalsread.getSignalById(this.ID.get(i));
    }

    public double getSignalByGeneSymbol(String symbol) {
        if (this.signalsread == null) {
            System.out.println("You have not read Signals Yet!");
            return 0;
        }
        int i = this.getGenePlaceByGeneSymbol(symbol);
        if (i < 0) {
            return 0;
        }
        return this.signalsread.getSignalById(this.ID.get(i));
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * Once setfilepath it start reading automatically
     * @param filePath
     * @return  if it reads successfully
     * @throws IOException 
     */
    public boolean setFilePath(String filePath) throws IOException {
        this.clearAll();
        this.filePath = filePath;
        return this.startReadingFile();
    }

    /**
     * Start to read the file,and reach "!platform_table_begin"
     * Goto this.read_platform_table(br);
     * @return true if read success,false otherwise
     */
    private boolean startReadingFile() throws FileNotFoundException, IOException {
        boolean readSuccess=false;
        File file = new File(this.filePath);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        temp = br.readLine();
        while (temp != null) {
            //if reach "!platform_table_begin" then begin to read the table
            if (temp.equals("!platform_table_begin")) {
                this.read_platform_table(br);
                readSuccess=true;
            }
            temp = br.readLine();
        }
        //br.close();
        return readSuccess;
    }

    /**
     * Read the platforrm table until "!platform_table_end"
     * @param br 
     * @throws IOException 
     */
    private void read_platform_table(BufferedReader br) throws IOException {
        String temp = br.readLine();
        temp = br.readLine();
        String[] strs = null;
        while (!temp.equals("!platform_table_end")) {
            strs = temp.split("\t");
            if (strs[0].isEmpty() || strs[1].isEmpty() || strs[2].isEmpty()) {
                temp = br.readLine();
                continue;
            }
            this.ID.add(strs[0]);
            this.geneTitle.add(strs[1]);
            this.geneSymbol.add(strs[2]);
            //System.out.println(this.ID.get(this.ID.size() - 1) + this.geneTitle.get(this.geneTitle.size() - 1));
            temp = br.readLine();
        }
    }
    /**
     * Only for testting
     * @param args
     * @throws IOException 
     */
//    public static void main(String args[]) throws IOException {
//        SoftFileRead fileread = new SoftFileRead("circos\\GPL3154.soft");
//        fileread.readSignalsFile("circos\\GSM510401.txt");
//        //System.out.println(fileread.getGenePlaceByID("175906jk9_at"));
//        System.out.println(fileread.getSignalByGeneSymbol("c4728"));
//    }
}
