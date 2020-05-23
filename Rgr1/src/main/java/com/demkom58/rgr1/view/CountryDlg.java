package com.demkom58.rgr1.view;

import com.demkom58.rgr1.model.AnyData;
import com.demkom58.rgr1.model.Country;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CountryDlg extends Dlg<Country> {
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


    /**
     * @return user data entered in dialog window in dialog data class representation.
     */
    @Override
    @Nullable
    public Country createData() throws Exception {
        if (!isOk())
            return null;

        if (nameField.getText().isEmpty()) throw new IllegalArgumentException("Name of city can't be empty!");
        if (presidentField.getText().isEmpty()) throw new IllegalArgumentException("Name of president can't be empty!");
        if (capitalField.getText().isEmpty()) throw new IllegalArgumentException("Name of capital can't be empty!");

        return new Country(
                nameField.getText(),
                presidentField.getText(),
                capitalField.getText()
        );
    }
}
