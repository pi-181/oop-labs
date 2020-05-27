package com.demkom58.ex1.view;

import com.demkom58.ex1.TimeUtil;
import com.demkom58.ex1.model.Circle;
import com.demkom58.ex1.model.Rectangle;
import com.demkom58.ex1.model.Shape;
import com.demkom58.ex1.model.Triangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MainView extends JFrame {
    private JPanel rootPanel;
    private JTextArea outputArea;

    private JTextField circleDiameterField;
    private JButton circleButton;

    private JTextField rectangleHeightField;
    private JTextField rectangleWidthField;
    private JButton rectangleButton;

    private JTextField triangleHeightField;
    private JTextField triangleBaseField;
    private JButton triangleButton;

    private final List<Shape> shapes = new ArrayList<>();

    public MainView() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setTitle("ExTask");

        circleButton.addActionListener(this::onCircleAdd);
        rectangleButton.addActionListener(this::onRectangleAdd);
        triangleButton.addActionListener(this::onTriangleAdd);

        updateOutput();
    }

    private void onCircleAdd(ActionEvent e) {
        try {
            double diameter = Double.parseDouble(circleDiameterField.getText());
            Circle circle = new Circle(diameter);
            shapes.add(circle);
            updateOutput();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRectangleAdd(ActionEvent e) {
        try {
            double height = Double.parseDouble(rectangleHeightField.getText());
            double width = Double.parseDouble(rectangleWidthField.getText());
            Rectangle rectangle = new Rectangle(height, width);
            shapes.add(rectangle);
            updateOutput();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onTriangleAdd(ActionEvent e) {
        try {
            double height = Double.parseDouble(triangleHeightField.getText());
            double base = Double.parseDouble(triangleBaseField.getText());
            Triangle triangle = new Triangle(height, base);
            shapes.add(triangle);
            updateOutput();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOutput() {
        final StringJoiner joiner = new StringJoiner("\n");

        joiner.add("\tДата: " + TimeUtil.getNowDate() + ". Час: " + TimeUtil.getNowTime() + ".");
        shapes.forEach(shape -> joiner.add(shape.toString()));

        outputArea.setText(joiner.toString());
    }

}
