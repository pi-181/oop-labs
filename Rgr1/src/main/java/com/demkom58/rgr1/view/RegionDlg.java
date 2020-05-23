package com.demkom58.rgr1.view;

import com.demkom58.rgr1.model.Region;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RegionDlg extends Dlg<Region> {
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

    /**
     * @return user data entered in dialog window in dialog data class representation.
     */
    @Override
    @Nullable
    public Region createData() throws Exception {
        if (!isOk())
            return null;

        if (nameField.getText().isEmpty()) throw new IllegalArgumentException("Name of region can't be empty!");
        if (governorField.getText().isEmpty()) throw new IllegalArgumentException("Name of Governor can't be empty!");
        if (adminCenterField.getText().isEmpty()) throw new IllegalArgumentException("Name of admin center can't be empty!");

        return new Region(
                nameField.getText(),
                governorField.getText(),
                Integer.parseInt(squareField.getText()),
                adminCenterField.getText()
        );
    }
}
