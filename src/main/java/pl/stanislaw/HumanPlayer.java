package pl.stanislaw;

public class HumanPlayer<T> implements Player<T>{
    private final String name;
    private final T type;

    public HumanPlayer(String name, T type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getType() {
        return type;
    }

    @Override
    public int[] move() {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

}
