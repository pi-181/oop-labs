package com.demkom58.rgr1.view;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Common data dialog logic class.
 *
 * @param <DATA> generic of data class
 */
public abstract class Dlg<DATA> extends JDialog {
    private JPanel contentPane;

    private JButton buttonOK;
    private JButton buttonCancel;
    private boolean ok;

    /**
     * Configurator method
     *
     * @param contentPane  root content pane of dialog object
     * @param buttonOK     ok button instance
     * @param buttonCancel cancel button instance
     */
    public void setup(@NotNull final JPanel contentPane,
                      @NotNull final JButton buttonOK,
                      @NotNull final JButton buttonCancel) {
        this.contentPane = contentPane;
        this.buttonOK = buttonOK;
        this.buttonCancel = buttonCancel;

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
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    /**
     * Ok result processor.
     */
    protected void onOK() {
        ok = true;
        setVisible(false);
    }

    /**
     * Cancel result processor.
     */
    protected void onCancel() {
        ok = false;
        setVisible(false);
    }

    /**
     * Abstract method extractor of received data.
     *
     * @return user data
     * @throws Exception on error in delegate occurred
     */
    @Nullable
    public abstract DATA createData() throws Exception;

    /**
     * Changes editable state of dialog
     *
     * @param editable true to make dialog editable
     */
    public void setEditable(boolean editable) {
        buttonOK.setVisible(editable);

        if (editable)
            buttonCancel.setText("Cancel");
        else
            buttonCancel.setText("Exit");

        for (Component component : contentPane.getComponents())
            component.setEnabled(editable);
    }

    /**
     * Getter for result state
     *
     * @return true when user pressed ok button
     */
    public boolean isOk() {
        return ok;
    }
}
