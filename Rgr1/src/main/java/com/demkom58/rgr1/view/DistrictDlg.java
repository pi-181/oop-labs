package com.demkom58.rgr1.view;

import com.demkom58.rgr1.model.AnyData;
import com.demkom58.rgr1.model.District;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;

public class DistrictDlg extends Dlg<District> {
    protected JPanel contentPane;

    protected JButton buttonOK;
    protected JButton buttonCancel;

    private JTextField nameField;
    private JTextField villageCouncilsField;
    private JTextField adminCenterField;

    public DistrictDlg() {
        setup(contentPane, buttonOK, buttonCancel);
    }

    public DistrictDlg(District district) {
        this();

        nameField.setText(district.getName());
        villageCouncilsField.setText(String.valueOf(district.getVillageCouncils()));
        adminCenterField.setText(district.getAdminCenter());
    }

    @Override
    @Nullable
    public District createData() throws Exception {
        if (!isOk())
            return null;

        return new District(
                nameField.getText(),
                Integer.parseInt(villageCouncilsField.getText()),
                adminCenterField.getText()
        );
    }
}