package pl.polsl.maj.matrixcalculator;

import pl.polsl.maj.controller.*;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.Matrix;
import pl.polsl.maj.view.ConsoleView;
import pl.polsl.maj.view.IView;
import pl.polsl.maj.view.MenuOption;

/**
 * Entry point for the Matrix Calculator application. Creates the view,
 * model and controller and runs a simple read-eval loop to process user
 * commands.
 *
 * Usage: run without arguments to interactively enter a matrix, or provide
 * matrix tokens as command line arguments in format
 * &lt;rows&gt; &lt;cols&gt; &lt;data...&gt;.
 * 
 * @author piotr.maj
 * @version 1.0.0
 */
public class MatrixCalculator {

    /**
     * Utility class - prevent instantiation.
     */
    private MatrixCalculator() {
        // prevent instantiation
    }

    /**
     * Application main method. Instantiates MVC components and starts the
     * interactive loop.
     *
     * @param args optional command-line tokens describing the initial matrix
     */
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