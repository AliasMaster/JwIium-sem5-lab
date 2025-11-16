package pl.polsl.maj.model.operations.simpleoperations;

import lombok.NoArgsConstructor;
import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.operations.IAddAlgorithm;

/**
 * Simple implementation of matrix addition.
 * <p>
 * Adds two matrices element-wise and returns a new matrix with the result.
 * This class performs basic validation (null checks and size equality) and
 * delegates storage creation to the provided {@link IMatrix} implementation
 * via {@link IMatrix#createSameType(int, int)}.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.1
 */
@NoArgsConstructor
public class SimpleAdd implements IAddAlgorithm {

    /**
     * Execute matrix addition.
     *
     * @param a left operand matrix (must not be null)
     * @param b right operand matrix (must not be null)
     * @return new matrix containing element-wise sums
     * @throws MatrixException when either matrix is null or dimensions differ
     */
    @Override
    public IMatrix execute(IMatrix a, IMatrix b) throws MatrixException {

        if(a == null || b == null) {
            throw new MatrixException("Matrices cannot be empty");
        }

        if(!a.isEqualSize(b)) {
            throw new MatrixException("Matrices must be equal in size");
        }

        IMatrix newMatrix = a.createSameType(a.getRows(), a.getCols());

        // use a lambda-based binary operation for addition
        pl.polsl.maj.model.operations.DoubleBinaryOp addOp = (x, y) -> x + y;

        for (int i = 0; i < a.getRows(); ++i) {
            for (int j = 0; j < a.getCols(); ++j) {
                newMatrix.set(i, j, addOp.apply(a.get(i, j), b.get(i, j)));
            }
        }

        return newMatrix;
    }
}
