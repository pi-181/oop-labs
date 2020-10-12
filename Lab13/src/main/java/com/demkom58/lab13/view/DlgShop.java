package com.demkom58.lab13.view;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WasteStore;
import com.demkom58.lab13.store.WoodDirectory;
import com.demkom58.lab13.thread.CylinderShop;
import com.demkom58.lab13.thread.TimberShop;
import com.demkom58.lab13.thread.WasteShop;
import com.demkom58.lab13.thread.WoodLock;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class DlgShop extends JDialog {
    private JPanel contentPane;

    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField cylinderTimeField;
    private JTextField timberTimeField;
    private JTextField totalTimeField;

    private final WoodDirectory woodDirectory;
    private final ProductStore<IWeight> productStore;

    private final WoodLock woodLock;
    private final WasteStore wasteStore;

    private final Consumer<String> logger;

    public DlgShop(WoodDirectory woodDirectory, ProductStore<IWeight> productStore,
                   WoodLock woodLock, WasteStore wasteStore, Consumer<String> logger) {
        this.woodDirectory = woodDirectory;
        this.productStore = productStore;
        this.woodLock = woodLock;
        this.wasteStore = wasteStore;
        this.logger = logger;

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

        final CylinderShop cylinderShop = new CylinderShop("Cylinder Shop", woodDirectory, productStore,
                wasteStore, woodLock, totalTime, cylinderTime, logger);
        final Thread cylThread = new Thread(cylinderShop);
        cylThread.setDaemon(true);
        cylThread.start();

        final TimberShop timberShop = new TimberShop("Timber Shop", woodDirectory, productStore,
                wasteStore, woodLock, totalTime, timberTime, logger);
        final Thread timberThread = new Thread(timberShop);
        timberThread.setDaemon(true);
        timberThread.start();

        final WasteShop wasteShop
                = new WasteShop("Waste Shop", productStore, wasteStore, woodLock, totalTime, logger);
        final Thread wasteThread = new Thread(wasteShop);
        wasteThread.setDaemon(true);
        wasteThread.start();

        setVisible(false);
    }

    private void onCancel() {
        setVisible(false);
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
