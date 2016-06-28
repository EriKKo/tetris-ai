package run;

import controllers.MoveTyper;
import controllers.TetrisFriendsPlayer;
import solvers.TetrisSolver;

import java.awt.*;

/**
 *  Class that can be used to play the Battle 2P mode at tetrisfriends.com.
 *
 *  The board coordinates are completely hard coded and will probably have to be
 *  adjusted depending on screen resolution and possible gui-changes on the target website.
 *  Examples of successful screen reads can be found in the root directory.
 */
public class Battle2pPlayer {

    static final int BOARD_X = 443;
    static final int BOARD_Y = 217;
    static final int BOARD_WIDTH = 604 - BOARD_X;
    static final int BOARD_HEIGHT = 541 - BOARD_Y;

    static final int NEXT_X = 628;
    static final int NEXT_Y = 238;
    static final int NEXT_WIDTH = 673 - NEXT_X;
    static final int NEXT_HEIGHT = 482 - NEXT_Y;

    public static void main(String[] args) throws AWTException {
        MoveTyper controller = new MoveTyper(60, 60);
        TetrisSolver solver = new TetrisSolver();
        Rectangle boardPosition = new Rectangle(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);
        Rectangle nextBoxPosition = new Rectangle(NEXT_X, NEXT_Y, NEXT_WIDTH, NEXT_HEIGHT);
        new TetrisFriendsPlayer(solver, controller, boardPosition, nextBoxPosition).run();
    }
}
