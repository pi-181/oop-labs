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
import java.util.Enumeration;

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

        final JMenuItem searchBiggestRegionByPopulation = new JMenuItem("Find region with the largest population");
        searchBiggestRegionByPopulation.addActionListener(this::onBiggestRegionByPopulation);
        popupMenu.add(searchBiggestRegionByPopulation);

        final JMenuItem searchDistrictWithMostVillageCouncils = new JMenuItem("Show district with most village councils");
        searchDistrictWithMostVillageCouncils.addActionListener(this::onDistrictByMostVillageCouncils);
        popupMenu.add(searchDistrictWithMostVillageCouncils);

        addButton.addActionListener(this::onAddAction);
        removeButton.addActionListener(this::onRemoveAction);
        editButton.addActionListener(this::onEditAction);
        storeButton.addActionListener(this::onStoreAction);
        restoreButton.addActionListener(this::onRestoreAction);
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

        viewTree.setModel(getDefaultTreeModel());
        expandAll();
    }

    public void onTreeViewClick(MouseEvent e) {
        if (e.getClickCount() != 3 || e.getButton() != MouseEvent.BUTTON3)
            return;

        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null)
            return;

        AnyData data = (AnyData) node.getUserObject();
        Dlg dlg = data.showDialog(false);
        dlg.dispose();
    }

    public void onAddAction(ActionEvent e) {
        final DefaultMutableTreeNode parent = getSelectedNode();
        if (parent == null)
            return;

        AnyData parentData = (AnyData) parent.getUserObject();
        Dlg dlg = parentData.showSonDialog();
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

    public void onRemoveAction(ActionEvent e) {
        final DefaultMutableTreeNode node = getSelectedNode();
        if (node == null)
            return;

        node.removeFromParent();

        viewTree.setSelectionPath(null);
        viewTree.updateUI();
    }

    public void onEditAction(ActionEvent e) {
        final DefaultMutableTreeNode node = getSelectedNode();
        if (node == null)
            return;

        AnyData data = (AnyData) node.getUserObject();
        Dlg dlg = data.showDialog(true);

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

    public void onStoreAction(ActionEvent e) {
        final TreeModel model = viewTree.getModel();
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
                return "Wood storage data - .wood";
            }
        });

        fileChooser.setMultiSelectionEnabled(false);
        final int selection = fileChooser.showSaveDialog(this);
        if (selection != JFileChooser.APPROVE_OPTION)
            return;

        File selectedFile = fileChooser.getSelectedFile();
        if (!selectedFile.getName().endsWith(".bin"))
            selectedFile = new File(selectedFile.getAbsolutePath(), selectedFile.getName() + ".bin");

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
            stream.writeObject(viewTree.getModel());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void onRestoreAction(ActionEvent e) {
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

        final File selectedFile = fileChooser.getSelectedFile();
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

    public void onBiggestRegionByPopulation(ActionEvent e) {
        final TreeModel model = viewTree.getModel();
        final Object root = model.getRoot();

        int maxPopulation = 0;
        DefaultMutableTreeNode max = null;

        final int regionCount = model.getChildCount(root);
        for (int i = 0; i < regionCount; i++) {
            final Object regionNode = model.getChild(root, i);
            int population = 0;

            final int districtCount = model.getChildCount(regionNode);
            for (int j = 0; j < districtCount; j++) {
                final Object districtNode = model.getChild(regionNode, j);

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

    public void onDistrictByMostVillageCouncils(ActionEvent e) {
        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null)
            return;

        int maxCouncils = 0;
        DefaultMutableTreeNode max = null;

        Enumeration<TreeNode> enm = node.postorderEnumeration();
        while (enm.hasMoreElements()) {
            final DefaultMutableTreeNode current = (DefaultMutableTreeNode) enm.nextElement();
            final Object data = current.getUserObject();
            if (!(data instanceof District))
                continue;

            int councils = ((District) data).getVillageCouncils();
            if (councils > maxCouncils) {
                maxCouncils = councils;
                max = current;
            }
        }

        if (max == null) {
            JOptionPane.showMessageDialog(viewTree, "Nothing found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectNode(max);
        ((AnyData) max.getUserObject()).showDialog(false);
    }

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

    @Nullable
    private DefaultMutableTreeNode getSelectedNode() {
        Object selectedNode = viewTree.getLastSelectedPathComponent();
        if (selectedNode == null)
            JOptionPane.showMessageDialog(viewTree, "Select node at first!", "Error", JOptionPane.ERROR_MESSAGE);

        return (DefaultMutableTreeNode) selectedNode;
    }

    private void expandAll() {
        for (int i = 0; i < viewTree.getRowCount(); i++)
            viewTree.expandRow(i);
    }

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
