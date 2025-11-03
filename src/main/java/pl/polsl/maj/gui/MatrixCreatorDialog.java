package pl.polsl.maj.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Modal dialog that allows the user to build a matrix interactively.
 *
 * <p>The user specifies number of rows and columns, generates a grid of
 * input fields and fills values. The dialog returns a string in the format
 * {@code "<rows> <cols> <data...>"} which is compatible with the
 * application's parsing logic.</p>
 * 
 * @author piotr.maj
 * @version 1.0.0
 */
public class MatrixCreatorDialog extends JDialog {
    /** Input field for number of rows. */
    private final JTextField rowsField = new JTextField(3);

    /** Input field for number of columns. */
    private final JTextField colsField = new JTextField(3);

    /** Container placed in the center area that holds the generated grid or a message. */
    private final JPanel centerHolder = new JPanel(new BorderLayout());

    /** Scroll pane that wraps the generated grid of text fields (created on generate). */
    private JScrollPane centerScroll = null;

    /** 2D array of text fields used to collect matrix data from the user. */
    private JTextField[][] dataFields = null;

    /** Owner frame used as parent for the modal dialog. */
    private final Frame owner;

    /** Resulting matrix string in the format "rows cols data..." or {@code null}. */
    private String result;

    /**
     * Constructs a new modal MatrixCreatorDialog.
     *
     * @param owner the parent frame; dialog will be modal relative to this frame
     */
    public MatrixCreatorDialog(Frame owner) {
        super(owner, "Matrix creator", true);
        this.owner = owner;
        setLayout(new BorderLayout(6, 6));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Rows"));
        top.add(rowsField);
        top.add(Box.createHorizontalStrut(8));
        top.add(new JLabel("Columns"));
        top.add(colsField);

        JButton btnGenerate = new JButton("Generate");

        btnGenerate.setToolTipText("Click this component to generate grid layout");
        btnGenerate.getAccessibleContext().setAccessibleDescription("Click this component to generate grid layout");
        btnGenerate.setMnemonic(KeyEvent.VK_G);

        top.add(Box.createHorizontalStrut(8));
        top.add(btnGenerate);

        add(top, BorderLayout.NORTH);

        centerHolder.add(new JLabel("Click 'Generate' to create grid"), BorderLayout.CENTER);
        add(centerHolder, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        ok.setToolTipText("Click this component to generate matrix");
        ok.getAccessibleContext().setAccessibleDescription("Click this component to generate matrix");
        ok.setMnemonic(KeyEvent.VK_O);

        cancel.setToolTipText("Click this component to cancel");
        cancel.getAccessibleContext().setAccessibleDescription("Click this component to cancel");
        cancel.setMnemonic(KeyEvent.VK_C);

        buttons.add(ok);
        buttons.add(cancel);
        add(buttons, BorderLayout.PAGE_END);

        btnGenerate.addActionListener(e -> onGenerate());
        ok.addActionListener(e -> onOk());
        cancel.addActionListener(e -> {
            result = null;
            setVisible(false);
        });

        pack();
        setResizable(true);
        setPreferredSize(new Dimension(600, 400));
        setLocationRelativeTo(owner);
    }

    private void onGenerate() {
        String rText = rowsField.getText().trim();
        String cText = colsField.getText().trim();

        if(rText.isEmpty() || cText.isEmpty()) {
            MessageUtils.error(owner, "Invalid rows or cols number given");
            return;
        }

        int r, c;
        try {
            r = Integer.parseInt(rText);
            c = Integer.parseInt(cText);
            if(r <= 0 || c <= 0) {
                MessageUtils.error(owner, "Rows and cols cannot be less or equal 0");
                return;
            }
        } catch (NumberFormatException e) {
            MessageUtils.error(owner, "Rows and cols must be integer");
            return;
        }

        centerHolder.removeAll();

        JPanel gridPanel = new JPanel(new GridLayout(r, c, 4, 4));
        dataFields = new JTextField[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                JTextField tf = new JTextField();
                tf.setColumns(1);
                dataFields[i][j] = tf;
                gridPanel.add(tf);
            }
        }

        centerScroll = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerScroll.setPreferredSize(new Dimension(Math.min(400, c*40), Math.min(200, r*15)));

        centerHolder.add(centerScroll, BorderLayout.CENTER);

        revalidate();
        repaint();
        pack();
    }

    private void onOk() {
        try {
            String rText = rowsField.getText().trim();
            String cText = colsField.getText().trim();
            if (rText.isEmpty() || cText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Provide number of rows and cols.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int r = Integer.parseInt(rText);
            int c = Integer.parseInt(cText);
            if (dataFields == null) {
                JOptionPane.showMessageDialog(this, "First click generate", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dataFields.length != r || dataFields[0].length != c) {
                int option = JOptionPane.showConfirmDialog(this, "Invalid amount of data?", "Notice", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    onGenerate();
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, "Adjust size.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append(r).append(" ").append(c);
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    String val = dataFields[i][j].getText().trim();
                    if (val.isEmpty()) {
                        JOptionPane.showMessageDialog(this, String.format("Field [%d,%d] is empty. All fields cannot be empty.", i+1, j+1), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    sb.append(" ").append(val.replaceAll("\\s+", " "));
                }
            }
            result = sb.toString();
            setVisible(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Columns and rows number must be integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Shows the dialog and returns the entered matrix as a space separated
     * string in the format {@code "rows cols data..."}.
     *
     * @return the matrix string or {@code null} if the user cancelled the dialog
     */
    public String showDialog() {
        setVisible(true);
        return result;
    }
}