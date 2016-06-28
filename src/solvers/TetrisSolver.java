package solvers;

import model.*;

public class TetrisSolver {

    private static final int MAX_DEPTH = 1;

    private BoardPenaltyParameters params;

    public TetrisSolver() {
        this(BoardPenaltyParameters.getStandardParameters());
    }

    public TetrisSolver(BoardPenaltyParameters params) {
        this.params = params;
    }

    public BoardPenaltyParameters getParams() {
        return params;
    }

    private TetrisMove bestMove;
    private double getMoveRec(BoardState game, int depth) {
        if (depth == MAX_DEPTH || game.getCurrent() == null) {
            return game.getBoard().getBoardPenalty(params);
        }
        double bestPenalty = Double.POSITIVE_INFINITY;
        for (int hold = 0; hold < 2; hold++) {
            for (int pos = 0; pos < TetrisBoard.WIDTH; pos++) {
                for (int rot = 0; rot < 4; rot++) {
                    TetrisMove move = new TetrisMove(hold == 1, pos, rot);
                    BoardState game2 = game.doMove(move);
                    if (game2 != null) {
                        double p = getMoveRec(game2, depth + 1);
                        if (p < bestPenalty) {
                            bestPenalty = p;
                            if (depth == 0) {
                                bestMove = move;
                            }
                        }
                    }
                }
            }
        }
        return bestPenalty;
    }

    public TetrisMove getMove(BoardState game) {
        bestMove = null;

        if (game.gethold() == null) {
            bestMove = new TetrisMove(true, 4, 0);
        }

        if (bestMove == null && game.getCurrent().getType() == Tetrimino.TYPE_I) {
            int[] h = game.getBoard().getHeights();
            boolean fillRight = true;
            for (int c = 0; c < h.length - 1; c++) {
                if (h[c] - h[h.length - 1] < 4) {
                    fillRight = false;
                }
            }
            if (fillRight) {
                bestMove = new TetrisMove(false, TetrisBoard.WIDTH - 2, 1);
            }
        }

        if (bestMove == null) {
            getMoveRec(game, 0);
        }
        return bestMove;
    }
}
