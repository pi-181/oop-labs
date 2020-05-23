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

    /**
     * Getter for admin canter.
     * @return not null value of admin center of district.
     */
    public String getAdminCenter() {
        return adminCenter;
    }

    /**
     * Shows dialog of data to user.
     *
     * @param editable true if it is editable.
     * @return dialog object
     */
    @Override
    public DistrictDlg showDialog(boolean editable) {
        final DistrictDlg dlg = new DistrictDlg(this);
        dlg.setEditable(editable);
        dlg.setVisible(true);
        return dlg;
    }

    /**
     * Shows son dialog, if it exist.
     *
     * @return null if dialog not exist else dialog of son.
     */
    @Override
    public CityDlg showSonDialog() {
        final CityDlg dlg = new CityDlg();
        dlg.setVisible(true);
        return dlg;
    }
}
