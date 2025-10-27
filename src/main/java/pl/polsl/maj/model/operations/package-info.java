/**
 * Abstractions and facade for matrix operations.
 * <p>
 * This package defines the operation interfaces (strategies) used by the
 * application and provides the {@link pl.polsl.maj.model.operations.MatrixOperations}
 * facade which aggregates concrete algorithm implementations.
 * </p>
 *
 * <p>Interfaces include:</p>
 * <ul>
 *   <li>{@link pl.polsl.maj.model.operations.IMultiplyAlgorithm}</li>
 *   <li>{@link pl.polsl.maj.model.operations.IAddAlgorithm}</li>
 *   <li>{@link pl.polsl.maj.model.operations.ISubstractAlgorithm}</li>
 *   <li>{@link pl.polsl.maj.model.operations.IDeterminantAlgorithm}</li>
 *   <li>{@link pl.polsl.maj.model.operations.IInverseAlgorithm}</li>
 *   <li>{@link pl.polsl.maj.model.operations.ITransposeAlgorithm}</li>
 *   <li>{@link pl.polsl.maj.model.operations.IMultiplyByScalarAlgorithm}</li>
 *   <li>{@link pl.polsl.maj.model.operations.ITraceAlgorithm}</li>
 * </ul>
 *
 * @author piotr.maj
 * @version 1.0.0
 */
package pl.polsl.maj.model.operations;