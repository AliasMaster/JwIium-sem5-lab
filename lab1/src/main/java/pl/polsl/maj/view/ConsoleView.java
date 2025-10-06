package pl.polsl.maj.view;

public class ConsoleView implements IView {
    @Override
    public void showMenu() {

    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public Input getInput() {
        return Input.End;
    }

    @Override
    public void showMatrixCreator() {}

    @Override
    public String getMatrix() {
        return "";
    }
}