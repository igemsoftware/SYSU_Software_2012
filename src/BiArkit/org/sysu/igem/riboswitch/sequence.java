package org.sysu.igem.riboswitch;

public class sequence {
	protected char[] sequence;
	protected String[] antisense;
	protected boolean helix=false;
	public sequence(String input){
		int length=input.length();
		sequence=new char[length];
		for(int i=0;i<length;i++){
			sequence[i]=input.substring(i, i+1).charAt(0);
		}
	}
	public sequence(char[] input){
		this.sequence=input;
	}
	public void helix(){
		helix=true;
		this.antisense=new String[this.sequence.length];
		for (int i=0; i<this.sequence.length;i++){
			switch(sequence[i]){
			case 'a': antisense[this.sequence.length-i-1]="u";break;
			case 'u': antisense[this.sequence.length-i-1]="a";break;
			case 'g': antisense[this.sequence.length-i-1]="c";break;
			case 'c': antisense[this.sequence.length-i-1]="g";break;
			case 'A': antisense[this.sequence.length-i-1]="U";break;
			case 'U': antisense[this.sequence.length-i-1]="A";break;
			case 'G': antisense[this.sequence.length-i-1]="C";break;
			case 'C': antisense[this.sequence.length-i-1]="G";break;
			default: break;
			}
		}
	}
	
	public sequence add(sequence input){
		char[] result=new char[input.getSequence().length+this.sequence.length];
		System.arraycopy(this.sequence, 0, result, 0, this.sequence.length);
		System.arraycopy(input.getSequence(), 0, result, this.sequence.length, input.getSequence().length);
		sequence output= new sequence(result);
		return output;
	}
	
	public char[] getSequence(){
		return this.sequence;
	}
	public String[] getAntisense(){
		return this.antisense;
	}
	public String getStringSequence(){
		String result="";
		for (int i=0;i<sequence.length;i++){
			result+=sequence[i];
		}
		return result;
	}
	public String getStringAntisense(){
		String result="";
		for (int i=0;i<antisense.length;i++){
			result+=antisense[i];
		}
		return result;
	}
	
	

}
