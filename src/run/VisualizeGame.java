package run;

import model .*;
import solvers.TetrisSolver;
import util.TetriminoRandomizer;

public class VisualizeGame {
    
    public static void main(String[] args) throws InterruptedException {
        TetriminoRandomizer rand = new TetriminoRandomizer();
        TetrisSolver solver = new TetrisSolver(BoardPenaltyParameters.getStandardParameters());
        int sleepTime = 500;
        if (args.length > 0) {
            try {
                sleepTime = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Usage: java VisualizeGame [sleepTime]");
            }
        }
        visualizeGame(solver, rand, sleepTime);
    }

    static void visualizeGame(TetrisSolver solver, TetriminoRandomizer rand, int sleepTime) throws InterruptedException {
        BoardState game = new BoardState(rand);
        int round = 0;
        while (!game.isGameOver()) {
            System.out.println("Round #" + round + ":");
            System.out.print(game);
            System.out.println("PENALTY: " + game.getBoard().getBoardPenalty(solver.getParams()));
            System.out.println();
            TetrisMove bestMove = solver.getMove(game);

            if (bestMove == null) {
                break;
            }
            game = game.doMove(bestMove);
            round++;
            Thread.sleep(sleepTime);
        }
    }

}
