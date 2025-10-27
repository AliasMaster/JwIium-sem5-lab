package pl.polsl.maj.model.operations.simpleoperations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.operations.IInverseAlgorithm;

/**
 * Simple inverse computation using Gauss-Jordan elimination.
 * <p>
 * Produces the inverse of a square matrix by performing row operations on
 * an augmented matrix. Throws {@link MatrixException} when the matrix is
 * singular (non-invertible).
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public class SimpleInverse implements IInverseAlgorithm {

    /** Create a new SimpleInverse algorithm instance. */
    public SimpleInverse() {
    }

    /**
     * Compute inverse of a square matrix.
     *
     * @param a square matrix to invert
     * @return new matrix containing the inverse of {@code a}
     * @throws MatrixException when matrix is not square or not invertible
     */
    @Override
    public IMatrix execute(IMatrix a) throws MatrixException {
        if (!a.isSquare()) {
            throw new MatrixException("Matrix must be squared");
        }

        int n = a.getRows();
        double[][] mat = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                mat[i][j] = a.get(i, j);
            }
        }

        double[][] inv = new double[n][n];
        for (int i = 0; i < n; ++i) {
            inv[i][i] = 1.0;
        }

        // Gauss-Jordan elimination
        for (int i = 0; i < n; ++i) {
            double pivot = mat[i][i];
            int row = i;
            for (int k = i + 1; k < n; ++k) {
                if (Math.abs(mat[k][i]) > Math.abs(pivot)) {
                    pivot = mat[k][i];
                    row = k;
                }
            }

            if (Math.abs(pivot) < 1e-12) {
                throw new MatrixException("Matrix is not invertible");
            }

            if (row != i) {
                double[] tmp = mat[i];
                mat[i] = mat[row];
                mat[row] = tmp;

                double[] tmpInv = inv[i];
                inv[i] = inv[row];
                inv[row] = tmpInv;
            }

            double pivotVal = mat[i][i];
            for (int j = 0; j < n; ++j) {
                mat[i][j] /= pivotVal;
                inv[i][j] /= pivotVal;
            }

            for (int k = 0; k < n; ++k) {
                if (k == i) continue;
                double factor = mat[k][i];
                for (int j = 0; j < n; ++j) {
                    mat[k][j] -= factor * mat[i][j];
                    inv[k][j] -= factor * inv[i][j];
                }
            }
        }

        return a.createSameType(inv);
    }
}
