package pl.polsl.maj.view;

/**
 * View interface used by the controller for user interaction. Implementations
 * may provide console or GUI front-ends.
 * 
 * @author piotr.maj
 * @version 1.0.1
 */
public interface IView {
    /**
     * Show available menu options to the user.
     */
    public void showMenu();

    /**
     * Ask user for menu option.
     *
     * @return selected
     */
    public String getMenuOption();

    /**
     * Show an informational message to the user.
     *
     * @param message informational message to display
     */
    public void showMessage(String message);

    /**
     * Show an error message to the user.
     *
     * @param message error message to display
     */
    public void showErrorMessage(String message);

    /**
     * Read a matrix specification from the user as a single string.
     *
     * @return a single-line matrix specification in format "rows cols data..."
     */
    public String getMatrix();

    /**
     * Prompt user to create / enter a matrix.
     */
    public void showMatrixCreator();

    /**
     * Display a matrix represented as a string.
     *
     * @param matrix matrix string (format: "rows cols data...") to display
     */
    public void showMatrix(String matrix);

    /**
     * Read a scalar value from the user.
     *
     * @return the scalar value entered by the user
     */
    public double getScalar();
}