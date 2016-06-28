package run;

import model.*;
import solvers.TetrisSolver;
import util.TetriminoRandomizer;

import java.util.ArrayList;
import java.util.Random;

public class TestBoardPenaltyParameters {
    public static void main(String[] args) {
        int numTries = 10;
        if (args.length > 0) {
            try {
                numTries = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Usage: java TestBoardPenaltyParameters [numTries]");
            }
        }
        TetrisSolver bestSolver = createGoodRandomSolver(numTries);
    }

    static TetrisSolver createGoodRandomSolver(int tries) {
        System.out.println("Starting to test standard solver...");
        TetrisSolver bestSolver = new TetrisSolver(BoardPenaltyParameters.getStandardParameters());
        double bestResult = getAverageSurvival(bestSolver);
        System.out.println("Standard solver got score: " + bestResult);
        System.out.println("Starting to test new random solvers...");
        for (int i = 1; i <= tries; i++) {
            TetrisSolver solver = new TetrisSolver(BoardPenaltyParameters.getRandomParameters());
            double result = getAverageSurvival(solver);
            System.out.println("Solver #" + i + " got score: " + result);
            if (result > bestResult) {
                bestResult = result;
                bestSolver = solver;
                System.out.println("New best solver (" + bestResult + " points):");
                System.out.println(bestSolver.getParams());
            }
        }
        System.out.println("Best solver (" + bestResult + " points):");
        System.out.println(bestSolver.getParams());
        return bestSolver;
    }

    static double getAverageSurvival(TetrisSolver solver) {
        double res = 0;
        int TRIES = 30;
        for (int i = 0; i < TRIES; i++) {
            TetriminoRandomizer rand = new TetriminoRandomizer();
            res += calculateSurvivalTurns(solver, rand);
        }
        res /= TRIES;
        return res;
    }

    static int calculateSurvivalTurns(TetrisSolver solver, TetriminoRandomizer rand) {
        int res = 0;
        BoardState game = new BoardState(rand);
        while (!game.isGameOver()) {
            TetrisMove bestMove = solver.getMove(game);

            if (bestMove == null) {
                break;
            }
            game = game.doMove(bestMove);
            res++;
        }
        return res;
    }
}
