package com.demkom58.rgr1.view;

import com.demkom58.rgr1.model.*;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

public class MainGui extends JFrame {
    private JPanel rootPanel;
    private JTree viewTree;

    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton storeButton;
    private JButton restoreButton;
    private JButton calculateButton;

    private JPopupMenu popupMenu;

    public MainGui() {
        // set default system theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("RGR 1");

        popupMenu = new JPopupMenu();

        // crates item for popup menu
        final JMenuItem searchBiggestRegionByPopulation = new JMenuItem("Show region with the largest population");
        searchBiggestRegionByPopulation.addActionListener(this::onBiggestRegionByPopulation);
        popupMenu.add(searchBiggestRegionByPopulation);

        final JMenuItem searchDistrictWithMostVillageCouncils = new JMenuItem("Find city by mayor's name");
        searchDistrictWithMostVillageCouncils.addActionListener(this::findCityByMayorName);
        popupMenu.add(searchDistrictWithMostVillageCouncils);

        // let's set listeners for buttons
        addButton.addActionListener(this::onAddAction);
        removeButton.addActionListener(this::onRemoveAction);
        editButton.addActionListener(this::onEditAction);
        storeButton.addActionListener(this::onStoreAction);
        restoreButton.addActionListener(this::onRestoreAction);

        // listener to show popup on click
        calculateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        viewTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onTreeViewClick(e);
            }
        });

        // set default model to our tree
        viewTree.setModel(getDefaultTreeModel());
        expandAll();
    }

    /**
     * Shows detailed information
     * about selected tree node to user.
     *
     * @param e event object.
     */
    public void onTreeViewClick(MouseEvent e) {
        if (e.getClickCount() != 1 || e.getButton() != MouseEvent.BUTTON2)
            return;

        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null)
            return;

        AnyData data = (AnyData) node.getUserObject();
        Dlg<? extends AnyData> dlg = data.showDialog(false);
        dlg.dispose();
    }

    /**
     * Shows to user gui for creating
     * new node for selected as parent node.
     *
     * @param e event object.
     */
    public void onAddAction(ActionEvent e) {
        final DefaultMutableTreeNode parent = getSelectedNode();
        if (parent == null)
            return;

        AnyData parentData = (AnyData) parent.getUserObject();
        Dlg<? extends AnyData> dlg = parentData.showSonDialog();
        if (dlg == null)
            return;

        AnyData newData;
        try {
            newData = dlg.createData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(viewTree, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dlg.dispose();
        if (newData == null)
            return;

        final DefaultMutableTreeNode newSon = new DefaultMutableTreeNode(newData);
        parent.add(newSon);
        viewTree.updateUI();

        expandAll();
    }

    /**
     * Removes selected node from
     * the tree.
     *
     * @param e event object.
     */
    public void onRemoveAction(ActionEvent e) {
        final DefaultMutableTreeNode node = getSelectedNode();
        if (node == null)
            return;

        node.removeFromParent();

        viewTree.setSelectionPath(null);
        viewTree.updateUI();
    }

    /**
     * Shows edit gui to user, for
     * the selected node in tree.
     *
     * @param e event object.
     */
    public void onEditAction(ActionEvent e) {
        final DefaultMutableTreeNode node = getSelectedNode();
        if (node == null)
            return;

        AnyData data = (AnyData) node.getUserObject();
        Dlg<? extends AnyData> dlg = data.showDialog(true);

        AnyData editData;
        try {
            editData = dlg.createData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(viewTree, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dlg.dispose();
        if (editData == null)
            return;

        node.setUserObject(editData);
        viewTree.updateUI();
    }

    /**
     * Shows to user file selection gui,
     * and then if possible saves all current
     * data to selected by user file.
     *
     * @param e event object.
     */
    public void onStoreAction(ActionEvent e) {
        final TreeModel model = viewTree.getModel();

        // Check for tree empty.
        if (model == null || model.getRoot() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tree is empty.\n" +
                            "To store tree, you must fill it at first.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Forming file chooser gui.
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to save");
        fileChooser.setApproveButtonText("Save");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isDirectory() && f.getName().endsWith(".bin");
            }

            @Override
            public String getDescription() {
                return "Bin storage data - .bin";
            }
        });

        fileChooser.setMultiSelectionEnabled(false);
        final int selection = fileChooser.showSaveDialog(this);
        if (selection != JFileChooser.APPROVE_OPTION)
            return;

        // Extract selected file with spell check.
        File selectedFile = fileChooser.getSelectedFile();
        if (!selectedFile.getName().endsWith(".bin"))
            selectedFile = new File(selectedFile.getAbsolutePath(), selectedFile.getName() + ".bin");

        // Writing current tree model to file.
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
            stream.writeObject(viewTree.getModel());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Shows to user file selection gui,
     * and then if possible loads all data
     * from the selected by user file.
     *
     * @param e event object.
     */
    public void onRestoreAction(ActionEvent e) {
        // Forming file chooser gui.
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to open.");
        fileChooser.setApproveButtonText("Open");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isDirectory() && f.getName().endsWith(".bin");
            }

            @Override
            public String getDescription() {
                return "Binary Data - .bin";
            }
        });

        fileChooser.setMultiSelectionEnabled(false);
        final int selection = fileChooser.showOpenDialog(this);
        if (selection != JFileChooser.APPROVE_OPTION)
            return;

        // Getting user selected file.
        final File selectedFile = fileChooser.getSelectedFile();

        // Writes data to file.
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(selectedFile))) {
            viewTree.setModel((TreeModel) stream.readObject());
        } catch (IOException | ClassCastException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "The bad format of store.\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
            return;
        }

        expandAll();
    }

    /**
     * Shows to user region with the biggest
     * population of region.
     * <p>
     * Population of region is
     * the sum of cities of this region.
     *
     * @param e event object.
     */
    public void onBiggestRegionByPopulation(ActionEvent e) {
        final TreeModel model = viewTree.getModel();
        final Object root = model.getRoot();

        int maxPopulation = 0;
        DefaultMutableTreeNode max = null;

        // Iterates over regions
        final int regionCount = model.getChildCount(root);
        for (int i = 0; i < regionCount; i++) {
            final Object regionNode = model.getChild(root, i);
            int population = 0;

            // Iterates over districts
            final int districtCount = model.getChildCount(regionNode);
            for (int j = 0; j < districtCount; j++) {
                final Object districtNode = model.getChild(regionNode, j);

                // Iterates over cities
                final int cityCount = model.getChildCount(districtNode);
                for (int k = 0; k < cityCount; k++) {
                    final DefaultMutableTreeNode cityNode = (DefaultMutableTreeNode) model.getChild(districtNode, k);
                    final City city = (City) cityNode.getUserObject();
                    population += city.getPopulation();
                }
            }

            if (population >= maxPopulation) {
                maxPopulation = population;
                max = (DefaultMutableTreeNode) regionNode;
            }
        }

        if (max == null) {
            JOptionPane.showMessageDialog(viewTree, "Nothing found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectNode(max);
        ((AnyData) max.getUserObject()).showDialog(false);
    }

    /**
     * Search city by mayor's name.
     * <p>
     * Shows dialog to user in which writes mayor's name,
     * then if it is ok, results will be shown to user.
     *
     * @param e event object.
     */
    public void findCityByMayorName(ActionEvent e) {
        final InputDlg inputDlg = new InputDlg("Mayor's name: ", "");
        if (!inputDlg.isOk())
            return;

        final String name = Optional.ofNullable(inputDlg.createData()).orElse("");
        final List<City> result = new ArrayList<>();

        DefaultMutableTreeNode root = ((DefaultMutableTreeNode) viewTree.getModel().getRoot());
        Enumeration<DefaultMutableTreeNode> regionEnum = root.children(); // country node children

        // iterates over regions
        while (regionEnum.hasMoreElements()) {
            DefaultMutableTreeNode regionNode = regionEnum.nextElement();
            Enumeration<DefaultMutableTreeNode> districtEnum = regionNode.children();

            // Iterates over districts
            while (districtEnum.hasMoreElements()) {
                DefaultMutableTreeNode districtNode = districtEnum.nextElement();
                Enumeration<DefaultMutableTreeNode> cityEnum = districtNode.children();

                // iterates over cities
                while (cityEnum.hasMoreElements()) {
                    DefaultMutableTreeNode cityNode = cityEnum.nextElement();
                    City city = (City) cityNode.getUserObject();
                    if (name.equalsIgnoreCase(city.getMayor()))
                        result.add(city);
                }
            }
        }

        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(viewTree, "City nodes with that name not found!", "No results!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        result.forEach(city -> city.showDialog(false));
    }

    /**
     * Creates default model of tree
     */
    public TreeModel getDefaultTreeModel() {
        final Country country = new Country("Україна", "Зеленський Володимир Олександрович", "Київ");
        final Region region = new Region("Чернігівська область", "Андрій Леонідович Прокопенко", 31_903, "Чернігов");
        final District district = new District("Чернігівський район", 46, "Чернігів");
        final City city = new City("Чернігів", 286_899, "Владислав Анатолійович Атрошенко");

        final DefaultMutableTreeNode countryNode = new DefaultMutableTreeNode(country);
        final DefaultMutableTreeNode regionNode = new DefaultMutableTreeNode(region);
        final DefaultMutableTreeNode districtNode = new DefaultMutableTreeNode(district);
        final DefaultMutableTreeNode cityNode = new DefaultMutableTreeNode(city);

        countryNode.add(regionNode);
        regionNode.add(districtNode);
        districtNode.add(cityNode);

        return new JTree(countryNode).getModel();
    }

    /**
     * Returns selected node by user.
     * @return selected node
     */
    @Nullable
    private DefaultMutableTreeNode getSelectedNode() {
        Object selectedNode = viewTree.getLastSelectedPathComponent();
        if (selectedNode == null)
            JOptionPane.showMessageDialog(viewTree, "Select node at first!", "Error", JOptionPane.ERROR_MESSAGE);

        return (DefaultMutableTreeNode) selectedNode;
    }

    /**
     * Opens all rows
     */
    private void expandAll() {
        for (int i = 0; i < viewTree.getRowCount(); i++)
            viewTree.expandRow(i);
    }

    /**
     * Sets selection to node in tree's model
     */
    private void selectNode(DefaultMutableTreeNode node) {
        int n = 0;

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) viewTree.getModel().getRoot();
        Enumeration<TreeNode> enm = root.children();
        while (enm.hasMoreElements()) {
            final TreeNode treeNode = enm.nextElement();
            if (treeNode == node) {
                viewTree.setSelectionRow(n);
                return;
            }

            n++;
        }
    }

}
