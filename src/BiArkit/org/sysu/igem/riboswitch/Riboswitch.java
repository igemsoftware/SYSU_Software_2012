package org.sysu.igem.riboswitch;
import org.sysu.igem.riboswitch.freeEnergy;
import org.sysu.igem.riboswitch.sequence;

/**
 * 
 * @author Miao Zong
 */
public class Riboswitch{
	protected sequence aptamer;
	protected sequence MS;
	protected static sequence SL =new sequence("GGGAGACCACAACGGUUUCCC");
	protected static sequence aaIRES =new sequence("AAGAGGUCU");
	protected static sequence aIRES =new sequence("AGACCUCU");
	protected static sequence slIIIIVV =new sequence("GCUGACUAUGUGAUCUUAUUAAAAUUAGGUUAAAUUUCGAGGUUAAAAAUAGUUUUAAUAUUGCUAUAGUCUUAGAGGUCUUGUAUAUUUAUACUUACCACACAAGAUGGACCGGGAGCAGCCCUCCAAUAUCNAGUGUUACCCUCGUGCUCGCUCAAACAUUAAGUGGUGUUGUGCGAAAAGAAUCUCACUUCAAGAAAAGAAACUAGU");

	public Riboswitch() {
		System.out.println("Please enter the aptamer sequence, The default aptamer is set to AUACCAGCCGAAAGGCCCUUGGCAGA.");
		this.aptamer= new sequence("AUACCAGCCGAAAGGCCCUUGGCAGA");
	}

	public Riboswitch(String input){
		this.aptamer= new sequence(input);
	}

	public void makeON(){
		double distance=11.6; double temp;int counter=1;
		sequence tempSeq =new sequence("GUCU"+aptamer.getStringSequence().substring(0, counter));
		tempSeq.helix();
		freeEnergy result= calculate(tempSeq.getStringAntisense()).add(new freeEnergy(5.7,0.2)).add(new freeEnergy(-1.4,0));
		temp =Math.abs(result.getBody()+11.6);
		while (temp<=distance){
			distance=temp;
			counter++;
			tempSeq =new sequence("GUCU"+aptamer.getStringSequence().substring(0, counter));
			tempSeq.helix();
			result= calculate(tempSeq.getStringAntisense()).add(new freeEnergy(5.7,0.2)).add(new freeEnergy(-1.4,0));
			temp =Math.abs(result.getBody()+11.6);
		}
		this.MS= new sequence(tempSeq.getStringAntisense().substring(1));
	}
	
	public void makeOFF(){
		this.MS= new sequence("CCUCU");
	}
	
	public void setAptamer(String aptamer){
		this.aptamer=new sequence(aptamer);
	}
	
	public freeEnergy calculate(String candidate){
		freeEnergy result= new freeEnergy(0,0);
		for(int i=0;i<candidate.length()-1;i++){
			result=result.add(this.getValue(candidate.substring(i, i+1), candidate.substring(i+1, i+2)));
		}
		return result;
		}
	
	public void print(){
		System.out.println(MS.getStringSequence());
	}
	
	public String getON(){
		String result = SL.getStringSequence()+MS.getStringSequence()+aaIRES.getStringSequence()+aptamer.getStringSequence()+aIRES.getStringSequence()+slIIIIVV.getStringSequence();
		return result;
	}
	
	public String getOFF(){
		String result = SL.getStringSequence()+MS.getStringSequence()+aIRES.getStringSequence()+aptamer.getStringSequence()+slIIIIVV.getStringSequence();
		return result;
	}
	
	public String getAptamer(){
		return aptamer.getStringSequence();
	}
	
	public String getMS(){
		return MS.getStringSequence();
	}
	
	public void showON(){
		System.out.println(SL.getStringSequence()+MS.getStringSequence()+aaIRES.getStringSequence()+aptamer.getStringSequence()+aIRES.getStringSequence()+slIIIIVV.getStringSequence());
	}
	
	public void showOFF(){
		System.out.println(SL.getStringSequence()+MS.getStringSequence()+aptamer.getStringSequence()+aaIRES.getStringSequence()+aIRES.getStringSequence()+slIIIIVV.getStringSequence());
	}
	
	public freeEnergy getValue(String a, String b){
		freeEnergy AAUU= new freeEnergy(-0.93,0.03);
		freeEnergy AU= new freeEnergy(-1.10,0.08);
		freeEnergy CUAG= new freeEnergy(-2.08,0.06);
		freeEnergy CAUG= new freeEnergy(-2.11,0.07);
		freeEnergy UA= new freeEnergy(-1.33,0.09);
		freeEnergy GUAC= new freeEnergy(-2.24,0.06);
		freeEnergy GAUC= new freeEnergy(-2.35,0.06);
		freeEnergy CG= new freeEnergy(-2.36,0.09);
		freeEnergy GGCC= new freeEnergy(-3.26,0.07);
		freeEnergy GC= new freeEnergy(-3.42,0.08);
		if ((a.equals("A")&&b.equals("A"))||(a.equals("U")&&b.equals("U"))){
			return AAUU;
		}else if(a.equals("A")&&b.equals("U")){
			return AU;
		}else if((a.equals("C")&&b.equals("U"))||(a.equals("A")&&b.equals("G"))){
			return CUAG;
		}else if((a.equals("C")&&b.equals("A"))||(a.equals("U")&&b.equals("G"))){
			return CAUG;
		}else if((a.equals("G")&&b.equals("U"))||(a.equals("A")&&b.equals("C"))){
			return GUAC;
		}else if((a.equals("G")&&b.equals("A"))||(a.equals("U")&&b.equals("C"))){
			return GAUC;
		}else if((a.equals("G")&&b.equals("G"))||(a.equals("C")&&b.equals("C"))){
			return GGCC;
		}else if(a.equals("U")&&b.equals("A")){
			return UA;
		}else if(a.equals("C")&&b.equals("G")){
			return CG;
		}else if(a.equals("G")&&b.equals("C")){
			return GC;
		}
		return GC;
	}
}
