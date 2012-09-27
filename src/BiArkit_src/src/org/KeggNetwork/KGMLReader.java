package org.KeggNetwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javax.swing.filechooser.FileNameExtensionFilter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import twaver.Link;
import twaver.PopupMenuGenerator;
import twaver.TDataBox;
import twaver.TUIManager;
import twaver.TView;
import twaver.TWaverConst;
import twaver.network.TNetwork;

/**
 * 
 * @author Guo Jiexin
 */
public class KGMLReader {

    /**
     * the xml file that you open
     */
    private Document document = null;
    /*
     * entry list
     */
    private List<Entry> mEntrys = new ArrayList<Entry>();
    private List<Relation> mRelations = new ArrayList<Relation>();
    private PassWay mPassway = null;
    private List<Reaction> mReactions = new ArrayList<Reaction>();
    private static TDataBox box = new TDataBox("Simple Data Box");
    private static TNetwork network = null;
    private JPanel networkPane = new JPanel(new BorderLayout());
    /**
     * the node list that print on the networkpane
     */
    private List<UmlNode> nodes = new ArrayList<UmlNode>();
    private List<Link> links = new ArrayList<Link>();
    /**
     * the node that find out last time
     */
    public List<UmlNode> preFindedNodes = new ArrayList<UmlNode>();
    private static Font font = new Font("微软雅黑", Font.PLAIN, 10);
    /**
     * The file name that user opened
     */
    private String mFileName = "";
    /**
     * If you have used JFrame, then leave a record 
     */
    protected JFrame mFrame = null;
    //private int mLinkType=TWaverConst.LINK_TYPE_BOTTOM;
    private int mLinkType = TWaverConst.LINK_TYPE_ORTHOGONAL;

    public KGMLReader(String fileName) throws HeadlessException {
        super();
        //Anti-aliasing
        TUIManager.registerDefault(TWaverConst.PROPERTYNAME_LINK_ANTIALIAS, true);
        this.ReadXML(this.mFileName);
        this.DrawNetwork();
    }

    public KGMLReader(String fileName, JPanel jPanel13) {
        super();
        this.mFileName = fileName;
        jPanel13.add(networkPane);
        TUIManager.registerDefault(TWaverConst.PROPERTYNAME_LINK_ANTIALIAS, true);
        jPanel13.setVisible(true);
        this.ReadXML(this.mFileName);
        this.DrawNetwork();
    }

//    public KGMLReader(String fileName, JPanel frame) throws HeadlessException {
//        super();
//        this.mFileName = fileName;
//        frame.add(networkPane);
//        //Anti-aliasing��
//        TUIManager.registerDefault(TWaverConst.PROPERTYNAME_LINK_ANTIALIAS, true);
//        frame.setVisible(true);
//        this.ReadXML(this.mFileName);
//        this.DrawNetwork();
//    }
    /**
     * Clear all list or fields
     */
    public void ClearAll() {
        for (Link link : links) {
            box.removeElement(link);
        }
        for (UmlNode node : nodes) {
            box.removeElement(node);
        }
        this.networkPane.removeAll();
        this.preFindedNodes.clear();
        this.nodes.clear();
        this.links.clear();
        this.mEntrys.clear();
        this.mReactions.clear();
        this.mReactions.clear();
        this.document.clearContent();
    }

    /**
     * get passway nodes
     * @param printToConsole 
     *          Whether to print in the console
     */
    public void GetPassway(boolean printToConsole) {
        mPassway = new PassWay(document.getRootElement());
        if (this.mPassway == null)//Get the root node of the error. 
        {
            System.out.println("get passway root errror!The program exit now.");
            System.exit(0);
        }
        if (printToConsole) {
            mPassway.PrintAll();
        }
    }

    /**
     * get entrys
     * @param printToConsole
     *           Whether to print in the console
     */
    public void GetEntrys(boolean printToConsole) {
        List<Element> entrys = document.getRootElement().elements("entry");
        if (entrys != null) {
            //Traversing the entry node
            for (Iterator<Element> it = entrys.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
                Entry ent = new Entry(elm);
                if (ent != null) {
                    this.mEntrys.add(ent);
                    if (printToConsole) {
                        ent.PrintAll();
                    }
                }
            }
        } else {
            System.out.println("get entry data error,null pointer");
        }
    }

    /**
     * Relations in XML
     * @param printToConsole
     *           Whether to print in the console
     */
    public void GetRelations(boolean printToConsole) {
        List<Element> relations = document.getRootElement().elements("relation");
        if (relations != null) {
            //Traverse relation node
            for (Iterator<Element> it = relations.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
                Relation rel = new Relation(elm);
                this.mRelations.add(rel);
                if (printToConsole) {
                    rel.PrintAll();
                }
            }
        } else {
            System.out.println("get relation data error,null pointer");
        }
    }

    /**
     * Get Reaction in xml
     * @param printToConsole
     *          Whether to print in the console
     */
    public void GetReactions(boolean printToConsole) {
        List<Element> reactions = document.getRootElement().elements("reaction");
        if (reactions != null) {
            //Traverse reaction nodes
            for (Iterator<Element> it = reactions.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
                Reaction rel = new Reaction(elm);
                this.mReactions.add(rel);
                if (printToConsole) {
                    rel.PrintAll();
                }
            }
        }
    }

    /**
     * perSelected node's color back (and clear the list)
     */
    public void AllPreNodeReset() {
        for (UmlNode node : this.preFindedNodes) {
            node.putCustomDrawFillColor(new Color(Integer.parseInt(node.mEntry.mGraphics.bgcolor.substring(1), 16)));
        }
        this.preFindedNodes.clear();
    }

    public void SaveXML(String fileName) {
    }

    /**
     * Read the xml
     * @param fileName
     *          the fileName that you want to read
     */
    public void ReadXML(String fileName) {
        SAXReader reader = new SAXReader();
        reader.setValidation(false);
        System.out.println(fileName);
        try {
            document = reader.read(new File(fileName));
        } catch (DocumentException e) {
            System.out.println("Document open error!");
            e.printStackTrace();
        }
        this.GetPassway(false);
        this.GetEntrys(false);
        this.GetRelations(false);
        this.GetReactions(false);
    }

    /**
     *�Draw all entrys in box (data container)�
     */
    public void DrawEntrys() {
        for (int i = 0; i < this.mEntrys.size(); i++) {
            final Entry temp = this.mEntrys.get(i);
            UmlNode node = new UmlNode(temp);
            if (node != null) {
                node.setLocation(this.mEntrys.get(i).mGraphics.getX(), this.mEntrys.get(i).mGraphics.getY());
                this.nodes.add(node);
                box.addElement(node);
            }
        }
    }

    /**
     * set link type by relation
     * @param link the link in box
     * @param relation the relation from xml
     */
    private void SetLinkType(Link link, Relation relation) {
        //First determine the type of relation is whether pprel
        if (relation.type.equals("PPrel")) {
            if (relation.mSubType.get(0).name.equals("activation")) {
                //Solid line arrows connect activation
                link.setLinkType(mLinkType);
                link.putLinkOutlineWidth(0);
                link.putLinkWidth(1);
                link.putRenderColor(Color.BLACK);
                link.putLinkToArrow(true);
            } else if (relation.mSubType.get(0).name.equals("binding/association")) {
                //Solid line no arrow to connect binding / association
                link.setLinkType(mLinkType);
                link.putLinkOutlineWidth(0);
                link.putLinkWidth(1);
                link.putRenderColor(Color.BLACK);
                link.putLinkToArrow(false);
            } else if (relation.mSubType.get(0).name.equals("phosphorylation")) {
                //Dashed line arrow connect phosphorylation
                link.setLinkType(mLinkType);
                link.putLinkStyle(TWaverConst.LINK_STYLE_DASH);
                link.putLinkOutlineWidth(0);
                link.putLinkWidth(1);
                link.putRenderColor(Color.BLACK);
                link.putLinkToArrow(false);
            } else if (relation.mSubType.get(0).name.equals("dephosphorylation")) {
                //Dashed arrows connect dephosphorylation
                link.setLinkType(mLinkType);
                link.putLinkStyle(TWaverConst.LINK_STYLE_DASH);
                link.putLinkOutlineWidth(0);
                link.putLinkWidth(1);
                link.putRenderColor(Color.BLACK);
                link.putLinkToArrow(true);
            } else if (relation.mSubType.get(0).name.equals("inhibition")) {
                //Dotted arrow indicates inhibition
                link.setLinkType(mLinkType);
                link.putLinkStyle(TWaverConst.LINK_STYLE_DASH);
                link.putLinkOutlineWidth(0);
                link.putLinkWidth(1);
                link.putRenderColor(Color.BLACK);
                link.putLinkToArrow(true);

                if (relation.mSubType.size() > 1) {
                    if (relation.mSubType.get(0).name.equals("dephosphorylation")) {
                        //Dashed arrows connect dephosphorylation
                        link.setLinkType(mLinkType);
                        link.putLinkStyle(TWaverConst.LINK_STYLE_DASH);
                        link.putLinkOutlineWidth(0);
                        link.putLinkWidth(1);
                        link.putRenderColor(Color.BLACK);
                        link.putLinkToArrow(true);
                    }
                }
            } else if (relation.mSubType.get(0).name.equals("indirect effect")) {
                //Dashed arrows connect indirect effect
                link.setLinkType(mLinkType);
                link.putLinkStyle(TWaverConst.LINK_STYLE_DASH);
                link.putLinkOutlineWidth(0);
                link.putLinkWidth(1);
                link.putRenderColor(Color.BLACK);
                link.putLinkToArrow(true);
            } else if (relation.mSubType.get(0).name.equals("compound")) {
                //Dashed arrows connect compound
                link.setLinkType(mLinkType);
                link.putLinkOutlineWidth(0);
                link.putLinkWidth(1);
                link.putRenderColor(Color.BLACK);
                link.putLinkToArrow(false);
            }
        } else {
            //The dotted line for the links of the map 
            //and the reaction product of compound
            link.setLinkType(mLinkType);
            link.putLinkStyle(TWaverConst.LINK_STYLE_DASH);
            link.putLinkOutlineWidth(0);
            link.putLinkWidth(1);
            link.putRenderColor(Color.BLACK);
            link.putLinkToArrow(false);
        }
    }

    /**
     * set link type of link by reaction from xml
     * @param link
     * @param reaction 
     */
    private void SetLinkType(Link link, Reaction reaction) {
        if (reaction.type.equals("reversible")) {
            //Solid triangular arrow, a solid line
            //link.setLinkType(TWaverConst.LINK_TYPE_STRAIGHT);
            link.setLinkType(mLinkType);
            link.putLinkOutlineWidth(0);
            link.putLinkWidth(1);
            link.putRenderColor(Color.BLACK);
            link.putLinkToArrow(true);
            link.putLinkToArrowStyle(TWaverConst.ARROW_DELTA_SMALL);
            link.putLinkToArrowOutlineColor(Color.black);
            link.putLinkToArrowColor(Color.black);
        } else {
            link.setLinkType(mLinkType);
            link.putLinkOutlineWidth(0);
            link.putLinkWidth(1);
            link.putRenderColor(Color.BLACK);
            link.putLinkToArrow(false);
        }
    }

    /**
     * to find a node by entryId
     * @param entryId
     * @return 
     *          a UmlNode whose id is entryId,if not found:return null
     */
    public UmlNode findNodeById(String entryId) {
        int id = Integer.parseInt(entryId);
        for (int j = 0; j < this.nodes.size(); j++) {
            if (Integer.parseInt(nodes.get(j).getID().toString()) == id) {
                return nodes.get(j);
            }
        }
        return null;
    }

    /**
     * to draw reations (PCreal and MapLink)
     */
    private void DrawRelationsPCrelAndMapLink() {
        for (int i = 0; i < this.mRelations.size(); i++) {
            //Find a relation's left and right node
            UmlNode leftNode = this.findNodeById(this.mRelations.get(i).entry1_id);
            UmlNode rightNode = this.findNodeById(this.mRelations.get(i).entry2_id);
            if (this.mRelations.get(i).type.equals("maplink")) {
                if (leftNode != null) {
                    if (leftNode.mEntry.type.equals("map")) {
                        UmlNode middleNode = this.findNodeById(this.mRelations.get(i).mSubType.get(0).value);
                        if (leftNode != null && middleNode != null) {
                            if (!leftNode.linkedId.contains(Integer.parseInt(middleNode.getID().toString()))) {
                                Link link = new Link("link" + (char) this.links.size() + 1, leftNode, middleNode);
                                this.SetLinkType(link, this.mRelations.get(i));
                                this.links.add(link);
                                box.addElement(link);
                                leftNode.linkedId.add(Integer.parseInt(middleNode.getID().toString()));
                                middleNode.linkedId.add(Integer.parseInt(leftNode.getID().toString()));
                            }
                        }
                    }
                }
                if (rightNode != null) {
                    if (rightNode.mEntry.type.equals("map")) {
                        UmlNode middleNode = this.findNodeById(this.mRelations.get(i).mSubType.get(0).value);
                        if (middleNode != null) {
                            if (!rightNode.linkedId.contains(Integer.parseInt(middleNode.getID().toString()))) {
                                Link link = new Link("link" + (char) this.links.size() + 1, middleNode, rightNode);
                                this.SetLinkType(link, this.mRelations.get(i));
                                this.links.add(link);
                                box.addElement(link);
                                rightNode.linkedId.add(Integer.parseInt(middleNode.getID().toString()));
                                middleNode.linkedId.add(Integer.parseInt(rightNode.getID().toString()));
                            }
                        }
                    }
                }
            } else if (this.mRelations.get(i).type.equals("PCrel")) {
                if (rightNode != null) {
                    if (!rightNode.linkedId.contains(Integer.parseInt(leftNode.getID().toString()))) {
                        Link link = new Link("link" + (char) this.links.size() + 1, leftNode, rightNode);
                        this.SetLinkType(link, this.mRelations.get(i));
                        this.links.add(link);
                        box.addElement(link);
                        rightNode.linkedId.add(Integer.parseInt(leftNode.getID().toString()));
                        leftNode.linkedId.add(Integer.parseInt(rightNode.getID().toString()));
                    }
                }
            } else if (this.mRelations.get(i).type.equals("ECrel")) {
                //"ECrel" type of relationship does not Paint
            }
        }
    }

    /**
     * �The processing the type PPrel 's link
     */
    private void DrawRelationPPrel() {
        for (int i = 0; i < this.mRelations.size(); i++) {
            //Find a relation 's left and right node
            UmlNode leftNode = this.findNodeById(this.mRelations.get(i).entry1_id);
            UmlNode rightNode = this.findNodeById(this.mRelations.get(i).entry2_id);

            if (this.mRelations.get(i).type.equals("PPrel")) {
                if (!rightNode.linkedId.contains(Integer.parseInt(leftNode.getID().toString()))) {
                    Link link = new Link("link" + (char) this.links.size() + 1, leftNode, rightNode);
                    this.SetLinkType(link, this.mRelations.get(i));
                    this.links.add(link);
                    box.addElement(link);
                    rightNode.linkedId.add(Integer.parseInt(leftNode.getID().toString()));
                    leftNode.linkedId.add(Integer.parseInt(rightNode.getID().toString()));
                }
            }
        }
    }

    /**
     * Draw all connections in the network diagram
     */
    private void DrawLinks() {
        this.DrawRelationsPCrelAndMapLink();
        this.DrawRelationPPrel();
        for (int i = 0; i < this.mReactions.size(); i++) {
            Reaction rea = this.mReactions.get(i);
            if (rea.mSubstrate != null) {
                for (int j = 0; j < rea.mSubstrate.size(); j++) {
                    UmlNode leftNode = this.findNodeById(rea.id);
                    UmlNode rightNode = this.findNodeById(rea.mSubstrate.get(j).id);
                    Link link = new Link("link" + (char) this.links.size() + 1, leftNode, rightNode);
                    this.SetLinkType(link, this.mReactions.get(i));
                    this.links.add(link);
                    box.addElement(link);
                }
            }
            if (rea.mProduct != null) {
                for (int j = 0; j < rea.mProduct.size(); j++) {
                    UmlNode leftNode = this.findNodeById(rea.id);
                    UmlNode rightNode = this.findNodeById(rea.mProduct.get(j).id);
                    Link link = new Link("link" + (char) this.links.size() + 1, leftNode, rightNode);
                    this.SetLinkType(link, this.mReactions.get(i));
                    this.links.add(link);
                    box.addElement(link);
                }
            }
        }
    }

    /**
     * using name to find node
     * @param name the name need to be find
     * @return return UmlNodes ,UmlNode name or its entry's name matching the name
     * �Return null representatives not find
     */
    public List<UmlNode> FindNodeByName(String name) {
        List<UmlNode> nodelist = new ArrayList<UmlNode>();
        name = name.toLowerCase();//all converted to lowercase
        for (UmlNode node : nodes) ///Query node name and the name of the node in the node matches�������Ƿ�ƥ��
        {
            if (node.getName().toLowerCase().indexOf(name) != -1/*||node.mEntry.name.toLowerCase().indexOf(name)!=-1*/) {
                nodelist.add(node);
            }
        }
        return nodelist;
    }

    /**
     * Painting network, set the network diagram right-click menu
     */
    public void DrawNetwork() {
        network = new TNetwork(box);
        network.getToolbar().setBackground(new java.awt.Color(255, 165, 0));
        networkPane.add(network, BorderLayout.CENTER);

        //Set the pop-up menu
        network.setPopupMenuGenerator(new PopupMenuGenerator() {

            @Override
            public JPopupMenu generate(TView arg0, MouseEvent arg1) {
                JPopupMenu menu = new JPopupMenu();
                //OpenFile menu option
                JMenuItem fileOpen = new JMenuItem("open KEGG xml");
                fileOpen.addActionListener(new ActionListener() {

                    @SuppressWarnings("deprecation")
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "KEGG XML Files", "xml");
                        chooser.setFileFilter(filter);
                        int returnVal = chooser.showOpenDialog(network);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            KGMLReader.this.ClearAll();
                            KGMLReader.this.mFileName = chooser.getSelectedFile().getAbsolutePath();
                            if (KGMLReader.this.mFrame != null) {
                                KGMLReader.this.mFrame.setTitle(KGMLReader.this.mFileName);
                                KGMLReader.this.mFrame.show();
                            }
                            KGMLReader.this.ReadXML(KGMLReader.this.mFileName);
                            KGMLReader.this.DrawNetwork();
                        }
//                        SaveAndOpenFileDialog f = new SaveAndOpenFileDialog("keggxml", "*.xml");
//                        KGMLReader.this.mFileName = f.showOpenDataDialog();
//                        if(KGMLReader.this.mFileName==null)
//                            return;
//                        if (!KGMLReader.this.mFileName.equals("")) {
//                            KGMLReader.this.ClearAll();
//                            KGMLReader.this.ReadXML(KGMLReader.this.mFileName);                            
//                            KGMLReader.this.DrawNetwork();
//                        }
                    }
                });
                //Find elements menu item
                JMenuItem findElement = new JMenuItem("find element");
                findElement.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        String toFind = JOptionPane.showInputDialog("Please input element name");
                        List<UmlNode> nodeList = KGMLReader.this.FindNodeByName(toFind);
                        if (nodeList.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Can't find element", "Can't find element", JOptionPane.ERROR_MESSAGE);
                        } else {
                            KGMLReader.this.AllPreNodeReset();
                            for (UmlNode node : nodeList) {
                                KGMLReader.this.preFindedNodes.add(node);
                                node.putCustomDrawFillColor(Color.PINK);
                            }
                        }
                    }
                });
                menu.add(fileOpen);
                menu.add(findElement);
                return menu;
            }
        });
        this.DrawEntrys();
        this.DrawLinks();
        //network.doLayout(TWaverConst.LAYOUT_HIERARCHIC);
        //network.doLayout(TWaverConst.LAYOUT_SYMMETRIC);
        //network.doLayout(TWaverConst.LAYOUT_EAST);
        //Set the network mouse wheel event
        network.getCanvas().addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0) {
                if (arg0.getWheelRotation() == -1) {
                    network.getZoomer().zoomIn();//zoomIn
                } else {
                    network.getZoomer().zoomOut();//zoomOut
                }
            }
        });
    }
//    public static void main(String[] args) throws Exception {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            public void run() {
//                UIManager.put("Label.font", font);
//                UIManager.put("Button.font", font);
//                UIManager.put("RadioButton.font", font);
//                UIManager.put("CheckBox.font", font);
//                UIManager.put("TextField.font", font);
//                JFrame frame = new JFrame();
//                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//                frame.setSize(500, 300);
//                TWaverUtil.centerWindow(frame);
//                frame.setVisible(true);
//                SaveAndOpenFileDialog f = new SaveAndOpenFileDialog("keggxml", "*.xml");
//                String xmlPath = f.showOpenDataDialog();
//                if (!xmlPath.equals("")) {
//                    JPanel panel = (JPanel) frame.getContentPane();
//                    KGMLReader kGMLReader = new org.guokeggxml.KGMLReader(xmlPath, (JPanel) frame.getContentPane());
//                    panel.revalidate();
//                }
//            }
//        });
//    }
}
