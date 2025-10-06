package pl.polsl.maj.model;

public class Matrix implements IMatrix {
    private int rows;
    private int cols;
    private double[][] data;

    public Matrix() {
        this.rows = 0;
        this.cols = 0;
        this.data = null;
    }
    
    
    public Matrix(int rows, int cols) {
        if(rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Invalid rows or cols number");
        }
                
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }
    
    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        
        this.data = new double[rows][cols];
        
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new IllegalArgumentException("");
            }
            
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    @Override
    public void init(int rows, int cols) {
        if(rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Invalid rows or cols number");
        }
                
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    @Override
    public void init(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        
        this.data = new double[rows][cols];
        
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new IllegalArgumentException("");
            }
            
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
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
    public double get(int r, int c) {
        return data[r][c];
    }
    
    @Override
    public void set(int r, int c, double value) {
        data[r][c] = value;
    }
    
    @Override
    public boolean isSquare() {
        return rows == cols;
    }    
}