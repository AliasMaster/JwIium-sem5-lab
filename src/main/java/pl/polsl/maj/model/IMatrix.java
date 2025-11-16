package pl.polsl.maj.model;

import pl.polsl.maj.exceptions.MatrixException;

/**
 * Contract for matrix implementations.
 *
 * <p>Implementations of this interface provide storage and basic accessors
 * for a two-dimensional numeric matrix. The interface is intentionally
 * minimal: it exposes element accessors, initialization helpers and factory
 * methods to create matrices of the same concrete type.</p>
 *
 * @author piotr.maj
 * @version 1.0.2
 */
public interface IMatrix  {

    /**
     * Return number of rows in the matrix.
     *
     * @return number of rows (>= 0)
     */
    public int getRows();
    
    /**
     * Return number of columns in the matrix.
     *
     * @return number of columns (>= 0)
     */
    public int getCols();
    
    /**
     * Check whether the matrix is square (rows == columns).
     *
     * @return {@code true} when the matrix has the same number of rows and columns
     */
    public boolean isSquare();
    
    /**
     * Read the element at the specified row and column.
     *
     * @param r row index (0-based)
     * @param c column index (0-based)
     * @return value stored at (r, c)
     * @throws MatrixException when indices are out of bounds
     */
    public double get(int r, int c) throws MatrixException;
    
    /**
     * Store a value at the specified row and column.
     *
     * @param r row index (0-based)
     * @param c column index (0-based)
     * @param value value to store
     * @throws MatrixException when indices are out of bounds
     */
    public void set(int r, int c, double value) throws MatrixException;    

    /**
     * Initialize matrix storage for the given dimensions. Implementations
     * should allocate internal storage and reset contents to zeros if
     * appropriate.
     *
     * @param r number of rows (> 0)
     * @param c number of columns (> 0)
     * @throws MatrixException when dimensions are invalid
     */
    public void init(int r, int c) throws MatrixException;

    /**
     * Initialize matrix from a two-dimensional {@code double} array. The
     * provided array is expected to be rectangular (all rows same length).
     *
     * @param data 2D array with matrix values
     * @throws MatrixException when provided data is null, empty or ragged
     */
    public void init(double[][] data) throws MatrixException;

    /**
     * Initialize matrix from a {@link MatrixData} record (collection-backed).
     * Implementations may override for better performance; a default
     * implementation converts the record to a primitive array and delegates
     * to {@link #init(double[][])}.
     *
     * @param data record containing rows, cols and flat row-major values
     * @throws MatrixException when provided data is invalid
     */
    default void init(MatrixData data) throws MatrixException {
        if (data == null) throw new MatrixException("Invalid matrix data");
        if (data.rows() <= 0 || data.cols() <= 0) throw new MatrixException("Invalid matrix dimensions");
        if (data.data() == null || data.data().size() != data.rows() * data.cols())
            throw new MatrixException("Invalid matrix data size");

        double[][] arr = new double[data.rows()][data.cols()];
        int idx = 0;
        for (int r = 0; r < data.rows(); r++) {
            for (int c = 0; c < data.cols(); c++) {
                arr[r][c] = data.data().get(idx++);
            }
        }
        init(arr);
    }

    /**
     * Create a fresh matrix instance of the same concrete implementation
     * with the given dimensions.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @return new matrix instance
     * @throws MatrixException when creation fails for given dimensions
     */
    public IMatrix createSameType(int rows, int cols) throws MatrixException;
    
    /**
     * Create a fresh matrix instance of the same concrete implementation
     * initialized from given data.
     *
     * @param data initial matrix values
     * @return new matrix instance
     * @throws MatrixException when provided data is invalid
     */
    public IMatrix createSameType(double[][] data) throws MatrixException;

    /**
     * Create a fresh matrix instance of the same concrete implementation
     * initialized from {@link MatrixData}. Default implementation converts
     * the record to a primitive array and delegates to
     * {@link #createSameType(double[][])}.
     *
     * @param data initial matrix values
     * @return new matrix instance
     * @throws MatrixException when provided data is invalid
     */
    default IMatrix createSameType(MatrixData data) throws MatrixException {
        if (data == null) throw new MatrixException("Invalid matrix data");
        double[][] arr = new double[data.rows()][data.cols()];
        int idx = 0;
        for (int r = 0; r < data.rows(); r++) {
            for (int c = 0; c < data.cols(); c++) {
                arr[r][c] = data.data().get(idx++);
            }
        }
        return createSameType(arr);
    }

    /**
     * Obtain a {@link MatrixData} record representing this matrix.
     * Default implementation reads elements via {@link #get(int,int)}.
     *
     * @return matrix data in row-major order
     */
    default MatrixData getMatrixData() {
        int r = getRows();
        int c = getCols();
        java.util.List<Double> list = new java.util.ArrayList<>(r * c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                try {
                    list.add(get(i, j));
                } catch (MatrixException e) {
                    list.add(Double.NaN);
                }
            }
        }
        return new MatrixData(r, c, list);
    }

    /**
     * Check whether this matrix has the same dimensions as {@code other}.
     *
     * @param other matrix to compare with
     * @return {@code true} when both matrices have equal rows and columns
     */
    public boolean isEqualSize(IMatrix other);

    /**
     * Produce a single-line string representation used by the view. The
     * expected format is: {@code "<rows> <cols> <data...>"} where data is
     * rows*cols double values separated by spaces.
     *
     * @return single-line textual representation of the matrix
     */
    @Override
    String toString();
}