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
    int snakeLength = 15;
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
        setStartingDir();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void setStartingPos() {
        int squaresWidth = WIDTH / SQUARE_SIZE;
        int squaresHeight= HEIGHT / SQUARE_SIZE;

        int minWidth = squaresWidth / 4;
        int maxWidth = squaresWidth - minWidth - 1;
        int minHeight = squaresHeight / 4;
        int maxHeight = squaresHeight - minHeight - 1;

        int stepsX = random.nextInt(maxWidth - minWidth) + minWidth;
        int stepsY = random.nextInt(maxHeight - minHeight) + minHeight;

        for (int i = 0; i < snakeLength; i++) {
            x[i] = stepsX * SQUARE_SIZE;
            y[i] = stepsY * SQUARE_SIZE;
        }
    }

    private void setStartingDir() {
        int dirIndex = random.nextInt(4);
        char directions[] = {'n', 'e', 's', 'w'};
        direction = directions[dirIndex];
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

    protected boolean checkSnakeCollision() {
        // self
        for (int i = 1; i < snakeLength; i++)
            if (x[0] == x[i] && y[0] == y[i])
                return false;

        // left
        if (x[0] < 0) 
            return false;

        // right
        if (x[0] > (WIDTH - SQUARE_SIZE)) 
            return false;

        // top
        if (y[0] < 0) 
            return false;

        // bottom
        if (y[0] > (HEIGHT - SQUARE_SIZE*2)) 
            return false;

        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i < snakeLength; i++) {
            g.fillRect(x[i], y[i], SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            isRunning = checkSnakeCollision();
        }
        repaint();
    }

    public class SnakeKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (direction != 's') direction = 'n';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'w') direction = 'e';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'n') direction = 's';
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'e') direction = 'w';
                    break;
            }
        }
    }
}
