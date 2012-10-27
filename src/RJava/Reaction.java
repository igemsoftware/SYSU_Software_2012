import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
public class Reaction {
	public class specieRef
	{
		public String species="";
//		public double stoichiometry=0;
//		public BigDecimal stoichiometry=null;
		public String stoichiometry=null;
		public specieRef(final Element element)
		{
			this.species=element.attributeValue("species");
			String temp=element.attributeValue("stoichiometry");
//			double temp2=Double.parseDouble(temp);
//			System.out.println(temp2);
			this.stoichiometry=temp;
//			stoichiometry=new BigDecimal(temp);
		}
		public void PrintAll()
		{
			System.out.print(this.species+' ');
			System.out.print(this.stoichiometry+' ');
			System.out.println();
		}
	}
	public boolean reversible=false;
	public String id="";
	public String name="";
	public String SUBSYSTEM="";
	public List<String>  mGeneNames=new ArrayList<String>();
	public List<specieRef> mReactants=new ArrayList<specieRef>();
	public List<specieRef> mProducts=new ArrayList<specieRef>();
	public String mGeneName="";
	public double upper_val=0;
	public double lower_val=0;
	public double OBJECTIVE_COEFFICIENT=0;
	public Reaction(final Element element) {		
		this.id=element.attributeValue("id");
		this.name=element.attributeValue("name");
		List<Element> temp=element.elements("listOfReactants");
		temp=temp.get(0).elements("speciesReference");
		for(Element ele:temp)			
			this.mReactants.add(new specieRef(ele));
		temp=element.elements("listOfProducts");
		temp=temp.get(0).elements("speciesReference");
		for(Element ele:temp)
			this.mProducts.add(new specieRef(ele));
		Element temp2=element.element("notes");
		temp=temp2.elements();
		this.mGeneName=temp.get(0).getText().substring(18);
		this.SUBSYSTEM=temp.get(2).getText().substring(11);
		//System.out.println("sub"+this.SUBSYSTEM);
		List<Element> parameters=element.element("kineticLaw").element("listOfParameters").elements("parameter");
		this.lower_val=Double.parseDouble(parameters.get(0).attributeValue("value"));
		this.upper_val=Double.parseDouble(parameters.get(1).attributeValue("value"));
		this.OBJECTIVE_COEFFICIENT=Double.parseDouble(parameters.get(2).attributeValue("value"));
			
		String[] temString=this.mGeneName.split(" ");		
		for(int i=0;i<temString.length;i++)
			if(temString[i].length()==5)
				this.mGeneNames.add(temString[i]);
	}
	public void PrintAll()
	{
		System.out.print(this.id+' ');
		System.out.print(this.name+' ');
		System.out.println();
	}
}
