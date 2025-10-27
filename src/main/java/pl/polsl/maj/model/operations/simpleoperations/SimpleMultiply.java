package pl.polsl.maj.model.operations.simpleoperations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.operations.IMultiplyAlgorithm;

/**
 * Simple implementation of matrix multiplication (a × b).
 * <p>
 * Computes the product using the standard triple-loop algorithm. The method
 * validates that the number of columns in the left matrix equals the number
 * of rows in the right matrix and returns a newly created matrix of the
 * appropriate size.
 * </p>
 *
 * <strong>Complexity:</strong> O(n * m * k) for an (n x m) × (m x k) multiplication.
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public class SimpleMultiply implements IMultiplyAlgorithm {

    /** Create a new instance of SimpleMultiply. */
    public SimpleMultiply() {
    }

    /**
     * Execute matrix multiplication.
     *
     * @param a left matrix with dimensions (n x m)
     * @param b right matrix with dimensions (m x k)
     * @return new matrix of dimensions (n x k) containing the product
     * @throws MatrixException when matrices are not compatible for multiplication
     */
    @Override
    public IMatrix execute(IMatrix a, IMatrix b) throws MatrixException {
        if(a.getCols() != b.getRows()) {
            throw new MatrixException("Cannot multiply not compatible matrices");
        }

        IMatrix newMatrix = a.createSameType(a.getRows(), b.getCols());

        for (int i = 0; i < a.getRows(); ++i) {
            for (int j = 0; j < b.getCols(); ++j) {
                double sum = 0;
                for (int k = 0; k < a.getCols(); ++k) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                newMatrix.set(i, j, sum);
            }
        }

        return newMatrix;
    }
}
