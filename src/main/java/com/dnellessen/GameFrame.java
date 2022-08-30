package com.dnellessen;

import javax.swing.JFrame;

public class GameFrame {
    private static final String TITLE = "Snake";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 608;

    private static JFrame frame;
    
    public GameFrame() {
        init();
    }

    private static void init() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        frame.setTitle(TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        
        frame.add(new GamePanel(WIDTH, HEIGHT));
    }   
    
    public void start() {
        frame.setVisible(true);
    }
}
