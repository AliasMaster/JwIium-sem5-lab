package pl.polsl.maj.matrixcalculator;

import pl.polsl.maj.controller.*;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.Matrix;
import pl.polsl.maj.view.ConsoleView;
import pl.polsl.maj.view.IView;

public class MatrixCalculator {

    public static void main(String[] args) {
        IView view = new ConsoleView();
        IMatrix matrix = new Matrix();
        MatrixController m = new MatrixController(view, matrix);

        m.start(args);
    }
}