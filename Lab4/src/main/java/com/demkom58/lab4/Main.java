package com.demkom58.lab4;

import com.demkom58.lab4.view.MainGui;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                final MainGui gui = new MainGui();
                gui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
