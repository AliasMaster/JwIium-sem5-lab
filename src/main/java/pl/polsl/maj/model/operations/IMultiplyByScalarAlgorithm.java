package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Strategy for scalar multiplication (cA).
 * <p>
 * Multiplies each element of a matrix by a scalar value.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public interface IMultiplyByScalarAlgorithm {
    /**
     * Perform scalar multiplication.
     *
     * @param a matrix to scale
     * @param scalar scaling factor
     * @return result matrix (cA)
     * @throws MatrixException when operation fails
     */
    public IMatrix execute(IMatrix a, double scalar) throws MatrixException;
}
