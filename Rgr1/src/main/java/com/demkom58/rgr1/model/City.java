package com.demkom58.rgr1.model;

import com.demkom58.rgr1.view.CityDlg;
import com.demkom58.rgr1.view.Dlg;
import org.jetbrains.annotations.NotNull;

public class City extends AnyData {
    private int population;
    private String mayor;

    public City(@NotNull final String name,
                int population,
                @NotNull final String mayor) {
        super(name);
        this.population = population;
        this.mayor = mayor;
    }

    /**
     * @return population value of city.
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Getter for mayor's name.
     * @return not null value of mayor's name.
     */
    public String getMayor() {
        return mayor;
    }

    /**
     * Shows dialog of data to user.
     *
     * @param editable true if it is editable.
     * @return dialog object
     */
    @Override
    public CityDlg showDialog(boolean editable) {
        final CityDlg dlg = new CityDlg(this);
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
    public Dlg<AnyData> showSonDialog() {
        return null;
    }
}
