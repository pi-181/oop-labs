package com.demkom58.lab11.view;

import com.demkom58.lab11.event.EventLogger;
import com.demkom58.lab11.model.IWeight;
import com.demkom58.lab11.store.ProductStore;
import com.demkom58.lab11.store.WoodDirectory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;

public class MainGui extends JFrame {
    private JPanel rootPanel;
    private JList<IWoodDialog> dialogList;
    private JTextArea textArea;
    private JMenuItem saveMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem showLogMenuItem;

    private WoodDirectory woodDirectory = new WoodDirectory();
    private ProductStore productStore = new ProductStore();

    private DlgWaste dlgWaste = new DlgWaste();
    private DlgCylinder dlgCylinder = new DlgCylinder();
    private DlgTimber dlgTimber = new DlgTimber();
    private DlgWood dlgWood = new DlgWood();

    private EventLogger eventLogger = new EventLogger();

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
        Arrays.asList(dlgWaste, dlgCylinder, dlgTimber, dlgWood).forEach(listModel::addElement);
        dialogList.setModel(listModel);
        dialogList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onDialogSelect(e);
            }
        });
        openMenuItem.addActionListener(this::open);
        saveMenuItem.addActionListener(this::save);
        showLogMenuItem.addActionListener(this::showLog);

        textArea.setText(productStore.toString());

        productStore.addProductListener((event) -> System.out.println(event.getProduct()));
        productStore.addProductListener(eventLogger);
    }

    public void open(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to open.");
        fileChooser.setApproveButtonText("Open");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isDirectory() && f.getName().endsWith(".wood");
            }

            @Override
            public String getDescription() {
                return "Wood storage data - .wood";
            }
        });

        fileChooser.setMultiSelectionEnabled(false);
        final int selection = fileChooser.showOpenDialog(this);
        if (selection != JFileChooser.APPROVE_OPTION)
            return;

        final File selectedFile = fileChooser.getSelectedFile();
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(selectedFile))) {
            woodDirectory = (WoodDirectory) stream.readObject();
            productStore = (ProductStore) stream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        textArea.setText(productStore.toString());
    }

    public void save(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to save");
        fileChooser.setApproveButtonText("Save");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isDirectory() && f.getName().endsWith(".wood");
            }

            @Override
            public String getDescription() {
                return "Wood storage data - .wood";
            }
        });

        fileChooser.setMultiSelectionEnabled(false);
        final int selection = fileChooser.showSaveDialog(this);
        if (selection != JFileChooser.APPROVE_OPTION)
            return;

        File selectedFile = fileChooser.getSelectedFile();
        if (!selectedFile.getName().endsWith(".wood"))
            selectedFile = new File(selectedFile.getAbsolutePath(), selectedFile.getName() + ".wood");

        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
            stream.writeObject(woodDirectory);
            stream.writeObject(productStore);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showLog(ActionEvent e) {
        try {
            Desktop.getDesktop().open(new File(eventLogger.getLogFileName()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
