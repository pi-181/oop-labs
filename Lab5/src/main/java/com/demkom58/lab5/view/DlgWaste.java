package com.demkom58.lab5.view;

import com.demkom58.lab5.model.Waste;
import com.demkom58.lab5.store.WoodDirectory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DlgWaste extends JDialog implements IWoodDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textAreaWeight;

    private Waste waste;

    public DlgWaste() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setSize(400, 200);
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
        final float weight = Float.parseFloat(textAreaWeight.getText());
        try {
            waste = new Waste(weight);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Waste dialog error", JOptionPane.ERROR_MESSAGE);
            waste = null;
        }
        setVisible(false);

    }

    private void onCancel() {
        waste = null;
        setVisible(false);
    }

    @Override
    public Object getObject() {
        return waste;
    }

    @Override
    public void setWoodDirectory(WoodDirectory woodDirectory) {

    }

    @Override
    public String toString() {
        return "Waste";
    }
}
