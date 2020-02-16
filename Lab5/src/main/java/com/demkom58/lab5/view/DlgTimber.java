package com.demkom58.lab5.view;

import com.demkom58.lab5.model.Timber;
import com.demkom58.lab5.model.Wood;
import com.demkom58.lab5.store.WoodDirectory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DlgTimber extends JDialog implements IWoodDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<Wood> comboBoxWood;
    private JTextField textFieldLength;
    private JTextField textFieldWidth;
    private JTextField textFieldHeight;

    private Timber timber;

    public DlgTimber() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setSize(400, 400);
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
        final float length = Float.parseFloat(textFieldLength.getText());
        final float width = Float.parseFloat(textFieldWidth.getText());
        final float height = Float.parseFloat(textFieldHeight.getText());
        final Wood wood = (Wood) comboBoxWood.getSelectedItem();

        timber = new Timber(wood, length, height, width);
        setVisible(false);
    }

    private void onCancel() {
        timber = null;
        setVisible(false);
    }

    public static void main(String[] args) {
        DlgTimber dialog = new DlgTimber();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    @Override
    public Object getObject() {
        return timber;
    }

    @Override
    public void setWoodDirectory(WoodDirectory woodDirectory) {
        comboBoxWood.setModel(new DefaultComboBoxModel<>(woodDirectory.getArr()));
    }

    @Override
    public String toString() {
        return "Timber";
    }
}
