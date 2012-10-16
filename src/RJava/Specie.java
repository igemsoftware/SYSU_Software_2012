import org.dom4j.Element;
public class Specie {		
	public String id="";
	public String name="";
	public boolean boundaryCondition=false;
	public int compartment=0;
	public Specie(final Element element) {		
		this.id=element.attributeValue("id");
		this.name=element.attributeValue("name");		
		if(element.attributeValue("compartment").equals("Cytosol"))
			this.compartment=1;
		else if(element.attributeValue("compartment").equals("Extra_organism"))
			this.compartment=2;
		if(element.attributeValue("boundaryCondition").equals("true"))
			this.boundaryCondition=true;
		else
			this.boundaryCondition=false;
	}	
	public void PrintAll()
	{
		System.out.print(this.id+' ');
		System.out.print(this.name+' ');
		System.out.println();
	}
}
