package pl.polsl.maj.gui;

import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Dialog helper that requests a scalar (double) value from the user.
 * 
 * @author piotr.maj
 * @version 1.0.0
 */
public class ScalarInputDialog {

    /**
     * Shows a modal input dialog and parses the entered value as {@code double}.
     * The method repeats the prompt until the user enters a valid number or
     * cancels the dialog.
     *
     * @param owner the parent frame for the dialog
     * @param title the dialog title
     * @return the parsed Double value or {@code null} when the user cancels
     */
    public static Double askScalar(java.awt.Frame owner, String title) {
        while (true) {
            String input = JOptionPane.showInputDialog(owner, "Please give a scalar value:", title, JOptionPane.QUESTION_MESSAGE);
            if (input == null) return null;
            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(owner, "Value is not provided.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(owner, "Invalid format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Utility class - prevent instantiation.
     */
    private ScalarInputDialog() {
        // no instances
    }
}