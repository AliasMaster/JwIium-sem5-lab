package pl.polsl.maj.model;

import java.util.ArrayList;
import java.util.Collections;
import pl.polsl.maj.exceptions.MatrixException;
import lombok.Getter;

/**
 * List-backed implementation of the {@link IMatrix} interface.
 * <p>
 * Stores matrix data in a {@code List<List<Double>>} structure, allowing
 * dynamic resizing and flexible memory usage. Supports creation from
 * dimensions or raw double array data.
 * </p>
 *
 * @version 1.0.0
 * @author piotr.maj
 */
public class ArrayListMatrix implements IMatrix {
    private java.util.List<java.util.List<Double>> data;
    @Getter
    private int rows;
    @Getter
    private int cols;
    
    /**
     * Create an empty matrix with zero dimensions.
     * <p>
     * This constructor produces an empty, zero-sized matrix instance with an
     * initialized backing list. It is intended for usage patterns where the
     * matrix is created first and subsequently initialized via {@link #init(int,int)}
     * or {@link #init(MatrixData)}. Keeping this explicit constructor ensures
     * the internal {@code data} list is non-null and avoids NPEs from later
     * operations.
     * </p>
     */
    public ArrayListMatrix() {
        this.rows = 0;
        this.cols = 0;
        this.data = new ArrayList<>();
    }
    
    /**
     * Create a matrix with given dimensions, initialized with zeros.
     *
        * @param rows number of rows (must be positive)
        * @param cols number of columns (must be positive)
     * @throws MatrixException if rows or cols are not positive
     */
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
    
    /**
     * Create a matrix from a 2D double array.
     *
        * @param data the 2D array to initialize this matrix with
        * @throws MatrixException if data is null, empty, or has inconsistent row lengths
     */
    public ArrayListMatrix(double[][] data) throws MatrixException {
        if (data == null || data.length == 0) {
            throw new MatrixException("Invalid matrix data");
        }
        
        this.rows = data.length;
        this.cols = data[0].length;
        
        this.data = new java.util.ArrayList<>(this.rows);
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new MatrixException("Invalid matrix data: inconsistent row lengths");
            }
            java.util.List<Double> innerList = new java.util.ArrayList<>(cols);
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
        this.data = new java.util.ArrayList<>(rows);
        for(int i = 0; i < rows; ++i) {
            java.util.List<Double> inner = new java.util.ArrayList<>(Collections.nCopies(cols, 0.0));
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
        
        this.data = new java.util.ArrayList<>(this.rows);
        for(int i = 0; i < rows; ++i) {
            if(data[i].length != cols) {
                throw new MatrixException("Invalid matrix data: inconsistent row lengths");
            }
            java.util.List<Double> innerList = new java.util.ArrayList<>(cols);
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
    public void init(MatrixData md) throws MatrixException {
        if (md == null) throw new MatrixException("Invalid matrix data");
        init(md.rows(), md.cols());
        java.util.List<Double> list = md.data();
        int idx = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data.get(i).set(j, list.get(idx++));
            }
        }
    }

    @Override
    public IMatrix createSameType(MatrixData md) throws MatrixException {
        if (md == null) throw new MatrixException("Invalid matrix data");
        ArrayListMatrix m = new ArrayListMatrix(md.rows(), md.cols());
        init(md);
        return m;
    }

    @Override
    public boolean isEqualSize(IMatrix other) {
        return this.rows == other.getRows() && this.cols == other.getCols();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rows).append(" ").append(cols);
        java.util.List<String> values = new java.util.ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                try {
                    double value = get(i, j);
                    // Format to 3 significant figures using g format specifier
                    String formatted = String.format("%.3g", value);
                    values.add(formatted);
                } catch (MatrixException e) {
                    values.add("NaN");
                }
            }
        }
        if (!values.isEmpty()) {
            sb.append(' ').append(String.join(" ", values));
        }
        return sb.toString();
    }
    
}
