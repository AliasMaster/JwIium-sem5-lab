package pl.polsl.maj.model;

import pl.polsl.maj.exceptions.MatrixException;

/**
 * Concrete implementation of {@link IMatrix} that stores matrix elements in a
 * 2D double array.
 * 
 * @author piotr.maj
 * @version 1.0.0
 */
public class Matrix extends  IMatrix {
    private double[][] data;

    /**
     * Create an empty matrix (0x0).
     */
    public Matrix() {
        this.rows = 0;
        this.cols = 0;
        this.data = null;
    }
    
    
    /**
     * Create a matrix with given dimensions initialized with zeros.
     *
     * @param rows number of rows (must be >0)
     * @param cols number of columns (must be >0)
     * @throws MatrixException when dimensions are invalid
     */
    public Matrix(int rows, int cols) throws MatrixException {
        if(rows <= 0 || cols <= 0) {
            throw new MatrixException("Invalid rows or cols number");
        }
                
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }
    
    /**
     * Create a matrix initialized from a 2D array. The provided array must
     * be rectangular (all rows same length).
     *
     * @param data 2D array with matrix values
     * @throws MatrixException when data is invalid
     */
    public Matrix(double[][] data) throws MatrixException {
        this.rows = data.length;
        this.cols = data[0].length;
        
        this.data = new double[rows][cols];
        
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new MatrixException("Invalid given data");
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
            throw new MatrixException("Invalid rows or cols number");
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
        this.rows = data.length;
        this.cols = data[0].length;
        
        this.data = new double[rows][cols];
        
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new MatrixException("Invalid given data");
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
    protected IMatrix createMatrix(double[][] data) throws MatrixException {
        return new Matrix(data);
    }
}