package run;

import controllers.MoveTyper;
import controllers.TetrisFriendsPlayer;
import solvers.TetrisSolver;

import java.awt.*;

public class MarathonPlayer {

    static final int BOARD_X = 608; //598;
    static final int BOARD_Y = 192; //261;
    static final int BOARD_WIDTH = 769 - BOARD_X; //776-BOARD_X;
    static final int BOARD_HEIGHT = 515 - BOARD_Y; //620-BOARD_Y;

    public static void main(String[] args) throws AWTException {
        MoveTyper controller = new MoveTyper();
        TetrisSolver solver = new TetrisSolver();
        Rectangle boardPosition = new Rectangle(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);
        new TetrisFriendsPlayer(solver, controller, boardPosition, null).run();
    }
}
