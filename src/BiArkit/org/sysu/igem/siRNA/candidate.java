package org.sysu.igem.siRNA;

public class candidate {
	private String sequence;
	private int position;
	private double GCcontent;
	private double Tm;
	public candidate (String str,int position){
//		sequence= new StringBuffer(str).reverse().toString();
		sequence= str.replaceAll("T", "U");
		this.position=position;
		GCcontent=GCcontent(str);
		Tm=79.8+18.5*Math.log10(0.05)+(58.4*GCcontent)+(11.8*GCcontent*GCcontent)-(820/sequence.length());
	}
	public boolean test1(){
		if (position<50||position>100){return false;}
		if (sequence.length()==23){
			if(sequence.substring(0, 2).equals("AA")&&sequence.substring(21, 23).equals("UU")){
                            return true;
                        }
			if(sequence.substring(1, 2).equals("A")){
				sequence=sequence.substring(0,sequence.length()-2)+"UU";
				return true;
			}else{return false;}
		}
		return false;
	}
        public boolean test12(){
            if (position<50||position>100){return false;}
            if(sequence.substring(1, 2).equals("A")){
                sequence=sequence.substring(0,sequence.length()-2)+"UU";
                return true;
            }
            return false;
        }
	public boolean test2(){
		int score=0;
		if (0.3<=GCcontent&&GCcontent<=0.52){score++;}
		if (GCcontent(sequence.substring(14, 19))<=0.4){score+=5*(1-GCcontent(sequence.substring(14, 19)));}
		if (Tm<20){score++;}
		if (sequence.substring(18, 19).equals("A")){score++;}
		if (sequence.substring(2, 3).equals("A")){score++;}
		if (sequence.substring(9, 10).equals("U")){score++;}
		if (sequence.substring(18, 19).equals("G")){score--;}
		if (sequence.substring(18, 19).equals("C")){score--;}
		if (sequence.substring(12, 13).equals("G")){score--;}
		if(score>=6){return true;}else{return false;}
	}
	private double GCcontent(String str){
		double counter=0;double resault;
		for(int i=0;i<str.length();i++){
			if(str.substring(i, i+1).equals("G")||str.substring(i, i+1).equals("C")){
				counter++;
			}
		}
		resault=counter/str.length();
		return resault;
	}
	public String getSequence(){
		return sequence;
		
	}
}
