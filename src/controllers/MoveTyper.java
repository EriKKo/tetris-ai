package controllers;

import model.Tetrimino;
import model.TetrisMove;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MoveTyper {

    private int keyDelay;
    private int keyPressTime;

    private Robot robot;

    public MoveTyper() throws AWTException {
        this(50, 50);
    }

    public MoveTyper(int keyDelay, int keyPressTime) throws AWTException {
        this.keyDelay = keyDelay;
        this.keyPressTime = keyPressTime;
        robot = new Robot();
    }

    public void doHold() {
        doKeyPress(KeyEvent.VK_C);
    }

    public void doRotateClockwise() {
        doKeyPress(KeyEvent.VK_X);
    }

    public void doRotateCounterClockwise() {
        doKeyPress(KeyEvent.VK_Z);
    }

    public void doDrop() {
        doKeyPress(KeyEvent.VK_SPACE);
    }

    public void doMoveLeft() {
        doKeyPress(KeyEvent.VK_LEFT);
    }

    public void doMoveRight() {
        doKeyPress(KeyEvent.VK_RIGHT);
    }

    public void doTetrisMove(TetrisMove move) {
        if (move.doHold()) {
            doHold();
        }
        int rot = move.getRotation();
        if (rot == 1) {
            doRotateClockwise();
        } else if (rot == 2) {
            doRotateClockwise();
            doRotateClockwise();
        } else if (rot == 3) {
            doRotateCounterClockwise();
        }
        int currPos = 4;
        int goalPos = move.getPosition();
        while (currPos != goalPos) {
            if (goalPos < currPos) {
                doMoveLeft();
                currPos--;
            } else {
                doMoveRight();
                currPos++;
            }
        }
        doDrop();
    }

    private void doKeyPress(int keycode) {
        robot.keyPress(keycode);
        robot.delay(keyPressTime);
        robot.keyRelease(keycode);
        robot.delay(keyDelay);
    }

}
