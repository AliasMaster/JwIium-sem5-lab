package pl.polsl.maj.matrixcalculator;

import pl.polsl.maj.controller.*;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.Matrix;
import pl.polsl.maj.view.ConsoleView;
import pl.polsl.maj.view.IView;
import pl.polsl.maj.view.MenuOption;

public class MatrixCalculator {

    public static void main(String[] args) {
        IView view = new ConsoleView();
        IMatrix model = new Matrix();
        MatrixController controller = new MatrixController(view, model);
        
        try {

            controller.initMatrix(args);
            
            while (true) { 
                controller.showMenu();
                MenuOption option = controller.getMenuOption();
                
                if(!controller.handleMenuOption(option)) {
                    break;
                }
            }
        } catch(Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }
}