package model;

import util.TetriminoRandomizer;
import vision.CellContent;

public class BoardState {

    private TetriminoRandomizer rand;
    private TetrisBoard board;
    private Tetrimino current;
    private Tetrimino hold;
    private Tetrimino[] next;

    public BoardState() {
        board = new TetrisBoard();
        next = new Tetrimino[5];
    }

    public BoardState(TetriminoRandomizer rand) {
        this.rand = rand;
        board = new TetrisBoard();
        current = rand.getNext();
        hold = null;
        next = new Tetrimino[5];
        for (int i = 0; i < 5; i++) {
            next[i] = rand.getNext();
        }
    }

    private BoardState createCopy() {
        BoardState res = new BoardState();
        res.rand = rand;
        res.board = board.createCopy();
        res.current = current;
        res.hold = hold;
        for (int i = 0; i < next.length; i++) {
            res.next[i] = next[i];
        }
        return res;
    }

    public void applyScreenReading(CellContent[][] cells) {
        board = TetrisBoard.createFromScreenReading(cells);
        for (int i = 0; i < cells.length; i++) {
            for (CellContent cell : cells[i]) {
                Tetrimino newCurrent = Tetrimino.createFromCellContent(cell);
                if (newCurrent != null) {
                    current = newCurrent;
                }
            }
        }
    }

    public void setNext(Tetrimino[] next) {
        this.next = next;
    }

    public TetrisBoard getBoard() {
        return board;
    }

    public Tetrimino getCurrent() {
        return current;
    }

    public void setCurrent(Tetrimino mino) {
        current = mino;
    }

    public Tetrimino gethold() {
        return hold;
    }

    public Tetrimino[] getNext() {
        return next;
    }

    private void shiftnext() {
        current = next[0];
        for (int i = 0; i < next.length - 1; i++) {
            next[i] = next[i+1];
        }
        next[next.length - 1] = null;
    }

    private void fillNext() {
        for (int i = 0; i < next.length; i++) {
            if (next[i] == null) {
                next[i] = rand.getNext();
            }
        }
    }

    public BoardState doMove(TetrisMove move) {
        BoardState res = createCopy();
        res.doMoveHelper(move);
        if (res.board == null) {
            return null;
        } else {
            return res;
        }
    }

    private void doMoveHelper(TetrisMove move) {
        if (move.doHold()) {
            if (hold != null) {
                Tetrimino tmp = hold;
                hold = current;
                current = tmp;
            } else {
                hold = current;
                shiftnext();
                if (current == null) return;
            }
        }
        if (move.getRotation() == 1) {
            current = current.rotateClockWise();
        } else if (move.getRotation() == 2) {
            current = current.rotateClockWise();
            current = current.rotateClockWise();
        } else if (move.getRotation() == 3) {
            current = current.rotateCounterClockWise();
        }
        board = board.dropMino(current, move.getPosition());
        shiftnext();
        if (rand != null) {
            fillNext();
        }
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public boolean clearedLine() {
        return board.clearedLine();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int[][] b = board.getBoard();
        for (int i = TetrisBoard.VISIBLE_HEIGHT - 1; i >= 0; i--) {
            if (i == TetrisBoard.VISIBLE_HEIGHT - 2) {
                sb.append("CURR:\t");
            } else if (i == TetrisBoard.VISIBLE_HEIGHT - 3) {
                sb.append(current + "\t");
            } else if (i == TetrisBoard.VISIBLE_HEIGHT - 4) {
                sb.append("HOLD:\t");
            } else if (i == TetrisBoard.VISIBLE_HEIGHT - 5 && hold != null) {
                sb.append(hold + "\t");
            } else {
                sb.append("\t");
            }
            for (int j = 0; j < TetrisBoard.WIDTH; j++) {
                sb.append(b[i][j] != TetrisBoard.EMPTY ? '#' : '.');
            }
            if (i == TetrisBoard.VISIBLE_HEIGHT - 2) {
                sb.append(" NEXT:");
            } else if (i <= TetrisBoard.VISIBLE_HEIGHT - 3 && i >= TetrisBoard.VISIBLE_HEIGHT - 7) {
                sb.append(" " + next[TetrisBoard.VISIBLE_HEIGHT - 3 - i]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
