package pl.polsl.maj.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pl.polsl.maj.exceptions.MatrixException;

import pl.polsl.maj.gui.MatrixCreatorDialog;
import pl.polsl.maj.gui.MessageUtils;
import pl.polsl.maj.gui.ScalarInputDialog;
import pl.polsl.maj.view.IView;

/**
 * Swing-based implementation of the {@link pl.polsl.maj.view.IView} interface.
 *
 * <p>This class provides a simple GUI for the matrix calculator: a table to
 * display matrix contents and buttons for available operations. User actions
 * are communicated to the controller via internal blocking queues.</p>
 * 
 * @author piotr.maj
 * @version 1.0.1
 */
public class SwingView implements IView {
    private final JFrame frame;

    private final JTable table;
    private final DefaultTableModel tableModel;

    private final BlockingQueue<String> menuBuffer = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> matrixBuffer = new LinkedBlockingQueue<>();

    /**
     * Constructs and shows the main application window.
     * <p>The constructor prepares the components and makes the frame visible.
     * Long-running or blocking operations must not be executed on the EDT.</p>
     */
    public SwingView() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        this.frame = new JFrame("Matrix calculator");
        frame.setLayout(new BorderLayout(6, 6));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(600, 300));
        frame.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnDeterminant = new JButton("Calculate determinant");
        JButton btnScalar = new JButton("Multiply by scalar");
        JButton btnMultiply = new JButton("Multiply by other matrix");
        JButton btnAdd = new JButton("Add matrix");
        JButton btnSubstract = new JButton("Substract matrix");
        JButton btnTranspose = new JButton("Transpose");
        JButton btnInverse = new JButton("Inverse");
        JButton btnTrace = new JButton("Trace");

        btnPanel.add(btnDeterminant);
        btnPanel.add(btnScalar);
        btnPanel.add(btnMultiply);
        btnPanel.add(btnAdd);
        btnPanel.add(btnSubstract);
        btnPanel.add(btnTranspose);
        btnPanel.add(btnInverse);
        btnPanel.add(btnTrace);

        btnDeterminant.setToolTipText("Click this component to calculate determinant");
        btnDeterminant.getAccessibleContext().setAccessibleDescription("Click this component to calculate determinant");
        btnDeterminant.setMnemonic(KeyEvent.VK_D);

        btnScalar.setToolTipText("Click this component to multiply matrix by scalar");
        btnScalar.getAccessibleContext().setAccessibleDescription("Click this component to multiply matrix by scalar");
        btnScalar.setMnemonic(KeyEvent.VK_S);

        btnMultiply.setToolTipText("Click this component to multiply matrix by other matrix");
        btnMultiply.getAccessibleContext().setAccessibleDescription("Click this component to multiply matrix by other matrix");
        btnMultiply.setMnemonic(KeyEvent.VK_M);

        btnAdd.setToolTipText("Click this component to add matrix");
        btnAdd.getAccessibleContext().setAccessibleDescription("Click this component to add matrix");
        btnAdd.setMnemonic(KeyEvent.VK_A);

        btnSubstract.setToolTipText("Click this component to substract matrix");
        btnSubstract.getAccessibleContext().setAccessibleDescription("Click this component to substract matrix");
        btnSubstract.setMnemonic(KeyEvent.VK_S);

        btnTranspose.setToolTipText("Click this component to transpose matrix");
        btnTranspose.getAccessibleContext().setAccessibleDescription("Click this component to transpose matrix");
        btnTranspose.setMnemonic(KeyEvent.VK_T);

        btnInverse.setToolTipText("Click this component to inverse matrix");
        btnInverse.getAccessibleContext().setAccessibleDescription("Click this component to inverse matrix");
        btnInverse.setMnemonic(KeyEvent.VK_I);

        btnTrace.setToolTipText("Click this component to calculate trace of the matrix");
        btnTrace.getAccessibleContext().setAccessibleDescription("Click this component to calculate trace of the matrix");
        btnTrace.setMnemonic(KeyEvent.VK_R);

        frame.add(btnPanel, BorderLayout.NORTH);

        btnDeterminant.addActionListener(e -> menuBuffer.offer(OperationCode.DETERMINANT.code()));

        btnScalar.addActionListener(e -> menuBuffer.offer(OperationCode.SCALAR.code()));

        btnMultiply.addActionListener(e -> menuBuffer.offer(OperationCode.MULTIPLY.code()));

        btnAdd.addActionListener(e -> menuBuffer.offer(OperationCode.ADD.code()));

        btnSubstract.addActionListener(e -> menuBuffer.offer(OperationCode.SUBSTRACT.code()));

        btnTranspose.addActionListener(e -> menuBuffer.offer(OperationCode.TRANSPOSE.code()));

        btnInverse.addActionListener(e -> menuBuffer.offer(OperationCode.INVERSE.code()));

        btnTrace.addActionListener(e -> menuBuffer.offer(OperationCode.TRACE.code()));

        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void showMenu() {
        if (!frame.isVisible()) frame.setVisible(true);
        frame.requestFocus();
    }

    @Override
    public String getMenuOption() {
        try {
            String option = menuBuffer.take();
            
            return option;
        } catch(InterruptedException ex) {
            frame.setVisible(false);
            frame.dispose();
            return "0";
        }
    }

    @Override
    public void showMessage(String message) {
        MessageUtils.info(frame, message);
    }

    @Override
    public void showErrorMessage(String message) {
        MessageUtils.error(frame, message);
    }

    @Override
    public String getMatrix() {
        try {
            return matrixBuffer.take();
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
            return "";
        }
    }

    @Override
    public void showMatrixCreator() {
        MatrixCreatorDialog d = new MatrixCreatorDialog(frame);
        String res = d.showDialog();
        if (res != null) {
            matrixBuffer.offer(res);
        } else {
            menuBuffer.offer("0");
        }
    }

    @Override
    public void showMatrix(String matrix) {
        if(matrix == null || matrix.isEmpty()) {
            showErrorMessage("Empty matrix");
            return;
        }
        updateTableFromMatrixString(matrix);
    }

    @Override
    public double getScalar() {
        Double d = ScalarInputDialog.askScalar(frame, "Scalar");
        return d == null ? Double.NaN : d;
    }

    private void updateTableFromMatrixString(String matrix) {
        try {
            java.util.List<String> tokens = java.util.Arrays.asList(matrix.trim().split("\\s+"));
            if (tokens.size() < 3) throw new MatrixException("Invalid matrix format");
            int rows = Integer.parseInt(tokens.get(0));
            int cols = Integer.parseInt(tokens.get(1));
            if (tokens.size() - 2 != rows * cols) throw new MatrixException("Invalid number of elements");

            // build column names and data using collections, then convert to vectors
            java.util.Vector<java.util.Vector<Object>> tableData = new java.util.Vector<>();
            int idx = 2;
            for (int r = 0; r < rows; r++) {
                java.util.Vector<Object> rowVec = new java.util.Vector<>();
                for (int c = 0; c < cols; c++) {
                    String valueStr = tokens.get(idx++);
                    try {
                        double value = Double.parseDouble(valueStr);
                        // Format to 3 significant figures
                        valueStr = String.format("%.3g", value);
                    } catch (NumberFormatException ignored) {
                        // Keep original string if not a valid number
                    }
                    rowVec.add(valueStr);
                }
                tableData.add(rowVec);
            }
            java.util.Vector<Object> colNames = new java.util.Vector<>();
            for (int i = 0; i < cols; i++) colNames.add("C" + (i+1));
            tableModel.setDataVector(tableData, colNames);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(80);
            }
        } catch (MatrixException ex) {
            showErrorMessage("Matrix parse error: " + ex.getMessage());
        }
    }
    
}
