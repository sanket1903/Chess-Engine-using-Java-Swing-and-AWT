package org.example.pieces;

import org.example.Board;
import java.awt.image.BufferedImage;

public class Bishop extends Piece {

    public Bishop(Board board, int row, int col, boolean isWhite) {
        super(board);

        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.name = "Bishop";

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = sheet.getSubimage(
                2 * sheetScale,
                isWhite ? 0 : sheetScale,
                sheetScale,
                sheetScale
        ).getScaledInstance(
                board.tileSize,
                board.tileSize,
                BufferedImage.SCALE_SMOOTH
        );
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return Math.abs(col - this.col) == Math.abs(row - this.row);
    }

    @Override
    public boolean moveCollidesWithPiece(int col, int row) {

        int colStep = (col > this.col) ? 1 : -1;
        int rowStep = (row > this.row) ? 1 : -1;

        int c = this.col + colStep;
        int r = this.row + rowStep;

        while (c != col && r != row) {
            if (board.getPiece(c, r) != null) {
                return true;
            }

            c += colStep;
            r += rowStep;
        }

        return false;
    }
}