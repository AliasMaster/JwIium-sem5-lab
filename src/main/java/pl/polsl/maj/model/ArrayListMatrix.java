package pl.polsl.maj.model;

import java.util.ArrayList;
import java.util.Collections;
import pl.polsl.maj.exceptions.MatrixException;

/**
 * @version 1.0.0
 * @author piotr.maj
 */
public class ArrayListMatrix implements IMatrix {
    private ArrayList<ArrayList<Double>> data;
    private int rows;
    private int cols;
    
    public ArrayListMatrix() {
        this.rows = 0;
        this.cols = 0;
        this.data = new ArrayList<>();
    }
    
    public ArrayListMatrix(int rows, int cols) throws MatrixException {
        if(rows <= 0 || cols <= 0) {
            throw new MatrixException("Invalid number of rows or columns");
        }
        
        this.rows = rows;
        this.cols = cols;
        this.data = new ArrayList<>(rows);
        
        for(int i = 0; i < rows; ++i) {
            ArrayList<Double> inner = new ArrayList<>(Collections.nCopies(cols, 0.0));
            data.add(inner);
        }
    }
    
    public ArrayListMatrix(double[][] data) throws MatrixException {
        if (data == null || data.length == 0) {
            throw new MatrixException("Invalid matrix data");
        }
        
        this.rows = data.length;
        this.cols = data[0].length;
        
        this.data = new ArrayList<>(this.rows);
        
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new MatrixException("Invalid matrix data: inconsistent row lengths");
            }

           ArrayList<Double> innerList = new ArrayList<>(cols);
            //System.arraycopy(data[i], 0, this.data[i], 0, cols);
            for(int j = 0; j < cols; ++j) {
                innerList.add(data[i][j]);
            }
            
            this.data.add(innerList);
        }
        
    }

    @Override
    public boolean isSquare() {
        return this.rows == this.cols;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getCols() {
        return this.cols;
    }

    @Override
    public double get(int r, int c) throws MatrixException {
        if(r < 0 || c < 0 || c >= cols || r >= rows) {
            throw new MatrixException("Row or column index out of bounds");
        }
        return this.data.get(r).get(c);
    }

    @Override
    public void set(int r, int c, double value) throws MatrixException {
        if(r < 0 || c < 0 || c >= cols || r >= rows) {
            throw new MatrixException("Row or column index out of bounds");
        }
        this.data.get(r).set(c, value);
    }

    @Override
    public void init(int rows, int cols) throws MatrixException {
        if(rows <= 0 || cols <= 0) {
            throw new MatrixException("Invalid number of rows or columns");
        }
        
        this.rows = rows;
        this.cols = cols;
        this.data = new ArrayList<>(rows);
        
        for(int i = 0; i < rows; ++i) {
            ArrayList<Double> inner = new ArrayList<>(Collections.nCopies(cols, 0.0));
            data.add(inner);
        }
    }

    @Override
    public void init(double[][] data) throws MatrixException {
        if (data == null || data.length == 0) {
            throw new MatrixException("Invalid matrix data");
        }
        
        this.rows = data.length;
        this.cols = data[0].length;
        
        this.data = new ArrayList<>(this.rows);
        
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new MatrixException("Invalid matrix data: inconsistent row lengths");
            }

           ArrayList<Double> innerList = new ArrayList<>(cols);
            //System.arraycopy(data[i], 0, this.data[i], 0, cols);
            for(int j = 0; j < cols; ++j) {
                innerList.add(data[i][j]);
            }
            
            this.data.add(innerList);
        }
    }

    @Override
    public IMatrix createSameType(int rows, int cols) throws MatrixException {
        return new ArrayListMatrix(rows, cols);
    }

    @Override
    public IMatrix createSameType(double[][] data) throws MatrixException {
        return new ArrayListMatrix(data);
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
