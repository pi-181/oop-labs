package com.demkom58.lab9.view;

import com.demkom58.lab9.model.Cylinder;
import com.demkom58.lab9.model.Wood;
import com.demkom58.lab9.store.WoodDirectory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DlgCylinder extends JDialog implements IWoodDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<Wood> comboBoxWood;
    private JTextField textFieldDiameter;
    private JTextField textFieldHeight;

    private Cylinder cylinder;

    public DlgCylinder() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setSize(400, 300);
        setLocationRelativeTo(null);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void onOK() {
        final float diameter = Float.parseFloat(textFieldDiameter.getText());
        final float height = Float.parseFloat(textFieldHeight.getText());
        final Wood wood = (Wood) comboBoxWood.getSelectedItem();

        try {
            cylinder = new Cylinder(wood, height, diameter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Cylinder dialog error", JOptionPane.ERROR_MESSAGE);
            cylinder = null;
        }

        setVisible(false);
    }

    private void onCancel() {
        cylinder = null;
        setVisible(false);
    }

    @Override
    public Object getObject() {
        return cylinder;
    }

    @Override
    public void setWoodDirectory(WoodDirectory woodDirectory) {
        comboBoxWood.setModel(new DefaultComboBoxModel<>(woodDirectory.getArr()));
    }

    @Override
    public String toString() {
        return "Cylinder";
    }
}
