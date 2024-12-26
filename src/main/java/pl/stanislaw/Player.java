package pl.stanislaw;

public interface Player<T> {

    public String getName();
    public T getType();
    public int[] move();

}
