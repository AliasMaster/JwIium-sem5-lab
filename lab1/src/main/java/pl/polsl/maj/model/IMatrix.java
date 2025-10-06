package pl.polsl.maj.model;

public interface IMatrix {
    public abstract int getRows();
    
    public abstract int getCols();
    
    public abstract boolean isSquare();
    
    public abstract double get(int r, int c);
    
    public abstract void set(int r, int c, double value);    

    public void init(int r, int c);

    public void init(double[][] data);
}