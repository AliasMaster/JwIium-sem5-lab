package pl.polsl.maj.gui;

import java.awt.Frame;

import javax.swing.JOptionPane;

/**
 * Simple message utility for showing modal informational and error dialogs.
 *
 * <p>All methods are static; the class is not instantiable. Lombok's
 * {@code @UtilityClass} annotation is used to enforce utility semantics.</p>
 *
 * @author piotr.maj
 * @version 1.0.1
 */
public final class MessageUtils {

    private MessageUtils() { }

    /**
     * Shows an informational message dialog.
     *
     * @param owner parent frame for the dialog
     * @param msg message text to display
     */
    public static void info(Frame owner, String msg) {
        JOptionPane.showMessageDialog(owner, msg, "Notice", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an error message dialog.
     *
     * @param owner parent frame for the dialog
     * @param msg error message text to display
     */
    public static void error(Frame owner, String msg) {
        JOptionPane.showMessageDialog(owner, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
