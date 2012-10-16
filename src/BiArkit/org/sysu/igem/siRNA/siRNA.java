package org.sysu.igem.siRNA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sysu.igem.utils.StringUtils;

public class siRNA {

    File fileIput; // = new File("p53.fasta");
    BufferedReader inputBuffered;
    String geneString;
    
    public String getGene(){
        return geneString;
    }

    public siRNA(File fileInput) {
        if(fileInput==null||!fileInput.exists())
            return;
        this.fileIput = fileInput;
        String temp = new String();
        try {
            String str;
            inputBuffered = new BufferedReader(new FileReader(fileIput));
            inputBuffered.readLine();
            while ((str = inputBuffered.readLine()) != null) {
                temp += str;
            }
        } catch (IOException ex) {
            Logger.getLogger(siRNA.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.geneString = StringUtils.replaceBlank(temp);
    }

    public siRNA(String geneString) {
        this.geneString = StringUtils.replaceBlank(geneString);
    }

    public boolean isValid() {
        for (int i = 0; i < geneString.length(); i++) {
            if (geneString.charAt(i) == 'A'
                    || geneString.charAt(i) == 'T'
                    || geneString.charAt(i) == 'G'
                    || geneString.charAt(i) == 'C'
                    || geneString.charAt(i) == '\n') {
                //System.out.println(input.charAt(i));
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public List siRNA() {
        List list = new ArrayList();

        ArrayList<candidate> outcome1 = new ArrayList<candidate>();
        ArrayList<candidate> outcome2 = new ArrayList<candidate>();
        for (int i = 0; i + 22 < geneString.length(); i++) {
            candidate temp = new candidate(geneString.substring(i, i + 23), i + 1);
            if (temp.test1()) {
                outcome1.add(temp);
            }
            temp = new candidate(geneString.substring(i, i + 19), i + 1);
            if (temp.test2()) {
                outcome2.add(temp);
            }
        }
        if (outcome1.size() == 0) {
            for (int i = 0; i + 22 < geneString.length(); i++) {
                candidate temp = new candidate(geneString.substring(i, i + 23), i + 1);
                if (temp.test12()) {
                    outcome1.add(temp);
                }
            }
        }
        

        String temp1 = "";//"<html>";
//        System.out.println("The resault for test1 is:");
        for (int i = 0; i < outcome1.size(); i++) {
            temp1 += outcome1.get(i).getSequence();
            temp1 += "\n\r";//"\n";
            System.out.println(outcome1.get(i).getSequence());
        }
        //temp1+="</html>";
        list.add(temp1);

        String temp2 = "";
//        System.out.println("The resault for test2 is:");
        for (int i = 0; i < outcome1.size(); i++) {
            temp2 += outcome2.get(i).getSequence();
            temp2 += "\n";
            System.out.println(outcome2.get(i).getSequence());
        }
        list.add(temp2);

        return list;
    }

}
