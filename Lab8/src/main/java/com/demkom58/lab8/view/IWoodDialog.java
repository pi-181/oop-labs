package com.demkom58.lab8.view;

import com.demkom58.lab8.store.WoodDirectory;

public interface IWoodDialog {

    void setVisible(boolean visible);

    Object getObject();

    void setWoodDirectory(WoodDirectory woodDirectory);

}
