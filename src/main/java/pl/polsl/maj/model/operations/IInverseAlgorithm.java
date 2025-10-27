package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Strategy for computing matrix inverse (A^-1).
 * <p>
 * The inverse of a matrix A is a matrix that, when multiplied by A,
 * yields the identity matrix. Only square, non-singular matrices have
 * an inverse.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public interface IInverseAlgorithm {
    /**
     * Compute matrix inverse.
     *
     * @param a matrix to invert (must be square and non-singular)
     * @return inverse matrix (A^-1)
     * @throws MatrixException when matrix is not invertible
     */
    public IMatrix execute(IMatrix a) throws MatrixException;
}
