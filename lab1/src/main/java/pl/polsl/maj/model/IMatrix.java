package pl.polsl.maj.model;
import pl.polsl.maj.exceptions.MatrixException;

/**
 * Abstract matrix model defining common matrix operations. Concrete
 * implementations must provide storage and basic getters/setters.
 * 
 * @author piotr.maj
 * @version 1.0.0
 */
public abstract class IMatrix  {
    /** Number of rows in the matrix (may be zero for an empty matrix). */
    protected int rows;

    /** Number of columns in the matrix (may be zero for an empty matrix). */
    protected int cols;

    /**
     * Protected default constructor for subclasses.
     * Implementations may initialize storage in their own constructors.
     */
    protected IMatrix() {
        // explicit default constructor
    }

    /**
     * Get number of rows.
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Get number of columns.
     *
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }
    
    /**
     * Check whether the matrix is square.
     *
     * @return {@code true} when rows == cols
     */
    public boolean isSquare() {
        return cols == rows;
    }
    
    /**
     * Get value at given row/column.
     *
     * @param r row index (0-based)
     * @param c column index (0-based)
     * @return stored value
     * @throws MatrixException when indices are invalid
     */
    public abstract double get(int r, int c) throws MatrixException;
    
    /**
     * Set value at given row/column.
     *
     * @param r row index (0-based)
     * @param c column index (0-based)
     * @param value value to store
     * @throws MatrixException when indices are invalid
     */
    public abstract void set(int r, int c, double value) throws MatrixException;    

    /**
     * Initialize matrix with given size.
     *
     * @param r number of rows
     * @param c number of columns
     * @throws MatrixException when dimensions are invalid
     */
    public abstract void init(int r, int c) throws MatrixException;

    /**
     * Initialize matrix from a 2D array.
     *
     * @param data 2D array with matrix values
     * @throws MatrixException when provided data is invalid
     */
    public abstract void init(double[][] data) throws MatrixException;

    /**
     * Factory method to create a new IMatrix instance from raw data.
     *
     * @param data 2D array with matrix contents
     * @return new matrix instance
     * @throws MatrixException when creation fails
     */
    protected abstract IMatrix createMatrix(double[][] data) throws MatrixException;

    /**
     * Compute determinant of the matrix.
     *
     * @return determinant value
     * @throws MatrixException when matrix is not square or invalid
     */
    public double det() throws MatrixException {
        if(!isSquare() || rows < 1) {
            throw new MatrixException("Matrix must be squeared and not empty");
        }

        if(rows == 1) {
            return get(0, 0);
        }

        if(rows == 2) {
            return get(0,0) * get(1, 1) - get(0, 1) * get(1,0);
        }

        double det = 0.0;

        for(int col = 0; col < cols; ++col) {
            double cofactor = cofactor(0, col);
            det += get(0, col) * cofactor;
        }

        return det;
    }

    private double cofactor(int row, int col) throws MatrixException {
        double sign = ((row + col) % 2 == 0) ? 1.0 : -1.0;
        return sign * subMatrix(this, row, col).det();
    }

    private IMatrix subMatrix(IMatrix m, int rowToRemove, int colToRemove) throws MatrixException {
        int n = m.getRows();

        if(n <= 1) {
            return createMatrix(new double[0][0]);
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
        return createMatrix(data);
    }

    /**
     * Add another matrix to this matrix (element-wise).
     *
     * @param other matrix to add
     * @throws MatrixException when sizes mismatch or other error
     */
    public void add(IMatrix other) throws MatrixException {
        if(!isEqualSize(other)) {
            throw new MatrixException("Matrices must be equal in size");
        }

        for (int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                set(i, j, get(i, j) + other.get(i, j));
            }
        }
    }

    /**
     * Check if matrices have equal dimensions.
     *
     * @param other other matrix
     * @return true when dimensions are equal
     */
    public boolean isEqualSize(IMatrix other) {
        return rows == other.getRows() && cols == other.getCols();
    }

    /**
     * Subtract another matrix from this matrix (element-wise).
     *
     * @param other matrix to subtract
     * @throws MatrixException when sizes mismatch or other error
     */
    public void substract(IMatrix other) throws MatrixException {
        if(!isEqualSize(other)) {
            throw new MatrixException("Matrices must be equal in size");
        }

        for (int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                set(i, j, get(i, j) - other.get(i, j));
            }
        }
    }

    /**
     * Multiply this matrix by another matrix (matrix multiplication).
     *
     * @param other right-hand matrix
     * @throws MatrixException when dimensions are incompatible
     */
    public void multiply(IMatrix other) throws MatrixException {
        if (cols != other.getRows()) {
            throw new MatrixException("Cannot multiply not compatible matrices");
        }

        double[][] result = new double[rows][other.getCols()];

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < other.getCols(); ++j) {
                double sum = 0;
                for (int k = 0; k < cols; ++k) {
                    sum += this.get(i, k) * other.get(k, j);
                }
                result[i][j] = sum;
            }
        }

        init(result);
    }

    /**
     * Multiply matrix by scalar (in-place).
     *
     * @param scalar multiplier
     * @throws MatrixException on error
     */
    public void multiply(double scalar) throws MatrixException {
        for (int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                set(i, j, get(i, j) * scalar);
            }
        }
    }

    /**
     * Transpose the matrix in-place.
     *
     * @throws MatrixException on error
     */
    public void transpose() throws MatrixException {
        double[][] transposed = new double[cols][rows];

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                transposed[j][i] = get(i, j);
            }
        }

        init(transposed);
    }

    /**
     * Compute trace (sum of diagonal) for square matrices.
     *
     * @return trace value
     * @throws MatrixException if matrix not square
     */
    public double trace() throws MatrixException {
        if(!isSquare()) {
            throw new MatrixException("Matrix must be squared");
        }

        double sum = 0.0;
        for (int i = 0; i < rows; ++i) {
            sum += get(i, i);
        }

        return sum;
    }

    /**
     * Compute inverse of the matrix (in-place) using Gauss-Jordan elimination.
     *
     * @throws MatrixException if matrix is not square or not invertible
     */
    public void inverse() throws MatrixException {
        if (!isSquare()) {
            throw new MatrixException("Matrix must be squared");
        }

        int n = rows;
        double[][] mat = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                mat[i][j] = get(i, j);
            }
        }

        double[][] inv = new double[n][n];
        for (int i = 0; i < n; ++i) {
            inv[i][i] = 1.0;
        }

        // Gauss-Jordan
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

        init(inv);
    }

    /**
     * Convert matrix to a single-line string in format:
     * {@code "rows cols data..."} where data contains row-major values.
     *
     * @return formatted matrix string
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(rows).append(" ").append(cols);
        if (rows * cols > 0) str.append(" ");

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                try {
                    str.append(get(i, j)).append(" ");
                } catch (MatrixException e) {
                    str.append("NaN ");
                }
            }
        }

        if (str.length() > 0 && str.charAt(str.length() - 1) == ' ') {
            str.setLength(str.length() - 1);
        }

        return str.toString();
    }
}