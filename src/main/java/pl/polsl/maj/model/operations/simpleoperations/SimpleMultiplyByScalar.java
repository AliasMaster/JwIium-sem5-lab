package pl.polsl.maj.model.operations.simpleoperations;

import lombok.NoArgsConstructor;
import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.operations.IMultiplyByScalarAlgorithm;

/**
 * Simple algorithm for multiplying a matrix by a scalar value.
 * <p>
 * Produces a new matrix holding values of the input matrix multiplied by
 * the provided scalar.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.1
 */
@NoArgsConstructor
public class SimpleMultiplyByScalar implements IMultiplyByScalarAlgorithm {

    /**
     * Multiply all elements of matrix {@code a} by {@code scalar}.
     *
     * @param a matrix to multiply
     * @param scalar scaling factor
     * @return new matrix with scaled values
     * @throws MatrixException when indexing fails (propagated from IMatrix)
     */
    @Override
    public IMatrix execute(IMatrix a, double scalar) throws MatrixException {
        IMatrix newMatrix = a.createSameType(a.getRows(), a.getCols());

        for(int i = 0; i < a.getRows(); ++i) {
            for(int j = 0; j < a.getCols(); ++j) {
                newMatrix.set(i, j, a.get(i, j) * scalar);
            }
        }

        return newMatrix;
    }
}
