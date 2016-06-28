package model;

import java.util.Random;

public class BoardPenaltyParameters {

    public double COVER_RIGHTMOST_PENALTY = 50.0;
    public double COVERED_PENALTY = 19.65102385016857;
    public double HEIGHT_RIGHTMOST_PENALTY = 5.0;
    public double CONSECUTIVE_CHANGE_PENALTY = 1.7050572208699066;
    public double SMALL_PIT_PENALTY = 5.604402435574228;
    public double BIG_PIT_PENALTY = 22.46371775813374;
    public double HEIGHT_DIFFERENCE_FACTOR = 5.2652791317118375;

    private BoardPenaltyParameters() {

    }

    public static BoardPenaltyParameters getStandardParameters() {
        return new BoardPenaltyParameters();
    }

    public static BoardPenaltyParameters getRandomParameters() {
        BoardPenaltyParameters params = new BoardPenaltyParameters();
        Random rand = new Random();
        params.COVER_RIGHTMOST_PENALTY = 50;
        params.COVERED_PENALTY = rand.nextDouble()*30;
        params.HEIGHT_RIGHTMOST_PENALTY = 5;
        params.CONSECUTIVE_CHANGE_PENALTY = rand.nextDouble()*10;
        params.SMALL_PIT_PENALTY = rand.nextDouble()*10;
        params.BIG_PIT_PENALTY = rand.nextDouble()*30;
        params.HEIGHT_DIFFERENCE_FACTOR = rand.nextDouble()*10;
        return params;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("params.COVER_RIGHTMOST_PENALTY = " + COVER_RIGHTMOST_PENALTY + ";\n");
        sb.append("params.COVERED_PENALTY = " + COVERED_PENALTY + ";\n");
        sb.append("params.HEIGHT_RIGHTMOST_PENALTY = " + HEIGHT_RIGHTMOST_PENALTY + ";\n");
        sb.append("params.CONSECUTIVE_CHANGE_PENALTY = " + CONSECUTIVE_CHANGE_PENALTY + ";\n");
        sb.append("params.SMALL_PIT_PENALTY = " + SMALL_PIT_PENALTY + ";\n");
        sb.append("params.BIG_PIT_PENALTY = " + BIG_PIT_PENALTY + ";\n");
        sb.append("params.HEIGHT_DIFFERENCE_FACTOR = " + HEIGHT_DIFFERENCE_FACTOR + ";\n");
        return sb.toString();
    }
}
