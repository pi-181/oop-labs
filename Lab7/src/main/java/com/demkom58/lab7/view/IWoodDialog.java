package com.demkom58.lab7.view;

import com.demkom58.lab7.store.WoodDirectory;

public interface IWoodDialog {

    void setVisible(boolean visible);

    Object getObject();

    void setWoodDirectory(WoodDirectory woodDirectory);

}
