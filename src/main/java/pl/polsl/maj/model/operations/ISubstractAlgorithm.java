package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Strategy for matrix subtraction (A - B).
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public interface ISubstractAlgorithm {
    /**
     * Perform matrix subtraction.
     *
     * @param a minuend matrix
     * @param b subtrahend matrix
     * @return result matrix (a - b)
     * @throws MatrixException when matrices cannot be subtracted
     */
    public IMatrix execute(IMatrix a, IMatrix b) throws MatrixException;
}
