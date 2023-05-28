package com.statify;

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
            checkBoxes[i] = new JCheckBox(objects[i].toString());
            checkBoxes[i].addActionListener(new CheckBoxListener(i));
            add(checkBoxes[i]);
        }
    }
    
    public Object[] selectedPlaylists(){
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

