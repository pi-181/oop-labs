package com.demkom58.rgr1;

import com.demkom58.rgr1.view.MainGui;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // set default system theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
