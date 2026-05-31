package org.example.pieces;

import org.example.Board;
import java.awt.image.BufferedImage;

public class Pawn extends Piece {

    public Pawn(Board board, int row, int col, boolean isWhite) {
        super(board);

        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.name = "Pawn";

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = sheet.getSubimage(
                5 * sheetScale,
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

        int direction = isWhite ? -1 : 1;

        Piece target = board.getPiece(col, row);

        // Capture diagonally
        if (target != null &&
                target.isWhite != this.isWhite &&
                Math.abs(col - this.col) == 1 &&
                row == this.row + direction) {

            return true;
        }

        // One step forward
        if (target == null &&
                col == this.col &&
                row == this.row + direction) {

            return true;
        }

        // Two steps from starting position
        if (target == null &&
                col == this.col) {

            if (isWhite && this.row == 6 && row == 4) {
                return true;
            }

            if (!isWhite && this.row == 1 && row == 3) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean moveCollidesWithPiece(int col, int row) {

        int direction = isWhite ? -1 : 1;

        // Diagonal capture
        if (Math.abs(col - this.col) == 1 &&
                row == this.row + direction) {

            Piece target = board.getPiece(col, row);

            return target == null;
        }

        // Forward move
        if (col == this.col) {

            if (board.getPiece(col, row) != null) {
                return true;
            }

            if (Math.abs(row - this.row) == 2) {

                int middleRow = this.row + direction;

                return board.getPiece(col, middleRow) != null;
            }
        }

        return false;
    }
}