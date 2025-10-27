package pl.polsl.maj.model.operations.simpleoperations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.operations.ISubstractAlgorithm;

/**
 * Simple implementation of matrix subtraction (a - b).
 * <p>
 * Performs element-wise subtraction and returns a new matrix with results.
 * Validates that both matrices have the same dimensions.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public class SimpleSubstract implements ISubstractAlgorithm {

    /** Create a new instance of SimpleSubstract. */
    public SimpleSubstract() {
    }

    /**
     * Execute matrix subtraction.
     *
     * @param a minuend matrix
     * @param b subtrahend matrix
     * @return new matrix containing element-wise differences
     * @throws MatrixException when dimensions differ
     */
    @Override
    public IMatrix execute(IMatrix a, IMatrix b) throws MatrixException {
        if(!a.isEqualSize(b)) {
           throw new MatrixException("Matrices must be equal in size"); 
        }

        IMatrix newMatrix = a.createSameType(a.getRows(), a.getCols());

        for (int i = 0; i < a.getRows(); ++i) {
            for(int j = 0; j < a.getCols(); ++j) {
                newMatrix.set(i, j, a.get(i, j) - b.get(i, j));
            }
        }

        return newMatrix;
    }
}
