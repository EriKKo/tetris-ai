package controllers;

import model.BoardState;
import model.Tetrimino;
import model.TetrisMove;
import solvers.TetrisSolver;
import vision.CellContent;
import vision.ScreenReader;

import java.awt.*;

public class TetrisFriendsPlayer {

    private Rectangle boardPosition;
    private Rectangle nextBoxPosition;
    private TetrisSolver solver;
    private MoveTyper controller;

    public TetrisFriendsPlayer(TetrisSolver solver, MoveTyper controller, Rectangle boardPosition, Rectangle nextBoxPosition) {
        this.solver = solver;
        this.controller = controller;
        this.boardPosition = boardPosition;
        this.nextBoxPosition = nextBoxPosition;
    }

    public void run() throws AWTException {
        BoardState board = new BoardState();
        String expectedBoard = null;
        while (true) {
            CellContent[][] cells = ScreenReader.readBoard(boardPosition);
            if (cells == null) {
                System.err.println("Unable to find board");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            } else {
                board.applyScreenReading(cells);
                if (nextBoxPosition != null) {
                    Tetrimino[] next = ScreenReader.readNextBox(nextBoxPosition);
                    if (next != null) {
                        board.setNext(next);
                    } else {
                        System.err.println("Next pieces not found");
                    }
                }

                if (board.getCurrent() == null) {
                    System.err.println("Can't find current piece");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {

                    }
                    continue;
                }

                if (expectedBoard != null && !board.getBoard().toString().equals(expectedBoard)) {
                    System.err.println("Error, board not looking as expected");
                    System.err.println("Actual:");
                    System.err.println(board.getBoard());
                    System.err.println("Expected:");
                    System.err.println(expectedBoard);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                    expectedBoard = null;
                    continue;
                }
                System.out.printf("Board:");
                System.out.println("PENALTY: " + board.getBoard().getBoardPenalty(solver.getParams()));
                System.out.println(board);

                TetrisMove move = solver.getMove(board);
                System.out.println(move);
                if (move != null) {
                    boolean holdTurn = board.gethold() == null;
                    controller.doTetrisMove(move);
                    board = board.doMove(move);
                    if (!holdTurn) {
                        expectedBoard = board.getBoard().toString();
                    }
                } else {
                    expectedBoard = null;
                    System.err.println("Unable to find move");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }
    }

}
