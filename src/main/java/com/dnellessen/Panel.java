package com.dnellessen;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Random;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel extends JPanel implements ActionListener {
    private static int WIDTH;
    private static int HEIGHT;
    private static int DELAY = 150;
    private static int NUM_OF_SQUARES;
    private static final int SQUARE_SIZE = 20;

    private int x[];
    private int y[];

    int score = 0;
    JLabel scoreLabel;

    int snakeLength = 5;
    char direction;
    boolean isRunning;
    
    int appleX;
    int appleY;

    Color bgColor = new Color(238, 238, 238);
    Color snakeColor = Color.black;
    Color appleColor = new Color(150, 0, 0);

    private Timer timer;
    private Random random;

    public Panel(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        init();
        start();
    }

    private void init() {
        NUM_OF_SQUARES = (WIDTH * HEIGHT) / SQUARE_SIZE;
        x = new int[NUM_OF_SQUARES];
        y = new int[NUM_OF_SQUARES];

        random = new Random();

        this.setFocusable(true);    // focus on panel to be able to listen to keys
        this.addKeyListener(new SnakeKeyListener());

        scoreLabel = new JLabel();
        scoreLabel.setText(Integer.toString(score));
        this.add(scoreLabel);
    }

    private void start() {
        isRunning = true;
        setStartingPos();
        setStartingDir();
        addApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void setStartingPos() {
        int numOfSquaresWidth  = WIDTH / SQUARE_SIZE;
        int numOfSquaresHeight = HEIGHT / SQUARE_SIZE;

        int minWidth  = numOfSquaresWidth / 4;
        int maxWidth  = numOfSquaresWidth - minWidth - 1;
        int minHeight = numOfSquaresHeight / 4;
        int maxHeight = numOfSquaresHeight - minHeight - 1;

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

    private void gameOver() {
        DELAY = 250;
        timer.setDelay(DELAY);
        snakeColor = (snakeColor == Color.black) ? bgColor : Color.black;
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

    private boolean checkSnakeCollision() {
        // self
        for (int i = 1; i < snakeLength; i++)
            if (x[0] == x[i] && y[0] == y[i])
                return true;

        // left
        if (x[0] < 0) 
            return true;

        // right
        if (x[0] > (WIDTH - SQUARE_SIZE)) 
            return true;

        // top
        if (y[0] < 0) 
            return true;

        // bottom
        if (y[0] > (HEIGHT - SQUARE_SIZE*2)) 
            return true;

        return false;
    }

    private boolean snakeAteApple() {
        if (x[0] == appleX && y[0] == appleY)
            return true;

        return false;
    }

    private void addApple() {
        int numOfSquaresWidth  = (WIDTH  / SQUARE_SIZE) - 1;
        int numOfSquaresHeight = (HEIGHT / SQUARE_SIZE) - 1;

        appleX = random.nextInt(numOfSquaresWidth) * SQUARE_SIZE;
        appleY = random.nextInt(numOfSquaresHeight) * SQUARE_SIZE;
    }

    private void handleEatenApple() {
        snakeLength++;
        score += 10;
        if (DELAY > 30 && score % 4 == 0) {
            DELAY -= 10;
            timer.setDelay(DELAY);
        }
        scoreLabel.setText(Integer.toString(score));
        addApple();
    }

    @Override
    public void paintComponent(Graphics g) {
        // apple
        g.setColor(appleColor);
        g.fillRoundRect(appleX, appleY, SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE/2, SQUARE_SIZE/2);

        // snake
        g.setColor(snakeColor);
        for (int i = 0; i < snakeLength; i++) {
            g.fillRect(x[i], y[i], SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            isRunning = !checkSnakeCollision();
            if (snakeAteApple())
                handleEatenApple();
        } else {
            gameOver();
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
