package com.demkom58.rgr1.model;

import com.demkom58.rgr1.view.CountryDlg;
import com.demkom58.rgr1.view.Dlg;
import com.demkom58.rgr1.view.RegionDlg;
import org.jetbrains.annotations.NotNull;

public class Country extends AnyData {
    private String president;
    private String capital;

    public Country(@NotNull final String name,
                   @NotNull final String president,
                   @NotNull final String capital) {
        super(name);
        this.president = president;
        this.capital = capital;
    }

    public String getCapital() {
        return capital;
    }

    public String getPresident() {
        return president;
    }

    @Override
    public Dlg showDialog(boolean editable) {
        final Dlg dlg = new CountryDlg(this);
        dlg.setEditable(editable);
        dlg.setVisible(true);
        return dlg;
    }

    @Override
    public Dlg showSonDialog() {
        final Dlg dlg = new RegionDlg();
        dlg.setVisible(true);
        return dlg;
    }
}
