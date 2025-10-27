package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Strategy for computing matrix transpose.
 * <p>
 * The transpose of an (m × n) matrix is the (n × m) matrix formed by
 * turning rows into columns and vice versa.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public interface ITransposeAlgorithm {
    /**
     * Compute matrix transpose.
     *
     * @param a matrix to transpose
     * @return transposed matrix
     * @throws MatrixException when operation fails
     */
    public IMatrix execute(IMatrix a) throws MatrixException;
}
