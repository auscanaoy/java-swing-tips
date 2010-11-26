package example;
//-*- mode:java; encoding:utf8n; coding:utf-8 -*-
// vim:set fileencoding=utf-8:
//@homepage@
import java.awt.*;
import javax.swing.*;

//Swing - JTabbedPane with non-tabbed text
//http://forums.sun.com/thread.jspa?threadID=605786
public class MainPanel extends JPanel{
    private static final String text = "<--1234567890";
    public MainPanel() {
        super(new BorderLayout());
        JTabbedPane tab = new JTabbedPane() {
            @Override public void paintComponent(Graphics g) {
                super.paintComponent(g);
                FontMetrics fm = getFontMetrics(getFont());
                int stringWidth = fm.stringWidth(text)+10;
                int x = getSize().width-stringWidth;
                Rectangle lastTab = getUI().getTabBounds(this, getTabCount()-1);
                int tabEnd = lastTab.x + lastTab.width;
                if(x<tabEnd) x = tabEnd;
                g.drawString(text, x+5, 18);
            }
        };
        tab.addTab("title1", new JLabel("tab1"));
        tab.addTab("title2", new JLabel("tab2"));
        add(tab);
        setPreferredSize(new Dimension(320, 180));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                createAndShowGUI();
            }
        });
    }
    public static void createAndShowGUI() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("@title@");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
