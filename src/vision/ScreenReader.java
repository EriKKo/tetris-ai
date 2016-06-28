package vision;

import model.Tetrimino;
import model.TetrisBoard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenReader {

    private static final boolean DEBUG = false;

    public static CellContent[][] readBoard(Rectangle boardPosition) throws AWTException{
        final double cellWidth = boardPosition.getWidth() / TetrisBoard.WIDTH;
        final double cellHeight = boardPosition.getHeight() / TetrisBoard.VISIBLE_HEIGHT;
        Robot robot = new Robot();
        BufferedImage img = robot.createScreenCapture(boardPosition);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.BLUE);
        Color[][] colors = new Color[TetrisBoard.VISIBLE_HEIGHT][TetrisBoard.WIDTH];
        for (int r = 0; r < TetrisBoard.VISIBLE_HEIGHT; r++) {
            for (int c = 0; c < TetrisBoard.WIDTH; c++) {
                int xPos = (int)(cellWidth*(c+0.5d));
                int yPos = (int)(cellHeight*(r+0.5d));
                colors[r][c] = new Color(img.getRGB(xPos, yPos));
                g.fillOval(xPos, yPos, 3, 3);
            }
        }
        CellContent[][] cells = new CellContent[TetrisBoard.VISIBLE_HEIGHT][TetrisBoard.WIDTH];
        int[][] diff = new int[TetrisBoard.VISIBLE_HEIGHT][TetrisBoard.WIDTH];
        int averageError = 0;
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[j].length; j++) {
                cells[cells.length - 1 - i][j] = CellContent.getContentFromColor(colors[i][j]);
                diff[diff.length - 1 - i][j] = VisionUtil.colorDist(cells[cells.length - 1 - i][j].color, colors[i][j]);
                averageError += diff[diff.length - 1 - i][j];
            }
        }
        averageError /= TetrisBoard.VISIBLE_HEIGHT*TetrisBoard.WIDTH;
        if (averageError > 50) return null;
        if (DEBUG) {
            try {
                ImageIO.write(img, "png", new File("screen.png"));
            } catch(IOException e) {
                System.err.println("Error while saving board image");
            }
        }
        return cells;
    }

    public static Tetrimino[] readNextBox(Rectangle nextBoxPos) throws AWTException {
        int h = (int)(nextBoxPos.getHeight() / 5);
        Robot robot = new Robot();
        BufferedImage img = robot.createScreenCapture(nextBoxPos);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.BLUE);
        Tetrimino[] res = new Tetrimino[5];
        for (int i = 0; i < 5; i++) {
            CellContent content = CellContent.EMPTY;
            for (int r = h*i; r < h*(i+1); r++) {
                for (int c = 0; c < img.getWidth(); c++) {
                    Color color = new Color(img.getRGB(c, r));
                    CellContent newContent = CellContent.getContentFromColor(color);
                    if (newContent.isMino() && VisionUtil.colorDist(color, newContent.color) == 0) {
                        content = newContent;
                    }
                }
            }
            res[i] = Tetrimino.createFromCellContent(content);
            g.drawLine(0, h*i, img.getWidth(), h*i);
        }
        if (DEBUG) {
            try {
                ImageIO.write(img, "png", new File("next.png"));
            } catch(IOException e) {
                System.err.println("Error while saving board image");
            }
        }
        for (int i = 0; i < 5; i++) {
            if (res[i] == null) {
                return null;
            }
        }
        return res;
    }

}
