package com.demkom58.lab6.view;

import com.demkom58.lab6.model.Wood;
import com.demkom58.lab6.store.WoodDirectory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DlgWood extends JDialog implements IWoodDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea;
    private JTextField textFieldId;
    private JTextField textFieldName;
    private JTextField textFieldDensity;

    private WoodDirectory woodDirectory;

    public DlgWood() {
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
        final int id = Integer.parseInt(textFieldId.getText());
        final String name = textFieldName.getText();
        final float density = Float.parseFloat(textFieldDensity.getText());
        woodDirectory.add(new Wood(id, name, density));
        textArea.setText(woodDirectory.toString());
    }

    private void onCancel() {
        setVisible(false);
    }

    @Override
    public Object getObject() {
        return null;
    }

    @Override
    public void setWoodDirectory(WoodDirectory woodDirectory) {
        this.woodDirectory = woodDirectory;
        textArea.setText(woodDirectory.toString());
    }

    @Override
    public String toString() {
        return "Wood";
    }
}
