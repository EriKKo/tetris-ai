package model;

import vision.CellContent;

public class TetrisBoard {

    public static final int HEIGHT = 22;
    public static final int VISIBLE_HEIGHT = 20;
    public static final int WIDTH = 10;

    public static final int EMPTY = 0;
    public static final int MINO = 1;
    public static final int UNBREAKABLE = 2;

    private static final char OCCUPIED_CHAR = '#';
    private static final char EMPTY_CHAR = '.';

    private int[][] board;
    private boolean clearedLine = false;

    public TetrisBoard() {
        this(new int[HEIGHT][WIDTH]);
    }

    public TetrisBoard(int[][] board) {
        this.board = board;
    }

    public static TetrisBoard createFromScreenReading(CellContent[][] cells) {
        TetrisBoard board = new TetrisBoard();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].isFixed()) {
                    if (cells[i][j] == CellContent.UNBREAKABLE) {
                        board.board[i][j] = UNBREAKABLE;
                    } else {
                        board.board[i][j] = MINO;
                    }
                } else {
                    board.board[i][j] = EMPTY;
                }
            }
        }
        return board;
    }

    public int[][] getBoard() {
        int[][] res = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            System.arraycopy(board[i], 0, res[i], 0, WIDTH);
        }
        return res;
    }

    public boolean clearedLine() {
        return clearedLine;
    }

    public int[] getHeights() {
        int[] h = new int[WIDTH];
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (board[r][c] != EMPTY) {
                    h[c] = r + 1;
                }
            }
        }
        return h;
    }

    public TetrisBoard createCopy() {
        int[][] newBoard = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, WIDTH);
        }
        return new TetrisBoard(newBoard);
    }

    private void removeFullLines() {
        clearedLine = false;
        int row = 0;
        for (int r = 0; r < HEIGHT; r++) {
            boolean full = true;
            for (int c = 0; c < WIDTH; c++) {
                if (board[r][c] != MINO) {
                    full = false;
                }
            }
            if (!full) {
                for (int c = 0; c < WIDTH; c++) {
                    board[row][c] = board[r][c];
                }
                row++;
            } else {
                clearedLine = true;
            }
        }
        while (row < HEIGHT) {
            for (int c = 0; c < WIDTH; c++) {
                board[row][c] = EMPTY;
            }
            row++;
        }
    }

    public boolean canPlaceTetrimino(Tetrimino mino, int r, int c) {
        for (int[] p : mino.getPoints()) {
            int pr = p[0] + r;
            int pc = p[1] + c;
            if (!TetrisBoard.checkPointBounds(pr, pc) || board[pr][pc] != EMPTY) {
                return false;
            }
        }
        return true;
    }

    public TetrisBoard placeTetrimino(Tetrimino mino, int r, int c) {
        TetrisBoard newBoard = createCopy();
        for (int[] p : mino.getPoints()) {
            int pr = p[0] + r;
            int pc = p[1] + c;
            newBoard.board[pr][pc] = MINO;
        }
        newBoard.removeFullLines();
        return newBoard;
    }

    public boolean canDropTetrimino(Tetrimino mino, int c) {
        return canPlaceTetrimino(mino, VISIBLE_HEIGHT - 1, c);
    }

    public TetrisBoard dropMino(Tetrimino mino, int c) {
        if (!canDropTetrimino(mino, c)) {
            return null;
        }
        for (int r = VISIBLE_HEIGHT - 2; r >= 0; r--) {
            if (!canPlaceTetrimino(mino, r, c)) {
                return placeTetrimino(mino, r+1, c);
            }
        }
        return placeTetrimino(mino, 0, c);
    }

    public boolean isGameOver() {
        for (int r = VISIBLE_HEIGHT; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (board[r][c] != EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    public double getBoardPenalty(BoardPenaltyParameters params) {
        if (isGameOver()) return Double.POSITIVE_INFINITY;
        double res = 0;
        int[] h = new int[WIDTH];
        int[] cnt = new int[WIDTH];
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (board[r][c] != EMPTY) {
                    h[c] = r+1;
                    cnt[c]++;
                }
            }
        }

        // Check height difference
        int hMin = h[0], hMax = h[0];
        for (int c = 1; c < WIDTH - 1; c++) {
            hMin = Math.min(hMin, h[c]);
            hMax = Math.max(hMax, h[c]);
        }
        hMax = Math.max(hMax, h[WIDTH - 1]);
        res += params.HEIGHT_DIFFERENCE_FACTOR * (hMax - hMin);

        // Check covered
        for (int c = 0; c < WIDTH; c++) {
            int covered = h[c] - cnt[c];
            if (c < WIDTH - 1) {
                res += covered * params.COVERED_PENALTY;
            } else {
                res += covered * params.COVER_RIGHTMOST_PENALTY;
                res += cnt[c] * params.HEIGHT_RIGHTMOST_PENALTY;
            }
        }

        // Check consecutive changes
        int prev = h[0];
        boolean prevChanged = false;
        for (int c = 0; c < WIDTH - 1; c++) {
            if (h[c] != prev) {
                if (prevChanged) {
                    res += params.CONSECUTIVE_CHANGE_PENALTY;
                }
                prevChanged = true;
            } else {
                prevChanged = false;
            }
            prev = h[c];
        }

        // Check pits
        for (int c = 0; c < WIDTH - 1; c++) {
            int left = 5;
            if (c > 0) {
                left = Math.max(0, h[c-1] - h[c]);
            }
            int right = 5;
            if (c < WIDTH - 2) {
                right = Math.max(0, h[c+1] - h[c]);
            }
            if (left >= 2 && right >= 2) {
                if (Math.min(left, right) == 2) {
                    res += params.SMALL_PIT_PENALTY;
                } else {
                    res += params.BIG_PIT_PENALTY;
                }
            }
        }
        return res;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = VISIBLE_HEIGHT - 1; i >= 0; i--) {
            for (int j = 0; j < WIDTH; j++) {
                sb.append(board[i][j] != EMPTY ? OCCUPIED_CHAR : EMPTY_CHAR);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static boolean checkPointBounds(int r, int c) {
        return r >= 0 && r < HEIGHT && c >= 0 && c < WIDTH;
    }
}
