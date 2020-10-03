package com.demkom58.lab12.view;

import com.demkom58.lab12.event.EventLogger;
import com.demkom58.lab12.model.Cylinder;
import com.demkom58.lab12.model.IWeight;
import com.demkom58.lab12.model.Timber;
import com.demkom58.lab12.store.ProductStore;
import com.demkom58.lab12.store.WoodDirectory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.StringJoiner;

public class MainGui extends JFrame {
    private JPanel rootPanel;
    private JList<IWoodDialog> dialogList;
    private JTextArea textArea;

    private JMenuItem saveMenuItem;
    private JMenuItem openMenuItem;

    private JMenuItem showLogMenuItem;
    private JMenuItem enableLoggerMenuItem;
    private JMenuItem disableLoggerMenuItem;

    private JMenuItem showCriticalWeightMenuItem;
    private JMenuItem removeCriticalWeightMenuItem;
    private JMenuItem moveCylinderTimberMenuItem;
    private JMenuItem resetTextAreaMenuItem;

    private WoodDirectory woodDirectory = new WoodDirectory();
    private ProductStore<IWeight> productStore = new ProductStore<>();
    private ProductStore<Timber> timberStore = new ProductStore<>("Каталог довгих брусів");
    private ProductStore<Cylinder> cylinderStore = new ProductStore<>("Каталог кругляків");

    private final DlgWaste dlgWaste = new DlgWaste();
    private final DlgCylinder dlgCylinder = new DlgCylinder();
    private final DlgTimber dlgTimber = new DlgTimber();
    private final DlgWood dlgWood = new DlgWood();

    private final EventLogger eventLogger = new EventLogger();

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

        // File menu items listeners
        openMenuItem.addActionListener(this::open);
        saveMenuItem.addActionListener(this::save);

        // Logger menu items listeners
        showLogMenuItem.addActionListener(this::showLog);
        enableLoggerMenuItem.addActionListener(this::enableLogger);
        disableLoggerMenuItem.addActionListener(this::disableLogger);

        // Lambda menu items listener
        showCriticalWeightMenuItem.addActionListener(this::showCriticalWeight);
        removeCriticalWeightMenuItem.addActionListener(this::removeCriticalWeight);
        moveCylinderTimberMenuItem.addActionListener(this::moveCylinderAndTimber);
        resetTextAreaMenuItem.addActionListener(this::resetTextArea);

        updateTextArea();

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
            timberStore = (ProductStore) stream.readObject();
            cylinderStore = (ProductStore) stream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        updateTextArea();
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
            stream.writeObject(timberStore);
            stream.writeObject(cylinderStore);
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

    public void enableLogger(ActionEvent e) {
        setLoggerEnabled(true);
        JOptionPane.showMessageDialog(
                this,
                "Event logger is enabled now.",
                "Settings change",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void disableLogger(ActionEvent e) {
        setLoggerEnabled(false);
        JOptionPane.showMessageDialog(
                this,
                "Event logger is disabled now.",
                "Settings change",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Reads information double number from user.
     *
     * @param message      message that user will see in dialog window
     * @param initialValue default value that will be entered to input
     * @return optional double, empty if user entered not valid data
     */
    public OptionalDouble readDoubleFromUser(String message, double initialValue) {
        String criticalWeightInput = "";

        try {
            criticalWeightInput = JOptionPane.showInputDialog(
                    this, message, initialValue
            );
            return OptionalDouble.of(Double.parseDouble(criticalWeightInput));
        } catch (NumberFormatException ex) {
            // handle not a float number input
            JOptionPane.showMessageDialog(
                    this, "'" + criticalWeightInput + "' is not correct weight",
                    "Timber dialog error",
                    JOptionPane.ERROR_MESSAGE
            );
            return OptionalDouble.empty();
        }
    }

    public void showCriticalWeight(ActionEvent e) {
        readDoubleFromUser("Enter critical weight", 10.0).ifPresent(criticalWeight -> {
            StringJoiner joiner = new StringJoiner("\n");
            joiner.add("Знайдені продукти з критичною вагою (починаючи з " + criticalWeight + "):");
            productStore.doOnlyFor(
                    o -> o.weight() >= criticalWeight, // critical weight condition
                    o -> joiner.add(o.toString()) // if true add to output
            );
            textArea.setText(joiner.toString());
        });
    }

    public void removeCriticalWeight(ActionEvent e) {
        readDoubleFromUser("Enter critical weight", 10.0).ifPresent(criticalWeight -> {
            productStore.remove(o -> o.weight() >= criticalWeight);
            updateTextArea();
        });
    }

    public void moveCylinderAndTimber(ActionEvent e) {
        productStore.remove(o -> {
            // cylinder check
            if (o.getClass() == Cylinder.class) {
                cylinderStore.add((Cylinder) o); // add to cylinder storage
                return true; // remove object from product storage
            }

            // timber check
            if (o.getClass() == Timber.class) {
                timberStore.add((Timber) o); // add to timber storage
                return true; // remove object from product storage
            }

            // don't remove object, because we won't move it.
            return false;
        });
        updateTextArea();
    }

    public void resetTextArea(ActionEvent e) {
        updateTextArea();
    }

    private void setLoggerEnabled(boolean enabled) {
        eventLogger.setEnabled(enabled);
        disableLoggerMenuItem.setEnabled(enabled);
        enableLoggerMenuItem.setEnabled(!enabled);
    }

    public void onDialogSelect(MouseEvent e) {
        IWoodDialog dlg = dialogList.getSelectedValue();
        dlg.setWoodDirectory(woodDirectory);
        dlg.setVisible(true);

        Object o = dlg.getObject();
        if (o != null)
            productStore.add((IWeight) o);

        updateTextArea();
    }

    /**
     * Updates information about storages in {@link MainGui#textArea}
     */
    public void updateTextArea() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(productStore.toString());
        joiner.add(timberStore.toString());
        joiner.add(cylinderStore.toString());
        textArea.setText(joiner.toString());
    }

}
