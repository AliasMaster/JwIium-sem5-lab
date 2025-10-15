package pl.polsl.maj.controller;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.Matrix;
import pl.polsl.maj.view.IView;
import pl.polsl.maj.view.MenuOption;

public class MatrixController {
    private final IView view;
    private final IMatrix matrixA;
    
    public MatrixController(IView view, IMatrix matrix) {
        this.view = view;
        this.matrixA = matrix;
    }

    public void showMenu() {
        view.showMenu();
    }

    public IMatrix getMatrix() {
        return matrixA;
    }

    public MenuOption getMenuOption() {
        return view.getMenuOption();
    }

    public void showMessage(String message) {
        view.showMessage(message);
    }

    public void initMatrix(String[] args) {
        initMatrix(args, this.matrixA);
    }

    public void initMatrix(IMatrix matrix) {
        String[] str = {};
        initMatrix(str, matrix);
    }

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

    public boolean handleMenuOption(MenuOption option) {
        if(option == MenuOption.End) {
            view.showMessage("Program closed");
            return false;
        }

        try {
            
            if(null != option) switch (option) {
                case Determinant -> {
                    double det = matrixA.det();
                    view.showMessage("Determinant: ".concat(String.valueOf(Math.scalb(det, 2))));
                }
                case Inverse -> {
                    matrixA.inverse();
                    view.showMatrix(matrixA.toString());
                }
                case Trace -> {
                    double trace = matrixA.trace();
                    view.showMessage("Trace: ".concat(String.valueOf(Math.scalb(trace, 2))));
                }
                case Transpose -> {
                    matrixA.transpose();
                    view.showMatrix(matrixA.toString());
                }

                case Multiply -> {
                    IMatrix matrixB = new Matrix();
                    this.initMatrix(matrixB);
                    matrixA.multiply(matrixB);
                    view.showMatrix(matrixA.toString());
                }

                case SubractMatrix -> {
                    IMatrix matrixB = new Matrix();
                    this.initMatrix(matrixB);
                    matrixA.substract(matrixB);
                    view.showMatrix(matrixA.toString());
                }

                case AddMatrix -> {
                    IMatrix matrixB = new Matrix();
                    this.initMatrix(matrixB);
                    matrixA.add(matrixB);
                    view.showMatrix(matrixA.toString());
                }

                case MultiplyByScalar -> {
                    double scalar = view.getScalar();
                    matrixA.multiply(scalar);
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