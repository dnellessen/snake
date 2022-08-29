package com.dnellessen;

import javax.swing.JFrame;

public class Frame {
    private static final String TITLE = "Snake";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 608;

    private static JFrame frame;
    
    public Frame() {
        initFrame();
    }

    private static void initFrame() {
        frame = new JFrame();
    
        frame.setTitle(TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        
        frame.add(new Panel(WIDTH, HEIGHT));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }    
}
