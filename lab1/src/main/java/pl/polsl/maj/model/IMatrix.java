package pl.polsl.maj.model;
import pl.polsl.maj.exceptions.MatrixException;

public abstract class IMatrix  {
    protected int rows;
    protected int cols;

    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public boolean isSquare() {
        return cols == rows;
    }
    
    public abstract double get(int r, int c) throws MatrixException;
    
    public abstract void set(int r, int c, double value) throws MatrixException;    

    public abstract void init(int r, int c) throws MatrixException;

    public abstract void init(double[][] data) throws MatrixException;

    protected abstract IMatrix createMatrix(double[][] data) throws MatrixException;

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

    public boolean isEqualSize(IMatrix other) {
        return rows == other.getRows() && cols == other.getCols();
    }

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

    public void multiply(double scalar) throws MatrixException {
        for (int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                set(i, j, get(i, j) * scalar);
            }
        }
    }

    public void transpose() throws MatrixException {
        double[][] transposed = new double[cols][rows];

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                transposed[j][i] = get(i, j);
            }
        }

        init(transposed);
    }

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