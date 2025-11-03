package pl.polsl.maj.controller;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.BaseMatrix;
import pl.polsl.maj.model.operations.MatrixOperations;
import pl.polsl.maj.view.IView;

/**
 * Main controller for the matrix calculator application.
 * <p>
 * Coordinates interactions between the view and the matrix model. Responsible
 * for creating and initializing matrices, delegating operations and handling
 * user menu choices.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.1
 */
public class MatrixController {
    private final IView view;
    private IMatrix matrixA;
    private final MatrixOperations calc;
    
    /**
     * Create a new controller instance.
     *
     * @param view   the view implementation used for input/output
     * @param matrix the matrix model instance to operate on
     * @param calc   the operations facade providing matrix algorithms
     */
    public MatrixController(IView view, IMatrix matrix, MatrixOperations calc) {
        this.view = view;
        this.matrixA = matrix;
        this.calc = calc;
    }

    /**
     * Show the application menu using the configured view.
     */
    public void showMenu() {
        view.showMenu();
    }

    /**
     * Return the currently held matrix instance.
     *
     * @return the primary matrix model
     */
    public IMatrix getMatrix() {
        return matrixA;
    }

    /**
     * Ask the view for the next menu option selected by the user.
     *
     * @return selected
     */
    public String getMenuOption() {
        return view.getMenuOption();
    }

    /**
     * Display an informational message via the view.
     *
     * @param message message text to show
     */
    public void showMessage(String message) {
        view.showMessage(message);
    }

    /**
     * Initialize the primary matrix using command-line arguments.
     * If arguments are empty, the view will be used to collect matrix data.
     *
     * @param args command-line arguments representing a matrix
     */
    public void initMatrix(String[] args) {
        initMatrix(args, this.matrixA);
        view.showMatrix(matrixA.toString());
    }

    /**
     * Initialize the supplied matrix by prompting the user through the view.
     *
     * @param matrix matrix to initialize
     */
    public void initMatrix(IMatrix matrix) {
        String[] str = {};
        initMatrix(str, matrix);
    }

    /**
     * Initialize the supplied matrix using either the provided input array or
     * by asking the view for a matrix specification. The expected format is:
     * <code>&lt;rows&gt; &lt;cols&gt; &lt;data...&gt;</code> where data is
     * rows*cols double values.
     *
     * @param input  optional string array with matrix tokens
     * @param matrix matrix to initialize
     */
    public void initMatrix(String[] input, IMatrix matrix) {
        while (true) {
        try {
            String[] dataArray;
            
            if (input != null && input.length > 2) {
                dataArray = input;
            } else {
                view.showMatrixCreator();
                String m = view.getMatrix();
                dataArray = m.split("\\s+");
            }

            if (dataArray.length < 3) {
                throw new MatrixException("Not enough data to create matrix");
            }

            int rows = Integer.parseInt(dataArray[0]);
            int cols = Integer.parseInt(dataArray[1]);

            int expected = rows * cols;
            if (dataArray.length - 2 != expected) {
                throw new MatrixException("Cannot parse data: expected " + expected + " elements, got " + (dataArray.length - 2));
            }

            matrix.init(rows, cols);

            int index = 2;
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    matrix.set(i, j, Double.parseDouble(dataArray[index++]));
                }
            }

            break;

        } catch (MatrixException | NumberFormatException e) {
            view.showErrorMessage(e.getMessage());
            input = null;
        }
    }
    }

    /**
     * Handle the given menu option, performing the requested matrix
     * operation. Any errors are reported through the view.
     *
     * @param option menu option to handle
     * @return {@code true} to continue program loop, {@code false} to exit
     */
    public boolean handleMenuOption(String option) {
        if("0".equals(option)) {
            view.showMessage("Program closed");
            return false;
        }

        try {
            
            if(null != option) switch (option) {
                case "1" -> {
                    double det = calc.determinant(matrixA);
                    view.showMessage("Determinant: ".concat(String.valueOf(det)));
                }
                case "7" -> {
                    matrixA = calc.inverse(matrixA);
                    view.showMatrix(matrixA.toString());
                }
                case "8" -> {
                    double trace = calc.trace(matrixA);
                    view.showMessage("Trace: ".concat(String.valueOf(trace)));
                }
                case "6" -> {
                    matrixA = calc.transpose(matrixA);
                    view.showMatrix(matrixA.toString());
                }

                case "3" -> {
                    IMatrix matrixB = new BaseMatrix();
                    this.initMatrix(matrixB);
                    matrixA = calc.multiply(matrixA, matrixB);
                    view.showMatrix(matrixA.toString());
                }

                case "5" -> {
                    IMatrix matrixB = new BaseMatrix();
                    this.initMatrix(matrixB);
                    matrixA = calc.substract(matrixA, matrixB);
                    view.showMatrix(matrixA.toString());
                }

                case "4" -> {
                    IMatrix matrixB = new BaseMatrix();
                    this.initMatrix(matrixB);
                    matrixA = calc.add(matrixA, matrixB);
                    view.showMatrix(matrixA.toString());
                }

                case "2" -> {
                    double scalar = view.getScalar();
                    matrixA = calc.multiplyByScalar(matrixA, scalar);
                    view.showMatrix(matrixA.toString());
                }

                default -> {
                    view.showErrorMessage("Invalid option");
                }
            }
            
        } catch (MatrixException e) {
            view.showErrorMessage(e.getMessage());
        }

        return  true;
    }
}