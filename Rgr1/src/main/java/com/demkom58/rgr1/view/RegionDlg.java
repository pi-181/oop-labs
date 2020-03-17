package com.demkom58.rgr1.view;

import com.demkom58.rgr1.model.AnyData;
import com.demkom58.rgr1.model.Region;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;

public class RegionDlg extends Dlg {
    protected JPanel contentPane;

    protected JButton buttonOK;
    protected JButton buttonCancel;

    private JTextField nameField;
    private JTextField governorField;
    private JTextField squareField;
    private JTextField adminCenterField;

    public RegionDlg() {
        setup(contentPane, buttonOK, buttonCancel);
    }

    public RegionDlg(Region region) {
        this();

        nameField.setText(region.getName());
        governorField.setText(region.getGovernor());
        squareField.setText(String.valueOf(region.getSquare()));
        adminCenterField.setText(region.getAdminCenter());
    }

    @Override
    @Nullable
    public AnyData createData() throws Exception {
        if (!isOk())
            return null;

        return new Region(
                nameField.getText(),
                governorField.getText(),
                Integer.parseInt(squareField.getText()),
                adminCenterField.getText()
        );
    }
}
