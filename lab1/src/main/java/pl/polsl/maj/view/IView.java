package pl.polsl.maj.view;

public interface IView {
    public void showMenu();
    public MenuOption getMenuOption();
    public void showMessage(String message);
    public void showErrorMessage(String message);
    public String getMatrix();
    public void showMatrixCreator();
    public void showMatrix(String matrix);
    public double getScalar();
}