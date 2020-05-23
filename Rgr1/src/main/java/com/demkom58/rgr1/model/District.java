package com.demkom58.rgr1.model;

import com.demkom58.rgr1.view.*;
import org.jetbrains.annotations.NotNull;

public class District extends AnyData {
    private int villageCouncils;
    private String adminCenter;

    public District(@NotNull final String name,
                    int villageCouncils,
                    @NotNull final String adminCenter) {
        super(name);
        this.villageCouncils = villageCouncils;
        this.adminCenter = adminCenter;
    }

    public int getVillageCouncils() {
        return villageCouncils;
    }

    public String getAdminCenter() {
        return adminCenter;
    }

    @Override
    public DistrictDlg showDialog(boolean editable) {
        final DistrictDlg dlg = new DistrictDlg(this);
        dlg.setEditable(editable);
        dlg.setVisible(true);
        return dlg;
    }

    @Override
    public CityDlg showSonDialog() {
        final CityDlg dlg = new CityDlg();
        dlg.setVisible(true);
        return dlg;
    }
}
