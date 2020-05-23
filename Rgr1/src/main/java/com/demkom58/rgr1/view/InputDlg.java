package com.demkom58.rgr1.view;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Dialog single text field class.
 */
public class InputDlg extends Dlg<String> {
    protected JPanel contentPane;

    protected JButton buttonOK;
    protected JButton buttonCancel;

    private JLabel valueLabel;
    private JTextField valueField;

    private InputDlg() {
        setup(contentPane, buttonOK, buttonCancel);
    }

    public InputDlg(String label, String value) {
        this();
        valueLabel.setText(label);
        valueField.setText(value);
        setVisible(true);
    }

    /**
     * @return user data entered in dialog window
     */
    @Override
    @Nullable
    public String createData() {
        if (!isOk())
            return null;

        return valueField.getText();
    }

}