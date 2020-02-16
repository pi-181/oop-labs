package com.demkom58.lab4.view;

import com.demkom58.lab4.model.IWeight;
import com.demkom58.lab4.store.ProductStore;
import com.demkom58.lab4.store.WoodDirectory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class MainGui extends JFrame {
    private JPanel rootPanel;
    private JList<IWoodDialog> dialogList;
    private JTextArea textArea;

    private WoodDirectory woodDirectory = new WoodDirectory();
    private ProductStore productStore = new ProductStore();

    private DlgWaste dlgWaste = new DlgWaste();
    private DlgCylinder dlgCylinder = new DlgCylinder();
    private DlgTimber dlgTimber = new DlgTimber();
    private DlgWood dlgWood = new DlgWood();

    public MainGui() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Wood Program");

        final DefaultListModel<IWoodDialog> listModel = new DefaultListModel<>();
        listModel.addAll(Arrays.asList(dlgWaste, dlgCylinder, dlgTimber, dlgWood));
        dialogList.setModel(listModel);
        dialogList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onDialogSelect(e);
            }
        });
    }

    public void onDialogSelect(MouseEvent e) {
        IWoodDialog dlg = dialogList.getSelectedValue();
        dlg.setWoodDirectory(woodDirectory);
        dlg.setVisible(true);

        Object o = dlg.getObject();
        if (o != null)
            productStore.add((IWeight) o);

        textArea.setText(productStore.toString());
    }

}
