/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author q
 */
public class ReadFileRandom {

    public int readSize = 100;
    // For Random Access
    RandomAccessFile randomFile;
    String fileName;
    byte[] bytes;
    /**
     * The file's size
     */
    int fileSize;
    // For First Line
    String description;
    /**
     * The First line's length
     */
    int length;

    public static void main(String[] args) {
        Object[] by = new ReadFileRandom("file/genome.fasta").readFileByRandomAccess(0);
        for (Object b : by) {
        }
    }

    public ReadFileRandom(String fileName) {
        this.fileName = fileName;

        try {
            // System.out.println(ReadFileRandom.class);
            // Open a random access file stream, read-only
            randomFile = new RandomAccessFile(fileName, "r");
            // Get file length
            fileSize = (int) randomFile.length();
            // Get the first line and its length
            description = randomFile.readLine();
            length = description.length();
            //System.out.println(fileSize); 
            //System.out.println(length);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadFileRandom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("ReadLing Error");
        }

    }

    public void setReadSize(int size) {
        this.readSize = size+2;
    }

    public int getReadSize() {
        return readSize;
    }

    /**
     * Get file size
     * @return 
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * The length of the first line
     */
    public int getFirstLength() {
        return length;
    }

    /**
     * Random read the contents of the file
     */
    public Object[][] readFileByRandomAccess(int beginIndex) {
        //System.out.println(beginIndex);
        try {
            // Open a random access file stream, read-only
            randomFile = new RandomAccessFile(fileName, "r");
            // Determine the length does not exceed integer
            // The starting position of the read files to determine if the overflow rolled back 41 
            beginIndex = (int) ((beginIndex + readSize) > fileSize + 1 ? fileSize - readSize - 1 : beginIndex);

            // The starting position of the read file is moved to the the beginIndex location. And do not need to display the first line
            //System.out.println(beginIndex+this.length);
            randomFile.seek(beginIndex + this.length);
            bytes = new byte[readSize + 1];
            // A read of 40 bytes, and if the file content is less than 10 bytes, the remaining bytes of the read.
            // To assign byteread will be a number of bytes read
            randomFile.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
        Object[] obj = new Object[readSize];
        Object[] opp = new Object[readSize];
        int counter = 0;
        for (byte b : bytes) {
            if (counter >= readSize) {
                break;
            }
            if (isATGC((char)b)){ //!= '\n' && (char) (b) != '\r') {
                    obj[counter] = (char) (b);
                    opp[counter] = getATGC((char) b);
                    counter++;
            }
        }
        Object[][] ret = new Object[][]{
            obj,
            opp
        };
        return ret;
    }

    private boolean isATGC(char c){
        if (c == 'A' || c == 'a' || c == 'T' || c == 't' || c == 'G' || c == 'g' || c == 'C' || c == 'c')
            return true;
        return false;
    }
    
    /**
     * Set ATGC
     */
    private Object getATGC(char c) {
        if (c == 'A' || c == 'a') {
            return 'T';
        } else if (c == 'T' || c == 't') {
            return 'A';
        } else if (c == 'G' || c == 'g') {
            return 'C';
        } else if (c == 'C' || c == 'c') {
            return 'G';
        } else {
            return null;//E stand for error
        }
    }
}
