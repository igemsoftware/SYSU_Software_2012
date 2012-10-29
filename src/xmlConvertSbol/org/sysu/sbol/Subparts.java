package org.sysu.sbol;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Guo Jiexin
 */
public class Subparts {

    private String file_name;
    private List<Subpart> subpart_list = new ArrayList<Subpart>();

    /**
     * @return the file_name
     */
    public String getFile_name() {
        return file_name;
    }
    /**
     * @param file_name the file_name to set
     */
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public List<Subpart> getSubpart_list() {
        return subpart_list;
    }

//    public void setSubpart_list(List<subpart> subpart_list) {
//        this.subpart_list = subpart_list;
//    }
    
    /**
     * Get the Deep_subparts into subpart_list
     * @param list 
     */
    private void getFeatures(List<Element> list2) {
        List<Element> list = list2.get(0).getChild("part").getChild("features").getChildren("feature");
        if(list.isEmpty())
            return;
        for (int i = 0; i < list.size(); i++) {
        	feature temp = new feature(list.get(i));
            if (temp != null&&!this.subpart_list.contains(temp)) {
                this.feature_list.add(temp);
            }
        }
    }
    public List<feature> feature_list=new ArrayList();
    private void getDeep_subparts(List<Element> list2) {
        List<Element> list = list2.get(0).getChild("part").getChild("deep_subparts").getChildren("subpart");
        if(list.isEmpty())
            return;
        for (int i = 0; i < list.size(); i++) {
            Subpart temp = new Subpart(list.get(i));
            if (temp != null&&!this.subpart_list.contains(temp)) {
                this.subpart_list.add(temp);
            }
        }
    }
    private void getSpecified_subparts(List<Element> list2) {
        List<Element>list = list2.get(0).getChild("part").getChild("specified_subparts").getChildren("subpart");
        for (int i = 0; i < list.size(); i++) {
            Subpart temp = new Subpart(list.get(i));
            if (temp != null&&!this.subpart_list.contains(temp)) {
                this.subpart_list.add(temp);
            }
        }
    }
    public Subparts(String fileName) {
        this.subpart_list.clear();
        file_name = fileName;
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(new File(file_name));
            Element rootEl = doc.getRootElement();
            List<Element> list = rootEl.getChildren();
            this.getDeep_subparts(list);        
            this.getSpecified_subparts(list);
            this.getFeatures(list);
        } catch (JDOMException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            return;
        }
    }
    
    public static void main(String[] args) {
        //new Subparts("biobrick/Translational units/BBa_E5500.xml");
        new Subparts("biobrick/Terminators/BBa_B0013.xml");
        //F:\360data\重要数据\桌面\TestBrowser\biobrick\Terminators
    }
}