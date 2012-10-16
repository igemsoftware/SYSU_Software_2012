import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
public class ShowBmp extends JInternalFrame{
	public ShowBmp(String bmpFile,final JDesktopPane parent){
        super(bmpFile,true,true,true,true);
        Image image =null;
        try{
            image=ImageIO.read(new File(bmpFile));
        }catch(IOException ex){        	
        }        
        JPanel panel=new JPanel();//(JPanel) frame.getContentPane();		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollPane=new JScrollPane(panel);		
        JLabel label =new JLabel(new ImageIcon(image));
        panel.add(label);
        scrollPane.setViewportView(panel); 
		this.getContentPane().add(scrollPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        parent.add(this);
        pack();
        RJavaFrame.deleteFile(bmpFile);
    }
//	public ShowBmp(String bmpFile,final String bmpfile2,final JDesktopPane parent){
//        super(bmpFile,true,true,true,true); 
//        parent.add(this);
//        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        ShowBmp nextBmp=new ShowBmp(bmpfile2,parent);
//        nextBmp.setVisible(true);
//        nextBmp.pack();		   
//        pack();
//    }    
}
