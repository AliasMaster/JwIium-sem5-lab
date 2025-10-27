package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Strategy for computing matrix trace.
 * <p>
 * The trace of a square matrix is defined as the sum of the elements
 * on the main diagonal (from top-left to bottom-right).
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public interface ITraceAlgorithm {
    /**
     * Compute matrix trace.
     *
     * @param a square matrix
     * @return trace value
     * @throws MatrixException when matrix is not square
     */
    public double execute(IMatrix a) throws MatrixException;
}
