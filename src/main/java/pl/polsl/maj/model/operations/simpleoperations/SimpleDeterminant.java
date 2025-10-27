package pl.polsl.maj.model.operations.simpleoperations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.operations.IDeterminantAlgorithm;

/**
 * Simple recursive determinant computation using expansion by first row.
 * <p>
 * This implementation is straightforward and easy to understand but has
 * exponential complexity and is therefore suitable only for small matrices.
 * It validates that the matrix is square and non-empty.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public class SimpleDeterminant implements IDeterminantAlgorithm {

    /** Create a new instance of the determinant algorithm. */
    public SimpleDeterminant() {
    }

    /**
     * Compute the determinant of a square matrix.
     *
     * @param a square matrix
     * @return determinant value
     * @throws MatrixException when the matrix is not square or is empty
     */
    @Override
    public double execute(IMatrix a) throws MatrixException {
        if(!a.isSquare() || a.getRows() < 1) {
            throw new MatrixException("Matrix must be squared and not empty");
        }

        if(a.getRows() == 1) {
            return a.get(0, 0);
        }

        if(a.getRows() == 2) {
            return a.get(0,0) * a.get(1, 1) - a.get(0, 1) * a.get(1,0);
        }

        double det = 0.0;

        for(int col = 0; col < a.getCols(); ++col) {
            double cofactor = cofactor(0, col, a);
            det += a.get(0, col) * cofactor;
        }

        return det;
    }

    /** Compute cofactor for element at (row,col). */
    private double cofactor(int row, int col, IMatrix m) throws MatrixException {
        double sign = ((row + col) % 2 == 0) ? 1.0 : -1.0;
        return sign * execute(subMatrix(m, row, col));
    }

    /** Build a submatrix removing specified row and column. */
    private IMatrix subMatrix(IMatrix m, int rowToRemove, int colToRemove) throws MatrixException {
        int n = m.getRows();

        if(n <= 1) {
            return m.createSameType(new double[0][0]);
        }

        double [][] data = new double[n - 1][n - 1];

        int r = 0;
        for(int i = 0; i < n; ++i) {
            if(i == rowToRemove) continue;
            int c = 0;
            for(int j = 0; j < n; ++j) {
                if(j == colToRemove) continue;
                data[r][c] = m.get(i, j);
                ++c;
            }
            ++r;
        }
        return m.createSameType(data);
    }
}
