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

    /**
     * Getter for capital.
     * @return not null value capital name.
     */
    public String getCapital() {
        return capital;
    }

    /**
     * Getter for president.
     * @return not null value of president's name.
     */
    public String getPresident() {
        return president;
    }

    /**
     * Shows dialog of data to user.
     *
     * @param editable true if it is editable.
     * @return dialog object
     */
    @Override
    public CountryDlg showDialog(boolean editable) {
        final CountryDlg dlg = new CountryDlg(this);
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
    public RegionDlg showSonDialog() {
        final RegionDlg dlg = new RegionDlg();
        dlg.setVisible(true);
        return dlg;
    }
}
