package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Strategy for computing matrix determinant.
 * <p>
 * The determinant is a scalar value that can be computed from the
 * elements of a square matrix and provides important information about
 * the matrix's properties (e.g., invertibility).
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public interface IDeterminantAlgorithm {
    /**
     * Compute matrix determinant.
     *
     * @param a square matrix
     * @return determinant value
     * @throws MatrixException when matrix is not square
     */
    public double execute(IMatrix a) throws MatrixException;
}
