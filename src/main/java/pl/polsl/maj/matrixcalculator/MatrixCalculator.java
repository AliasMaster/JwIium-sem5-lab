package pl.polsl.maj.matrixcalculator;

import pl.polsl.maj.controller.*;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.BaseMatrix;
import pl.polsl.maj.model.operations.MatrixOperations;
import pl.polsl.maj.model.operations.simpleoperations.SimpleAdd;
import pl.polsl.maj.model.operations.simpleoperations.SimpleDeterminant;
import pl.polsl.maj.model.operations.simpleoperations.SimpleInverse;
import pl.polsl.maj.model.operations.simpleoperations.SimpleMultiply;
import pl.polsl.maj.model.operations.simpleoperations.SimpleMultiplyByScalar;
import pl.polsl.maj.model.operations.simpleoperations.SimpleSubstract;
import pl.polsl.maj.model.operations.simpleoperations.SimpleTrace;
import pl.polsl.maj.model.operations.simpleoperations.SimpleTranspose;
import pl.polsl.maj.view.ConsoleView;
import pl.polsl.maj.view.SwingView;
import pl.polsl.maj.view.IView;

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
 * @version 1.0.1
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
        // IView view = new ConsoleView();
        IView view = new SwingView();

        IMatrix model = new BaseMatrix();

        MatrixOperations calc = new MatrixOperations(
            new SimpleDeterminant(),
            new SimpleMultiplyByScalar(),
            new SimpleMultiply(),
            new SimpleAdd(),
            new SimpleSubstract(),
            new SimpleTranspose(),
            new SimpleInverse(),
            new SimpleTrace()
        );

        MatrixController controller = new MatrixController(view, model, calc);
        
        try {

            controller.initMatrix(args);
            
            while (true) { 
                controller.showMenu();
                String option = controller.getMenuOption();
                
                if(!controller.handleMenuOption(option)) {
                    break;
                }
            }
        } catch(Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }
}