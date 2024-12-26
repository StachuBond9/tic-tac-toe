package pl.stanislaw;

import java.util.Random;

public class BotPlayer<T> implements Player<T>{

    private final String name;
    private final T type;

    public BotPlayer( T type) {
        this.name = "Bot Player";
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
    public int[] move(){
        Random random = new Random();
        int x = random.nextInt()%3;
        Random random1 =  new Random();
        int y =  random1.nextInt()%3;
        return new int[]{x, y};
    }

    @Override
    public String toString() {
        return name;
    }
}
