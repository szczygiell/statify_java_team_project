package com.statify;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel{

    private Color color1, color2;
    private int width;
    private int height;

    public GradientPanel(){
        super();
//        this.color1 = new java.awt.Color(25,20,20);
        this.color1 = Color.black;
        this.color2 = new java.awt.Color(29,185,84);
    }

    public GradientPanel(int width, int height){
        super();
//        this.color1 = new java.awt.Color(25,20,20);
        // this.color1 = Color.black;
        this.color1 = Color.DARK_GRAY;
        this.color2 = new java.awt.Color(29,185,84);
        this.width = width;
        this.height = height;

    }

    @Override
    protected void paintComponent(Graphics g) {
//        public void paintComponents(Graphics g) {
        super.paintComponent(g);

        Graphics2D graph2d = (Graphics2D) g;
        graph2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        GradientPaint gradientPaint;

        // #horizontal
        // gradientPaint = new GradientPaint(0, 330, color1, 370, 330, color2);

        // #digonal
        // gradientPaint = new GradientPaint(0, 660, color1, 370, 0, color2);
        gradientPaint = new GradientPaint(0, this.height, color1, this.width, 0, color2);

        graph2d.setPaint(gradientPaint);
        graph2d.fillRect(0, 0, this.width, this.height);

    }
}

// https://www.youtube.com/watch?v=ewxDItWfwUc
