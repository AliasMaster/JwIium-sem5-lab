package pl.polsl.maj.view;

public interface IView {
    public void showMenu();
    public void showMessage(String message);
    public Input getInput();
    public void showMatrixCreator();
    public String getMatrix();
}