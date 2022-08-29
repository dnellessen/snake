package com.dnellessen;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    private static int WIDTH;
    private static int HEIGHT;
    private static int DELAY = 150;
    private static int NUM_OF_SQUARES;
    private static final int SQUARE_SIZE = 20;

    private int x[];
    private int y[];

    int score = 0;
    int snakeLength = 10;
    char direction = 'e';
    boolean isRunning;

    private Timer timer;
    private Random random;

    public Panel(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        NUM_OF_SQUARES = (WIDTH * HEIGHT) / SQUARE_SIZE;
        x = new int[NUM_OF_SQUARES];
        y = new int[NUM_OF_SQUARES];

        random = new Random();

        this.setFocusable(true);    // focus on panel to be able to listen to keys
        this.addKeyListener(new SnakeKeyListener());

        start();
    }

    private void start() {
        isRunning = true;
        setStartingPos();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void setStartingPos() {
        int startX = random.nextInt(WIDTH);
        int startY = random.nextInt(HEIGHT);

        for (int i = 0; i < snakeLength; i++) {
            x[i] = startX;
            y[i] = startY;
        }
    }

    private void move() {
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'n':
                y[0] -= SQUARE_SIZE;
                break;
            case 'e':
                x[0] += SQUARE_SIZE;
                break;
            case 's':
                y[0] += SQUARE_SIZE;
                break;
            case 'w':
                x[0] -= SQUARE_SIZE;
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < snakeLength; i++) {
            g.setColor(Color.black);
            g.fillRect(x[i], y[i], SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
        }
        repaint();
    }

    public class SnakeKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    direction = 'n';
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = 'e';
                    break;
                case KeyEvent.VK_DOWN:
                    direction = 's';
                    break;
                case KeyEvent.VK_LEFT:
                    direction = 'w';
                    break;
            }
        }
    }
}
