package pl.polsl.maj.model.operations.simpleoperations;

import lombok.NoArgsConstructor;
import pl.polsl.maj.exceptions.MatrixException;
import pl.polsl.maj.model.IMatrix;
import pl.polsl.maj.model.operations.ITraceAlgorithm;

/**
 * Simple implementation of trace computation for square matrices.
 * <p>
 * The trace is defined as the sum of the diagonal elements of a square matrix.
 * </p>
 *
 * @author piotr.maj
 * @version 1.0.1
 */
@NoArgsConstructor
public class SimpleTrace implements ITraceAlgorithm {

    /**
     * Compute the trace (sum of diagonal) of the provided matrix.
     *
     * @param a square matrix
     * @return trace value
     * @throws MatrixException when matrix is not square
     */
    @Override
    public double execute(IMatrix a) throws MatrixException {
        if(!a.isSquare()) {
            throw new MatrixException("Matrix must be squared");
        }

        double sum = 0.0;
        for (int i = 0; i < a.getRows(); ++i) {
            sum += a.get(i, i);
        }

        return sum;
    }
}
