package util;

import model.Tetrimino;

import java.util.Random;

public class TetriminoRandomizer {

    private Random rand;
    private int prevprev = -1;
    private int prev = -1;

    public TetriminoRandomizer() {
        rand = new Random();
    }

    public TetriminoRandomizer(int seed) {
        rand = new Random(seed);
    }

    public Tetrimino getNext() {
        int n = rand.nextInt(7);
        while (n == prev && prev == prevprev) {
            n = rand.nextInt(7);
        }
        prevprev = prev;
        prev = n;
        return Tetrimino.getType(n);
    }
}
