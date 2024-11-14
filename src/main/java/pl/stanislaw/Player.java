package pl.stanislaw;

public class Player<T> {

    private final String name;
    private final T type;

    public Player(String name, T type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public T getType() {
        return type;
    }

    public int[] moveIndex(int x , int y){
        return new int[]{x, y};
    }

    @Override
    public String toString() {
        return name;
    }
}
