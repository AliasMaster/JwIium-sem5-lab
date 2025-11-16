package pl.polsl.maj.view;

import java.util.Scanner;
import java.util.regex.Pattern;

import pl.polsl.maj.model.IMatrix;

/**
 * Console implementation of {@link IView} that interacts with the user via
 * standard input and output. It is responsible for presenting the menu,
 * reading user choices, parsing matrix input lines and displaying results.
 * 
 * @author piotr.maj
 * @version 1.0.2
 */
public class ConsoleView implements IView {
    private final Scanner scanner;

    /**
     * Create a console view which reads from System.in and writes to
     * System.out/System.err.
     */
    public ConsoleView() {
        this.scanner = new Scanner(System.in);

        System.out.println("===== Matrix Calculator ===");
    }

    @Override
    public void showMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1) Calculate determinant");
        System.out.println("2) Multiply by Scalar");
        System.out.println("3) Multiply by other matrix");
        System.out.println("4) Add Matrix");
        System.out.println("5) Substract Matrix");
        System.out.println("6) Transpose");
        System.out.println("7) Inverse");
        System.out.println("8) Trace");
        System.out.println("0) Exit");
    }

    @Override
    public String getMenuOption() {
        System.out.println();
        System.out.print("Get Option: ");
        String option = scanner.nextLine().trim();

        return option;
    }

    /**
     * Show an informational message to the user.
     *
     * @param message message text to display
     */
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Show an error message to the user (sent to stderr).
     *
     * @param message error message text
     */
    @Override
    public void showErrorMessage(String message) {
        System.err.println(message);
    }

    /**
     * Prompt the user to enter a matrix in a single line using the format
     * {@code <rows> <cols> <data...>} where data is rows*cols double values
     * separated by whitespace. The method will keep prompting until a
     * non-empty line is provided.
     *
     * @return raw matrix line as entered by the user
     * @throws java.util.NoSuchElementException when input stream is closed
     */
    @Override
    public String getMatrix() {
        System.out.println("Enter matrix in format: <rows> <cols> <data...> (space separated)");

        String matrix = "";
        while (matrix.isBlank()) {
            if (!scanner.hasNextLine()) {
                throw new java.util.NoSuchElementException("No line found");
            }
            matrix = scanner.nextLine().trim();
        }

        return matrix;
    }

    /**
     * Prompt shown before requesting matrix input.
     */
    @Override
    public void showMatrixCreator() {
        System.out.println("Please input matrix:");
    }

    /**
     * Display a matrix represented as a single-line string in the format
     * produced by {@link IMatrix#toString()} ("rows cols data..."). If the
     * provided string cannot be parsed a brief message is printed.
     *
     * @param matrix matrix string to display
     */
    @Override
    public void showMatrix(String matrix) {

        java.util.List<String> parts = java.util.Arrays.asList(matrix.split(Pattern.quote(" ")));

        if(parts.size() < 2) {
            System.out.println("Cannot display matrix");
            return;
        }
        int rows = Integer.parseInt(parts.get(0));
        int cols = Integer.parseInt(parts.get(1));

        if(parts.size() != 2 + rows * cols) {
            System.out.println("Cannot display matrix");
            return;
        }

        java.util.List<java.util.List<Double>> data = new java.util.ArrayList<>(rows);
        int index = 2;
        for (int i = 0; i < rows; i++) {
            java.util.List<Double> row = new java.util.ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(Double.parseDouble(parts.get(index++)));
            }
            data.add(row);
        }

        int maxWidth = data.stream()
                .flatMap(java.util.List::stream)
                .mapToInt(v -> String.format("%.3g", v).length())
                .max()
                .orElse(0);

        System.out.println("= Matrix =");
        System.out.println("rows: " + rows);
        System.out.println("columns: " + cols);

        for (java.util.List<Double> row : data) {
            for (Double v : row) {
                System.out.printf("%" + maxWidth + ".3g ", v);
            }
            System.out.println();
        }
    }

    /**
     * Prompt for and read a scalar value from the user.
     *
     * @return parsed scalar value
     * @throws java.util.NoSuchElementException when input stream is closed
     * @throws NumberFormatException when provided value cannot be parsed
     */
    @Override
    public double getScalar() {
        System.out.println();
        System.out.print("Scalar: ");
        String value = scanner.nextLine().trim();
        while (value.isBlank()) {
            if (!scanner.hasNextLine()) {
                throw new java.util.NoSuchElementException("No line found");
            }
            value = scanner.nextLine().trim();
        }

        return Double.parseDouble(value);
    }
    
}