package com.demkom58.lab4.view;

import com.demkom58.lab4.store.WoodDirectory;

public interface IWoodDialog {

    void setVisible(boolean visible);

    Object getObject();

    void setWoodDirectory(WoodDirectory woodDirectory);

}
