package com.demkom58.rgr1.view;

import com.demkom58.rgr1.model.City;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CityDlg extends Dlg<City> {
    protected JPanel contentPane;

    protected JButton buttonOK;
    protected JButton buttonCancel;

    private JTextField nameField;
    private JTextField populationField;
    private JTextField mayorField;

    public CityDlg() {
        setup(contentPane, buttonOK, buttonCancel);
    }

    public CityDlg(City city) {
        this();
        nameField.setText(city.getName());
        populationField.setText(String.valueOf(city.getPopulation()));
        mayorField.setText(city.getMayor());
    }

    @Override
    @Nullable
    public City createData() throws Exception {
        if (!isOk())
            return null;

        return new City(
                nameField.getText(),
                Integer.parseInt(populationField.getText()),
                mayorField.getText()
        );
    }
}