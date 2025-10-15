package pl.polsl.maj.model;

import pl.polsl.maj.exceptions.MatrixException;

public class Matrix extends  IMatrix {
    private double[][] data;

    public Matrix() {
        this.rows = 0;
        this.cols = 0;
        this.data = null;
    }
    
    
    public Matrix(int rows, int cols) throws MatrixException {
        if(rows <= 0 || cols <= 0) {
            throw new MatrixException("Invalid rows or cols number");
        }
                
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }
    
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

    @Override
    public void init(int rows, int cols) throws MatrixException {
        if(rows <= 0 || cols <= 0) {
            throw new MatrixException("Invalid rows or cols number");
        }
                
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

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
    
    @Override
    public double get(int r, int c) throws MatrixException {
        if(r < 0 || c < 0 || c >= cols || r >= rows) {
            throw new MatrixException("Row or column index out of bounds");
        }
        return data[r][c];
    }
    
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