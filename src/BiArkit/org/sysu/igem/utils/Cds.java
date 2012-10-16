/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

/**
 *
 * @author q
 */
public class Cds {
    public Cds(int head, int tail, String amino){
        this.head = head;
        this.tail = tail;
        this.amino = amino;
    }
    

    public String amino;
    public String getAmino() {
        return amino;
    }

    public void setAmino(String amino) {
        this.amino = amino;
    }
    
    // set if get one cdc
    public int head;

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getTail() {
        return tail;
    }

    public void setTail(int tail) {
        this.tail = tail;
    }
    public int tail;
    
    
}
