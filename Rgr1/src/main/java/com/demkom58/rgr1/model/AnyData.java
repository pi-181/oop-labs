package com.demkom58.rgr1.model;

import com.demkom58.rgr1.view.Dlg;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public abstract class AnyData implements Serializable {
    protected String name;

    public AnyData(@NotNull final String name) {
        this.name = name;
    }

    /**
     * Getter for data name that can't be null
     * @return not null value of object name.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Convert object in readable string for user.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Shows dialog of data to user.
     *
     * @param editable true if it is editable.
     * @return dialog object
     */
    public abstract Dlg<? extends AnyData> showDialog(boolean editable);

    /**
     * Shows son dialog, if it exist.
     *
     * @return null if dialog not exist else dialog of son.
     */
    public abstract Dlg<? extends AnyData> showSonDialog();

}
