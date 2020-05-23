package com.demkom58.rgr1.model;

import com.demkom58.rgr1.view.CountryDlg;
import com.demkom58.rgr1.view.DistrictDlg;
import com.demkom58.rgr1.view.Dlg;
import com.demkom58.rgr1.view.RegionDlg;
import org.jetbrains.annotations.NotNull;

public class Region extends AnyData {
    private String governor;
    private int square;
    private String adminCenter;

    public Region(@NotNull final String name,
                  @NotNull final String governor,
                  final int square,
                  @NotNull final String adminCenter) {
        super(name);
        this.governor = governor;
        this.square = square;
        this.adminCenter = adminCenter;
    }

    public int getSquare() {
        return square;
    }

    /**
     * Getter for admin center of region.
     * @return not null value of admin center.
     */
    public String getAdminCenter() {
        return adminCenter;
    }

    /**
     * Getter for governor of region.
     * @return not null value of governor's name.
     */
    public String getGovernor() {
        return governor;
    }

    /**
     * Shows dialog of data to user.
     *
     * @param editable true if it is editable.
     * @return dialog object
     */
    @Override
    public RegionDlg showDialog(boolean editable) {
        final RegionDlg dlg = new RegionDlg(this);
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
    public DistrictDlg showSonDialog() {
        final DistrictDlg dlg = new DistrictDlg();
        dlg.setVisible(true);
        return dlg;
    }
}
