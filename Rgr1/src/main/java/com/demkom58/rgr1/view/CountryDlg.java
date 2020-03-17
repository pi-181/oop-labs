package com.demkom58.rgr1.view;

import com.demkom58.rgr1.model.AnyData;
import com.demkom58.rgr1.model.Country;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CountryDlg extends Dlg {
    protected JPanel contentPane;

    protected JButton buttonOK;
    protected JButton buttonCancel;

    private JTextField nameField;
    private JTextField presidentField;
    private JTextField capitalField;

    public CountryDlg() {
        setup(contentPane, buttonOK, buttonCancel);
    }

    public CountryDlg(Country country) {
        this();

        nameField.setText(country.getName());
        presidentField.setText(country.getPresident());
        capitalField.setText(country.getCapital());
    }

    @Override
    @Nullable
    public AnyData createData() throws Exception {
        if (!isOk())
            return null;

        return new Country(
                nameField.getText(),
                presidentField.getText(),
                capitalField.getText()
        );
    }
}
