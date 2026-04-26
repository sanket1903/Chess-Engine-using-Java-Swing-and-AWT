package org.example.pieces;

import org.example.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Piece {

    public int col, row;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;

    protected static BufferedImage sheet;
    protected static int sheetScale;

    protected Image sprite;
    protected Board board;

    // load sprite sheet once
    static {
        try {
            var stream = ClassLoader.getSystemResourceAsStream("pieces.png");

            if (stream == null) {
                throw new RuntimeException("pieces.png not found in resources");
            }

            sheet = ImageIO.read(stream);
            sheetScale = sheet.getWidth() / 6;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load pieces.png", e);
        }
    }

    public Piece(Board board) {
        this.board = board;
    }

    // draw piece
    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}