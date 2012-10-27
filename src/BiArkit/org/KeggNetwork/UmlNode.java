package org.KeggNetwork;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import twaver.ResizableNode;
import twaver.TUIManager;
import twaver.TWaverConst;
/**
 * 
 * @author Guo Jiexin
 */
public class UmlNode extends ResizableNode {
	public Entry mEntry=null;
	public List<Integer> linkedId=new ArrayList<Integer>();
	@Override
	public int getHeight() {
            if(!this.mEntry.mGraphics.type.equals("circle"))
		return Integer.parseInt(this.mEntry.mGraphics.height)+3;
            else
                return Integer.parseInt(this.mEntry.mGraphics.height);
	}
	@Override
	public int getWidth() {	  
            if(!this.mEntry.mGraphics.type.equals("circle"))
		return Integer.parseInt(this.mEntry.mGraphics.width)+4;	
            else
                return Integer.parseInt(this.mEntry.mGraphics.width);
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public String LegalString(String str)
	{
		String toReturn = null;
		for(int i=0;i<str.length();i++)
		{
			char ch=str.charAt(i);
			boolean flag=(ch==':')||(ch==' ')||('a'<=ch&&ch<='z')||('0'<=ch&&ch<='9')||('A'<=ch&&ch<='Z');
			if(flag)
			{
				toReturn+=ch;
			}
		}
		toReturn=toReturn.replaceAll("null","");
		return toReturn;
	}
    public UmlNode(Entry temp) {
    	super(Integer.parseInt(temp.id));
        this.mEntry=temp;
        initNode();
    }    
    private String SetLineByGraphicsWidth(String name)
    {
    	int fontWidth=7;
    	int charNumberOneLine=Integer.parseInt(this.mEntry.mGraphics.width)/fontWidth;
    	StringBuilder toReturn=new StringBuilder("");
    	for(int i=0;i<name.length();i++)
    	{
    		toReturn.append(name.charAt(i));
    		if(i%charNumberOneLine==0&&i!=0)
    		{
    			toReturn.append("<br>");
    		}
    	}
    	return toReturn.toString();
    }
    
    private void initNode() {
        this.putCustomDraw(true);
        this.putCustomDrawFillColor(new Color(Integer.parseInt(this.mEntry.mGraphics.bgcolor.substring(1),16)));
        
        //改
        //this.putCustomDrawFillColor(Color.red);
        //改
        
        if(this.mEntry.mGraphics.type.equals("roundrectangle"))//roundrectangle
        	this.putCustomDrawShapeFactory(TWaverConst.SHAPE_ROUND_RECTANGLE_QUARTER);
        else if(this.mEntry.mGraphics.type.equals("rectangle"))//"rectangle"
        	this.putCustomDrawShapeFactory(TWaverConst.SHAPE_RECTANGLE);
        else if(this.mEntry.mGraphics.type.equals("circle"))//"circle"
        	this.putCustomDrawShapeFactory(TWaverConst.SHAPE_CIRCLE);        
        
        //this.putCustomDrawOutlineColor(new Color(Integer.parseInt(this.mEntry.mGraphics.fgcolor.substring(1),16)));
        this.putLabelFont(TUIManager.getDefaultPlainFont());  
        
        if(!this.mEntry.mGraphics.type.equals("circle"))//��"circle"
        {        	
        	this.putLabelPosition(TWaverConst.POSITION_CENTER);   
        	//
        	String name=this.mEntry.mGraphics.name;
        	name=this.LegalString(name);//name.replaceAll("@...","");
        	//System.out.println("name+"+name);
        	//this.setName(name);
        	//this.setDisplayName("<html><center>"+this.SetLineByGraphicsWidth(name)+"<br></center></html>");
        	//this.setName(this.SetLineByGraphicsWidth(name));
        	this.setName("<html>"+this.SetLineByGraphicsWidth(name)+"</html>");
        	System.out.println(this.mEntry.name);
        }
        else if(this.mEntry.mGraphics.type.equals("circle"))// circle���͵Ľڵ�
        {        	
        	String name=this.mEntry.mGraphics.name;        	
        	name=this.LegalString(name);//name.replaceAll("@...","");
        	this.setName(name);
        	System.out.println(this.mEntry.name);
        }
    }
    //����UI
    /*public String getUIClassID() {
        return UmlNodeUI.class.getName();
    }*/
}

