package com.statify;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.ListCellRenderer;
import javax.swing.JList;

public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        setComponentOrientation(list.getComponentOrientation());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setSelected(isSelected);
        setEnabled(list.isEnabled());
        setText(value == null ? "" : value.toString());

        if (isSelected) {
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());
        } else {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }


        return this;
    }

    private static java.util.ArrayList<String> getSelectedItems(JList<String> list) {
        java.util.ArrayList<String> selectedItems = new java.util.ArrayList<>();
        int[] selectedIndices = list.getSelectedIndices();
        for (int index : selectedIndices) {
            selectedItems.add(list.getModel().getElementAt(index));
        }
        return selectedItems;
    }

}