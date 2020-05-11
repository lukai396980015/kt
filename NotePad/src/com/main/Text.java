package com.main;

import javax.swing.*;

/**
 * @author lukai
 * @TODO TODO自定义描述
 * @create 2019-11-29 15:01
 */
public class Text
{
    private JTextPane TextPane;

    private JPanel panel1;

    private JToolBar file;
    private static JMenu jMenu = new JMenu("文件");
    private static JMenu jMenu2 = new JMenu("测试2");
    private static JMenuItem jMenuItem = new JMenuItem("打开");
    private static JMenuItem jMenuItem2= new JMenuItem("另存");
    
    private static JMenuBar jMenuBar = new JMenuBar();

    public static void main(String[] args)
    {
        Text text = new Text();
        JFrame frame = new JFrame("Text");
        frame.setContentPane(text.panel1);
        text.file.add(jMenuBar);
        jMenuBar.add(jMenu);
        jMenu.add(jMenuItem);
        jMenu.add(jMenuItem2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
