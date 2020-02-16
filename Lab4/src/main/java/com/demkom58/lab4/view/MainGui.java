package com.demkom58.lab4.view;

import javax.swing.*;

public class MainGui extends JFrame {
    private JPanel rootPanel;
    private JFormattedTextField helloWorldInput;

    public MainGui() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(360, 240);
        setLocationRelativeTo(null);
        setTitle("Wood Program");
    }

}
