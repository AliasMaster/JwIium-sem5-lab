package pl.polsl.maj.model.operations.simpleoperations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.operations.ITransposeAlgorithm;

/**
 * Simple transpose operation for matrices.
 * <p>
 * Produces a new matrix which is the transpose of the input (rows ↔ columns).
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public class SimpleTranspose implements ITransposeAlgorithm {

    /** Create a new SimpleTranspose instance. */
    public SimpleTranspose() {
    }

    /**
     * Execute transpose of the provided matrix.
     *
     * @param a input matrix
     * @return transposed matrix
     * @throws MatrixException when indexing fails (propagated from IMatrix)
     */
    @Override
    public IMatrix execute(IMatrix a) throws MatrixException {
        IMatrix newMatrix = a.createSameType(a.getCols(), a.getRows());

        for(int i = 0; i < a.getRows(); ++i) {
            for(int j = 0; j < a.getCols(); ++j) {
                newMatrix.set(j, i, a.get(i, j));
            }
        }

        return newMatrix;
    }
}
