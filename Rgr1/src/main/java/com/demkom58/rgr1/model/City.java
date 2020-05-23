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

    public int getPopulation() {
        return population;
    }

    public String getMayor() {
        return mayor;
    }

    @Override
    public CityDlg showDialog(boolean editable) {
        final CityDlg dlg = new CityDlg(this);
        dlg.setEditable(editable);
        dlg.setVisible(true);
        return dlg;
    }

    @Override
    public Dlg<AnyData> showSonDialog() {
        return null;
    }
}
