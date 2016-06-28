package model;

import vision.CellContent;

public class Tetrimino {

    public static int TYPE_I = 0;
    public static int TYPE_T = 1;
    public static int TYPE_O = 2;
    public static int TYPE_L = 3;
    public static int TYPE_J = 4;
    public static int TYPE_S = 5;
    public static int TYPE_Z = 6;

    private static Tetrimino[][] minos;

    static {
        minos = new Tetrimino[7][4];

        // I-MINO
        minos[TYPE_I][0] = new Tetrimino(new int[][] {{0,0},{0,-1},{0,1},{0,2}}, "I-mino", TYPE_I, 0);
        minos[TYPE_I][1] = new Tetrimino(new int[][] {{0,1},{-1,1},{-2,1},{1,1}}, "I-mino", TYPE_I, 1);
        minos[TYPE_I][2] = new Tetrimino(new int[][] {{0,0},{0,-1},{0,1},{0,2}}, "I-mino", TYPE_I, 2);
        minos[TYPE_I][3] = new Tetrimino(new int[][] {{0,0},{-1,0},{-2,0},{1,0}}, "I-mino", TYPE_I, 3);

        // T-MINO
        for (int rot = 0; rot < 4; rot++) {
            minos[TYPE_T][rot] = new Tetrimino(new int[][] {{0,0},{0,-1},{0,1},{1,0}}, "T-mino", TYPE_T, rot);
            for (int i = 0; i < rot; i++) {
                minos[TYPE_T][rot].rotate();
            }
        }

        // O-MINO
        for (int rot = 0; rot < 4; rot++) {
            minos[TYPE_O][rot] = new Tetrimino(new int[][] {{0,0},{0,1},{-1,1},{-1,0}}, "O-mino", TYPE_O, rot);
            // Don't rotate O-types
        }

        // L-MINO
        for (int rot = 0; rot < 4; rot++) {
            minos[TYPE_L][rot] = new Tetrimino(new int[][] {{0,0},{0,-1},{0,1},{1,1}}, "L-mino", TYPE_L, rot);
            for (int i = 0; i < rot; i++) {
                minos[TYPE_L][rot].rotate();
            }
        }

        // J-MINO
        for (int rot = 0; rot < 4; rot++) {
            minos[TYPE_J][rot] = new Tetrimino(new int[][] {{0,0},{0,-1},{1,-1},{0,1}}, "J-mino", TYPE_J, rot);
            for (int i = 0; i < rot; i++) {
                minos[TYPE_J][rot].rotate();
            }
        }

        // S-MINO
        for (int rot = 0; rot < 4; rot++) {
            minos[TYPE_S][rot] = new Tetrimino(new int[][] {{0,0},{1,0},{1,1},{0,-1}}, "S-mino", TYPE_S, rot);
            for (int i = 0; i < rot; i++) {
                minos[TYPE_S][rot].rotate();
            }
        }

        // Z-MINO
        for (int rot = 0; rot < 4; rot++) {
            minos[TYPE_Z][rot] = new Tetrimino(new int[][] {{0,0},{1,0},{1,-1},{0,1}}, "Z-mino", TYPE_Z, rot);
            for (int i = 0; i < rot; i++) {
                minos[TYPE_Z][rot].rotate();
            }
        }
    }

    private int[][] points;
    private String name;
    private int type;
    private int rotation;

    private Tetrimino(int[][] p, String name, int type, int rotation) {
        this.points = p;
        this.name = name;
        this.type = type;
        this.rotation = rotation;
    }

    public static Tetrimino getType(int type) {
        return minos[type][0];
    }

    public static Tetrimino createFromCellContent(CellContent cell) {
        if (cell == CellContent.ACTIVE_I_MINO) {
            return minos[TYPE_I][0];
        } else if (cell == CellContent.ACTIVE_J_MINO) {
            return minos[TYPE_J][0];
        } else if (cell == CellContent.ACTIVE_L_MINO) {
            return minos[TYPE_L][0];
        } else if (cell == CellContent.ACTIVE_O_MINO) {
            return minos[TYPE_O][0];
        } else if (cell == CellContent.ACTIVE_T_MINO) {
            return minos[TYPE_T][0];
        } else if (cell == CellContent.ACTIVE_S_MINO) {
            return minos[TYPE_S][0];
        } else if (cell == CellContent.ACTIVE_Z_MINO) {
            return minos[TYPE_Z][0];
        } else {
            return null;
        }
    }

    private void rotate() {
        for (int[] point : points) {
            int nr = -point[1];
            int nc = point[0];
            point[0] = nr;
            point[1] = nc;
        }
    }

    public Tetrimino rotateClockWise() {
        return minos[type][(rotation+1)&3];
    }

    public Tetrimino rotateCounterClockWise() {
        return minos[type][(rotation+3)&3];
    }

    public int[][] getPoints() {
        return points;
    }

    public String toString() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getRotation() {
        return rotation;
    }
}
