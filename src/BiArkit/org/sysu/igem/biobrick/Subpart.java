/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.biobrick;

import org.jdom.Element;

/**
 * The class to save the data of Subpart from biobrick to show subparts in a visual way
 * @author Guo Jiexin
 */
public class Subpart {

    public enum subpart_type {

        DNA, Plasmid_Backbone, Plasmid, Primers, Promoters, Coding,
        Protein_Domain, RBS, other, Reporter, Composite, Conjugation, Generator, Intermediate, Regulatory, RNA, Signalling, T7, Tag, Terminator, Translational_Unit;

        @Override
        public String toString() {
            String id = name();
            return id;
        }
    }
    private subpart_type type;

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public subpart_type getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Subpart other = (Subpart) obj;
        if (this.type != other.type) {
            return false;
        }
        if ((this.part_name == null) ? (other.part_name != null) : !this.part_name.equals(other.part_name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 71 * hash + (this.part_name != null ? this.part_name.hashCode() : 0);
        return hash;
    }

    public void setType(subpart_type type) {
        this.type = type;
    }
    private String part_name;
    /**
     * 
     * @param ele a xml element from a xml
     */
    public Subpart(Element ele) {
        this.part_name = ele.getChildText("part_name");
        this.type = getSubpartTypeFromString(ele.getChildText("part_type"));        
        this.PrintAll();
    }

    private void PrintAll() {
        System.out.println(this.part_name + " " + this.type);
    }
    /**
     * Get subpart_type from string
     * @param type
     * @return 
     */
    public static subpart_type getSubpartTypeFromString(String type) {
        if (type.equals("Coding")) {
            return subpart_type.Coding;
        } else if (type.equals("Composite")) {
            return subpart_type.other;
        } else if (type.equals("Conjugation")) {
            return subpart_type.Conjugation;
        } else if (type.equals("DNA")) {
            return subpart_type.DNA;
        } else if (type.equals("Generator")) {
            return subpart_type.Generator;
        } else if (type.equals("Intermediate")) {
            return subpart_type.Intermediate;
        } else if (type.equals("Plasmid")) {
            return subpart_type.Plasmid;
        } else if (type.equals("Plasmid_Backbone")) {
            return subpart_type.Plasmid_Backbone;
        } else if (type.equals("Primers")) {
            return subpart_type.Primers;
        } else if (type.equals("Protein_Domain")) {
            return subpart_type.Protein_Domain;
        } else if (type.equals("RBS")) {
            return subpart_type.RBS;
        } else if (type.equals("Regulatory")) {
            return subpart_type.Regulatory;
        } else if (type.equals("Reporter")) {
            return subpart_type.Reporter;
        } else if (type.equals("RNA")) {
            return subpart_type.RNA;
        } else if (type.equals("Signalling")) {
            return subpart_type.Signalling;
        } else if (type.equals("T7")) {
            return subpart_type.T7;
        } else if (type.equals("Tag")) {
            return subpart_type.Tag;
        } else if (type.equals("Terminator")) {
            return subpart_type.Terminator;
        } else if (type.equals("Translational_Unit")) {
            return subpart_type.Translational_Unit;
        } else {
            return subpart_type.other;
        }
    }
}
