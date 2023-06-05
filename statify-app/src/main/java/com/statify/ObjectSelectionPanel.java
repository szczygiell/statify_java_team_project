package com.statify;

import javax.servlet.http.Cookie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObjectSelectionPanel extends JPanel {
    private JCheckBox[] checkBoxes;
    private Object[] objects;
    private Object[] selectedObjects;

    public ObjectSelectionPanel(Object[] objects) {
        this.objects = objects;
        checkBoxes = new JCheckBox[objects.length];
        selectedObjects = new Object[objects.length];

        setLayout(new GridLayout(objects.length, 1));

        for (int i = 0; i < objects.length; i++) {
            JCheckBox checkBox = new JCheckBox(objects[i].toString());
            Color darkGreyColor = new Color(44, 51, 51);
            checkBox.setForeground(Color.WHITE);
            checkBox.setBackground(darkGreyColor);
            checkBox.setFont(new Font("Noto Sans CJK JP", Font.PLAIN, 15));
            checkBox.setAlignmentX(20);
            checkBox.setAlignmentY(15);
            checkBox.setBorder(BorderFactory.createMatteBorder(10, 20, 10, 0, Color.BLACK));
            checkBoxes[i] = checkBox;
            checkBoxes[i].addActionListener(new CheckBoxListener(i));
            add(checkBoxes[i]);
        }
    }

    public Object[] selectedPlaylists() {
        return getSelectedObjects();
    }

    private class CheckBoxListener implements ActionListener {
        private int index;

        public CheckBoxListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox source = (JCheckBox) e.getSource();
            selectedObjects[index] = source.isSelected() ? objects[index] : null;
        }
    }

    public Object[] getSelectedObjects() {
        return selectedObjects;
    }
}
