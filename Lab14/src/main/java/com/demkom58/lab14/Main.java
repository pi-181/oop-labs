package com.demkom58.lab14;

import com.demkom58.lab14.graph.TestGraph;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            final TestGraph testGraph = new TestGraph();
            testGraph.setVisible(true);
        });
    }
}
