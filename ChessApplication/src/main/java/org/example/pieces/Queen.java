package org.example.pieces;

import org.example.Board;
import java.awt.image.BufferedImage;

public class Queen extends Piece {

    public Queen(Board board, int row, int col, boolean isWhite) {
        super(board);

        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.name = "Queen";

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = sheet.getSubimage(
                1 * sheetScale,
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

        int colDiff = Math.abs(col - this.col);
        int rowDiff = Math.abs(row - this.row);

        return col == this.col ||
                row == this.row ||
                colDiff == rowDiff;
    }

    //Queen combines Rook + Bishop
    @Override
    public boolean moveCollidesWithPiece(int col, int row) {

        // Rook-like movement
        if (this.col == col || this.row == row) {

            if (this.col > col)
                for (int c = this.col - 1; c > col; c--)
                    if (board.getPiece(c, this.row) != null)
                        return true;

            if (this.col < col)
                for (int c = this.col + 1; c < col; c++)
                    if (board.getPiece(c, this.row) != null)
                        return true;

            if (this.row > row)
                for (int r = this.row - 1; r > row; r--)
                    if (board.getPiece(this.col, r) != null)
                        return true;

            if (this.row < row)
                for (int r = this.row + 1; r < row; r++)
                    if (board.getPiece(this.col, r) != null)
                        return true;
        }

        // Bishop-like movement
        int colDiff = Math.abs(col - this.col);
        int rowDiff = Math.abs(row - this.row);

        if (colDiff == rowDiff) {

            int colStep = (col > this.col) ? 1 : -1;
            int rowStep = (row > this.row) ? 1 : -1;

            int c = this.col + colStep;
            int r = this.row + rowStep;

            while (c != col && r != row) {
                if (board.getPiece(c, r) != null)
                    return true;

                c += colStep;
                r += rowStep;
            }
        }

        return false;
    }
}