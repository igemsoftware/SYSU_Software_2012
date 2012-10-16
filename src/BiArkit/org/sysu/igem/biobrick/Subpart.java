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
    public enum subpart_type{
        DNA,PlasmidBackbones,Plasmids,Primers,Promoters,ProteinCodingSequences,
        ProteinDomains,RibosomeBindingSites,Terminators,TranslationalUnits    
        ,other,Reporters;
        @Override
        public String toString() {
            String id=name();
            if(id.equals("DNA"))
                return "DNA";
            else if(id.equals("RibosomeBindingSites"))
                return "RBS";
            else if(id.equals("ProteinCodingSequences"))
                return "Coding";
            else if(id.equals("PlasmidBackbones"))
                return "Plasmid_Backbone";
            else if(id.equals("Primers"))
                return "Primer";
            else if(id.equals("Terminators"))
                return "Terminator";
            else if(id.equals("Reporters"))
                return "Reporter";
            else
                return "other";
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
    public Subpart(Element ele) {
        this.part_name=ele.getChildText("part_name");
        this.type=getSubpartTypeFromString(ele.getChildText("part_type"));       
        this.PrintAll();
    }
    private void PrintAll()
    {
        System.out.println(this.part_name+" "+this.type);
    }
    public static subpart_type getSubpartTypeFromString(String type)
    {
        if(type.toUpperCase().equals("RBS"))
            return subpart_type.RibosomeBindingSites;
        else if(type.toLowerCase().equals("coding"))
            return subpart_type.ProteinCodingSequences;
        else if(type.toLowerCase().equals("plasmid_backbone"))
            return subpart_type.PlasmidBackbones;
        else if(type.toLowerCase().equals("primer"))
            return subpart_type.Primers;
        else if(type.toLowerCase().equals("terminator"))
            return subpart_type.Terminators;
        else if(type.toLowerCase().equals("reporter"))
            return subpart_type.Reporters;
        else
            return subpart_type.other;
    }
}
