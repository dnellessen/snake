package com.dnellessen;

import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

public class Panel extends JComponent{
    private static int WIDTH;
    private static int HEIGHT;

    private static final int PIXEL_SIZE = 20;

    public Panel(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        smoothOutShapes(g2d);
        drawShapes(g2d);

    }

    private void drawShapes(Graphics2D g2d) {
        Rectangle2D.Double background = new Rectangle2D.Double(
            (WIDTH / 2) - PIXEL_SIZE / 2, 
            (HEIGHT / 2) - PIXEL_SIZE / 2, 
            PIXEL_SIZE, 
            PIXEL_SIZE
        );
        g2d.setColor(Color.BLACK);
        g2d.fill(background);
    }

    private void smoothOutShapes(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);
    }
}
