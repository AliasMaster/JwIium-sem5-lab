package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Strategy for matrix multiplication (A × B).
 * <p>
 * Matrix multiplication requires that the number of columns in the first
 * matrix equals the number of rows in the second matrix. The result has
 * dimensions (m × k) when multiplying an (m × n) by an (n × k) matrix.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public interface IMultiplyAlgorithm {
    /**
     * Perform matrix multiplication.
     *
     * @param a left operand matrix with dimensions (m × n)
     * @param b right operand matrix with dimensions (n × k)
     * @return result matrix with dimensions (m × k)
     * @throws MatrixException when matrices cannot be multiplied
     */
    public IMatrix execute(IMatrix a, IMatrix b) throws MatrixException;
}
