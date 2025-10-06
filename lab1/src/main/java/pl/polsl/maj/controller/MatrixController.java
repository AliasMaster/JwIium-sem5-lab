package pl.polsl.maj.controller;

import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.view.IView;
import pl.polsl.maj.view.Input;

public class MatrixController {
    private final IView view;
    private final IMatrix matrixA;
    
    public MatrixController(IView view, IMatrix matrix) {
        this.view = view;
        this.matrixA = matrix;
    }
    
    public void start(String[] args) {
        view.showMessage("=== Matrix Calculator ===");
        
        if(args.length > 2) {
            int rows = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);
            int expected = rows * cols;
            
            if(args.length - 2 != expected) {
                throw new IllegalArgumentException("");
            }
            
            int index = 2;
            
            matrixA.init(rows, cols);
            
            for(int i = 0; i < rows; ++i) {
                for(int j = 0; j < cols; ++j) {
                    matrixA.set(rows, cols, Double.parseDouble(args[index++]));
                }
            }
        } else {
            view.showMatrixCreator();
            String inputData = view.getMatrix();
            // int rows = Integer.parseInt(inputData[0]);
            // int cols = Integer.parseInt(inputData[1]);
            
        }
        
        while(true) {
            view.showMenu();
            Input input = view.getInput();
            
            switch(input) {
                case Input.CalculateDeterminant -> {
                    break;
                }
                case Input.MultiplyByScalar -> {
                    break;
                }
                case Input.End -> {
                    return;
                }
            }
        }
    }
}