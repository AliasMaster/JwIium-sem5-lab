package pl.polsl.maj.view;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleView implements IView {
    private final Scanner scanner;

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
        System.out.println("0) End");
    }

    @Override
    public MenuOption getMenuOption() {
        System.out.println();
        System.out.print("Get Option: ");
        String option = scanner.nextLine().trim();

        switch (option) {
            case "0" -> {
                return MenuOption.End;
            }

            case "1" -> {
                return MenuOption.Determinant;
            }

            case "2" -> {
                return MenuOption.MultiplyByScalar;
            }

            case "3" -> {
                return MenuOption.Multiply;
            }

            case "4" -> {
                return MenuOption.AddMatrix;
            }

            case "5" -> {
                return MenuOption.SubractMatrix;
            }

            case "6" -> {
                return MenuOption.Transpose;
            }

            case "7" -> {
                return MenuOption.Inverse;
            }

            case "8" -> {
                return MenuOption.Trace;
            }
        
            default -> {
                return MenuOption.Invalid;
            }
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showErrorMessage(String message) {
        System.err.println(message);
    }

    @Override
    public String getMatrix() {
        System.out.println("Enter matrix in format: <rows> <cols> <data...> (space separated)");

        String matrix = "";
        // read until non-empty line is provided
        while (matrix.isBlank()) {
            if (!scanner.hasNextLine()) {
                throw new java.util.NoSuchElementException("No line found");
            }
            matrix = scanner.nextLine().trim();
        }

        return matrix;
    }

    @Override
    public void showMatrixCreator() {
        System.out.println("Please input matrix:");
    }

    @Override
    public void showMatrix(String matrix) {

        String[] parts = matrix.split(Pattern.quote(" "));

        if(parts.length < 2) {
            System.out.println("Cannot display matrix");
        }

        int rows = Integer.parseInt(parts[0]);
        int cols = Integer.parseInt(parts[1]);

        if(parts.length != 2 + rows * cols) {
            System.out.println("Cannot display matrix");
        }

        double[][] data = new double[rows][cols];
        int index = 2;

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                data[i][j] = Double.parseDouble(parts[index++]);
            }
        }

        int maxWidth = 0;
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                int len = String.format("%.2f", data[i][j]).length();
                if(len > maxWidth) maxWidth = len;
            }
        }

        System.out.println("= Matrix =");
        System.out.println("rows: " + rows);
        System.out.println("columns: " + cols);

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                System.out.printf("%" + maxWidth + ".2f ", data[i][j]);
            }
            System.out.println();
        }
    }

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