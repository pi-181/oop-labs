package com.demkom58.lab10.view;

import com.demkom58.lab10.store.WoodDirectory;

public interface IWoodDialog {

    void setVisible(boolean visible);

    Object getObject();

    void setWoodDirectory(WoodDirectory woodDirectory);

}
