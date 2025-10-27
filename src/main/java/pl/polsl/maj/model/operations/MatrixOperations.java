package pl.polsl.maj.model.operations;

import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;

/**
 * Facade class that groups available matrix operation algorithms.
 * <p>
 * This class holds references to algorithm implementations (strategy
 * objects) and exposes simple methods that delegate to those algorithms.
 * It simplifies wiring the algorithms together in the application and
 * provides a single entry point for performing matrix operations.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
public final class MatrixOperations {
    /** Algorithm used to compute determinants. */
    private final IDeterminantAlgorithm detAlgo;
    /** Algorithm used to multiply matrix by a scalar. */
    private final IMultiplyByScalarAlgorithm multiplyByScalarAlgo;
    /** Algorithm used to multiply two matrices. */
    private final IMultiplyAlgorithm multiplyAlgo;
    /** Algorithm used to add two matrices. */
    private final IAddAlgorithm addAlgo;
    /** Algorithm used to subtract two matrices. */
    private final ISubstractAlgorithm substractAlgo;
    /** Algorithm used to transpose a matrix. */
    private final ITransposeAlgorithm transposeAlgo;
    /** Algorithm used to invert a matrix. */
    private final IInverseAlgorithm inverseAlgo;
    /** Algorithm used to compute trace of a matrix. */
    private final ITraceAlgorithm traceAlgo;

    /**
     * Create MatrixOperations with specific algorithm implementations.
     *
     * @param detAlgorithm determinant algorithm
     * @param multiplyByScalarAlgorithm multiply-by-scalar algorithm
     * @param multiplyAlgorithm matrix multiplication algorithm
     * @param addAlgorithm matrix addition algorithm
     * @param substractAlgorithm matrix subtraction algorithm
     * @param transposeAlgorithm matrix transpose algorithm
     * @param inverseAlgorithm matrix inverse algorithm
     * @param traceAlgorithm matrix trace algorithm
     */
    public MatrixOperations(
        IDeterminantAlgorithm detAlgorithm,
        IMultiplyByScalarAlgorithm multiplyByScalarAlgorithm,
        IMultiplyAlgorithm multiplyAlgorithm,
        IAddAlgorithm addAlgorithm,
        ISubstractAlgorithm substractAlgorithm,
        ITransposeAlgorithm transposeAlgorithm,
        IInverseAlgorithm inverseAlgorithm,
        ITraceAlgorithm traceAlgorithm
    ) {
        detAlgo = detAlgorithm;
        multiplyByScalarAlgo = multiplyByScalarAlgorithm;
        multiplyAlgo = multiplyAlgorithm;
        addAlgo = addAlgorithm;
        substractAlgo = substractAlgorithm;
        transposeAlgo = transposeAlgorithm;
        inverseAlgo = inverseAlgorithm;
        traceAlgo = traceAlgorithm;
    }

    /**
     * Compute determinant of a matrix.
     *
     * @param a matrix to compute determinant for
     * @return determinant value
     * @throws MatrixException when matrix is not square
     */
    public double determinant(IMatrix a) throws MatrixException {
        return detAlgo.execute(a);
    }

    /**
     * Multiply two matrices.
     *
     * @param a left operand matrix
     * @param b right operand matrix
     * @return product matrix
     * @throws MatrixException when matrices cannot be multiplied
     */
    public IMatrix multiply(IMatrix a, IMatrix b) throws MatrixException {
        return multiplyAlgo.execute(a, b);
    }

    /**
     * Multiply matrix by scalar value.
     *
     * @param a matrix to scale
     * @param scalar scaling factor
     * @return scaled matrix
     * @throws MatrixException when operation fails
     */
    public IMatrix multiplyByScalar(IMatrix a, double scalar) throws MatrixException {
        return multiplyByScalarAlgo.execute(a, scalar);
    }

    /**
     * Add two matrices.
     *
     * @param a first operand matrix
     * @param b second operand matrix
     * @return sum matrix
     * @throws MatrixException when matrices cannot be added
     */
    public IMatrix add(IMatrix a, IMatrix b) throws MatrixException {
        return addAlgo.execute(a, b);
    }

    /**
     * Subtract two matrices.
     *
     * @param a minuend matrix
     * @param b subtrahend matrix
     * @return difference matrix
     * @throws MatrixException when matrices cannot be subtracted
     */
    public IMatrix substract(IMatrix a, IMatrix b) throws MatrixException {
        return substractAlgo.execute(a, b);
    }

    /**
     * Compute matrix transpose.
     *
     * @param a matrix to transpose
     * @return transposed matrix
     * @throws MatrixException when operation fails
     */
    public IMatrix transpose(IMatrix a) throws MatrixException {
        return transposeAlgo.execute(a);
    }

    /**
     * Compute matrix inverse.
     *
     * @param a matrix to invert
     * @return inverse matrix
     * @throws MatrixException when matrix is not invertible
     */
    public IMatrix inverse(IMatrix a) throws MatrixException {
        return inverseAlgo.execute(a);
    }

    /**
     * Compute matrix trace.
     *
     * @param a matrix to compute trace for
     * @return trace value
     * @throws MatrixException when matrix is not square
     */
    public double trace(IMatrix a) throws MatrixException {
        return traceAlgo.execute(a);
    }

}
