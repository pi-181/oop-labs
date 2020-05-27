package com.demkom58.ex1;

import com.demkom58.ex1.view.MainView;

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
                final MainView view = new MainView();
                view.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
