package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Strategy for matrix addition (A + B).
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public interface IAddAlgorithm {
    /**
     * Perform matrix addition.
     *
     * @param a first operand matrix
     * @param b second operand matrix
     * @return result matrix (a + b)
     * @throws MatrixException when matrices cannot be added
     */
    public IMatrix execute(IMatrix a, IMatrix b) throws MatrixException;
}
