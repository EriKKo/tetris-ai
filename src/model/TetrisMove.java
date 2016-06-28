package model;

public class TetrisMove {

    private boolean hold;
    private int rotation;
    private int position;

    public TetrisMove(boolean hold, int position, int rotation) {
        this.hold = hold;
        this.rotation = rotation;
        this.position = position;
    }

    public boolean doHold() {
        return hold;
    }

    public int getRotation() {
        return rotation;
    }

    public int getPosition() {
        return position;
    }

    public String toString() {
        return "TetrisMove(" + hold + ","+position+","+rotation+")";
    }
}
