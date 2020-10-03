package com.demkom58.lab12;

import com.demkom58.lab12.view.MainGui;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
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
