import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JButton;

/**
 *
 * @author Chel
 */
public class GridBagLayoutDemo {

    public static void main(String[] args) {
        JFrame f=new JFrame("GridBagLayout");
        f.setLayout(new GridBagLayout());
        JButton btn=new JButton("first");
        GridBagConstraints gbc=new GridBagConstraints();
        //设定第一个单元格的属性值
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.weightx=0;
        gbc.weighty=0;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        gbc.fill=GridBagConstraints.NONE;
        gbc.insets=new Insets(0,0,0,0);
        gbc.ipadx=0;
        gbc.ipady=0;
        f.add(btn,gbc);

        //设定第二个单元格属性值
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=GridBagConstraints.REMAINDER;
        gbc.gridheight=1;
        gbc.weightx=1;
        gbc.weighty=0;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.insets=new Insets(5,5,5,5);
        gbc.ipadx=0;
        gbc.ipady=0;
        btn=new JButton("second");
        f.add(btn,gbc);

        //设定第三个单元格属性值
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.gridheight=GridBagConstraints.REMAINDER;
        gbc.weightx=0;
        gbc.weighty=1;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.fill=GridBagConstraints.VERTICAL;
        gbc.insets=new Insets(0,0,0,0);
        gbc.ipadx=10;
        gbc.ipady=10;
        btn=new JButton("three");
        f.add(btn,gbc);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
