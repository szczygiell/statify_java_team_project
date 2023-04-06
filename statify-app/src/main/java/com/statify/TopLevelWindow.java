package com.statify;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class TopLevelWindow {
    public static void createChartFrame(JPanel chart) {
        JFrame frame = new JFrame();

        frame.add(chart);
        frame.pack();
        frame.setVisible(true);
    }
}
