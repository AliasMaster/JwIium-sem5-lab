package pl.polsl.maj.model;

import pl.polsl.maj.exceptions.MatrixException;

/**
 * Default implementation of {@link IMatrix} which stores elements in a
 * two-dimensional {@code double[][]} array.
 * <p>
 * The class provides basic constructors, initialization helpers and safe
 * indexed access (with {@link pl.polsl.maj.exceptions.MatrixException} thrown
 * on invalid indices). It is used as the concrete matrix type in the
 * application.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.1
 */
public class BaseMatrix implements IMatrix {
    /** Underlying storage for matrix values (rows x cols). May be {@code null}
     *  for an empty matrix. */
    private double[][] data;
    /** Number of rows in the matrix. */
    private int rows;
    /** Number of columns in the matrix. */
    private int cols;

    /**
     * Create an empty matrix (0x0). The internal storage is {@code null} until
     * {@link #init(int, int)} or {@link #init(double[][])} is called.
     */
    public BaseMatrix() {
        this.rows = 0;
        this.cols = 0;
        this.data = null;
    }
    
    /**
     * Create a matrix with given dimensions initialized with zeros.
     *
     * @param rows number of rows (must be > 0)
     * @param cols number of columns (must be > 0)
     * @throws MatrixException when dimensions are invalid
     */
    public BaseMatrix(int rows, int cols) throws MatrixException {
        if(rows <= 0 || cols <= 0) {
            throw new MatrixException("Invalid number of rows or columns");
        }
                
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }
    
    /**
     * Create a matrix initialized from a 2D array. The provided array must
     * be rectangular (all rows same length) and not empty.
     *
     * @param data 2D array with matrix values
     * @throws MatrixException when data is null, empty or not rectangular
     */
    public BaseMatrix(double[][] data) throws MatrixException {
        if (data == null || data.length == 0) {
            throw new MatrixException("Invalid matrix data");
        }

        this.rows = data.length;
        this.cols = data[0].length;
        
        this.data = new double[rows][cols];
        
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new MatrixException("Invalid matrix data: inconsistent row lengths");
            }
            
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    /**
     * Initialize the matrix with the given dimensions (zeros).
     *
     * @param rows number of rows
     * @param cols number of columns
     * @throws MatrixException when dimensions are invalid
     */
    @Override
    public void init(int rows, int cols) throws MatrixException {
        if(rows <= 0 || cols <= 0) {
            throw new MatrixException("Invalid number of rows or columns");
        }
                
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    /**
     * Initialize the matrix from a 2D array.
     *
     * @param data 2D array with values
     * @throws MatrixException when data is invalid
     */
    @Override
    public void init(double[][] data) throws MatrixException {
        if (data == null || data.length == 0) {
            throw new MatrixException("Invalid matrix data");
        }

        this.rows = data.length;
        this.cols = data[0].length;

        this.data = new double[rows][cols];

        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new MatrixException("Invalid matrix data: inconsistent row lengths");
            }

            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }
    
    /**
     * Get value at (r,c).
     *
     * @param r row index
     * @param c column index
     * @return value at given position
     * @throws MatrixException when indices are out of bounds
     */
    @Override
    public double get(int r, int c) throws MatrixException {
        if(r < 0 || c < 0 || c >= cols || r >= rows) {
            throw new MatrixException("Row or column index out of bounds");
        }
        return data[r][c];
    }
    
    /**
     * Set value at (r,c).
     *
     * @param r row index
     * @param c column index
     * @param value value to set
     * @throws MatrixException when indices are out of bounds
     */
    @Override
    public void set(int r, int c, double value) throws MatrixException {
        if(r < 0 || c < 0 || c >= cols || r >= rows) {
            throw new MatrixException("Row or column index out of bounds");
        }
        data[r][c] = value;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getCols() {
        return cols;
    }

    @Override
    public boolean isSquare() {
        return cols == rows;
    }

    @Override
    public IMatrix createSameType(int rows, int cols) throws MatrixException {
        return new BaseMatrix(rows, cols);
    }

    @Override
    public IMatrix createSameType(double[][] data) throws MatrixException {
        return new BaseMatrix(data);
    }

    @Override
    public boolean isEqualSize(IMatrix other) {
        return this.rows == other.getRows() && this.cols == other.getCols();
    }

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