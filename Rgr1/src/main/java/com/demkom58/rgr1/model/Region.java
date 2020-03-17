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

    public String getAdminCenter() {
        return adminCenter;
    }

    public String getGovernor() {
        return governor;
    }

    @Override
    public Dlg showDialog(boolean editable) {
        final Dlg dlg = new RegionDlg(this);
        dlg.setEditable(editable);
        dlg.setVisible(true);
        return dlg;
    }

    @Override
    public Dlg showSonDialog() {
        final Dlg dlg = new DistrictDlg();
        dlg.setVisible(true);
        return dlg;
    }
}
