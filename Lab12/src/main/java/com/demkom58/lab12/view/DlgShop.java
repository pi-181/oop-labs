package com.demkom58.lab12.view;

import com.demkom58.lab12.model.IWeight;
import com.demkom58.lab12.store.ProductStore;
import com.demkom58.lab12.store.WoodDirectory;
import com.demkom58.lab12.thread.CylinderShop;
import com.demkom58.lab12.thread.TimberShop;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DlgShop extends JDialog {
    private JPanel contentPane;

    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField cylinderTimeField;
    private JTextField timberTimeField;
    private JTextField totalTimeField;

    private final List<String> shopHistory = Collections.synchronizedList(new ArrayList<>());
    private final WoodDirectory woodDirectory;
    private final ProductStore<IWeight> productStore;

    private final Runnable updateTextArea;

    public DlgShop(WoodDirectory woodDirectory, ProductStore<IWeight> productStore,
                   Runnable updateTextArea) {
        this.woodDirectory = woodDirectory;
        this.productStore = productStore;
        this.updateTextArea = updateTextArea;

        setSize(350, 200);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        final Integer cylinderTime = getIntegerField(cylinderTimeField);
        final Integer timberTime = getIntegerField(timberTimeField);
        final Integer totalTime = getIntegerField(totalTimeField);
        if (cylinderTime == null || timberTime == null || totalTime == null)
            return;

        final Consumer<String> logger = s -> {
            shopHistory.add(s);
            System.out.println(s);
            updateTextArea.run();
        };

        final CylinderShop cylinderShop
                = new CylinderShop("Cylinder Shop", woodDirectory, productStore, totalTime, cylinderTime, logger);
        final Thread cylThread = new Thread(cylinderShop);
        cylThread.setDaemon(true);
        cylThread.start();

        final TimberShop timberShop
                = new TimberShop("Timber Shop", woodDirectory, productStore, totalTime, timberTime, logger);
        final Thread timberThread = new Thread(timberShop);
        timberThread.setDaemon(true);
        timberThread.start();

        setVisible(false);
    }

    private void onCancel() {
        setVisible(false);
    }

    public List<String> getShopHistory() {
        return shopHistory;
    }

    @Nullable
    private Integer getIntegerField(JTextField field) {
        final String text = field.getText();
        try {
            return (int) Float.parseFloat(text) * 1000;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    text + " is invalid value.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
