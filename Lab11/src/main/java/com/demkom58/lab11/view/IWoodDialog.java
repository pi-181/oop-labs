package com.demkom58.lab11.view;

import com.demkom58.lab11.store.WoodDirectory;

public interface IWoodDialog {

    void setVisible(boolean visible);

    Object getObject();

    void setWoodDirectory(WoodDirectory woodDirectory);

}
