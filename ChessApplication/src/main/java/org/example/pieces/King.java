package org.example.pieces;

import org.example.Board;
import java.awt.image.BufferedImage;

public class King extends Piece {

    public King(Board board, int row, int col, boolean isWhite) {
        super(board);

        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.name = "King";

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = sheet.getSubimage(
                0 * sheetScale,
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

        // Normal king move
        if (colDiff <= 1 && rowDiff <= 1) {
            return true;
        }

        // Castling
        if (isFirstMove &&
                row == this.row &&
                colDiff == 2) {
            return true;
        }

        return false;
    }

    @Override
    public boolean moveCollidesWithPiece(int col, int row) {

        // Kingside castling
        if (isFirstMove && row == this.row && col == this.col + 2) {

            Piece rook = board.getPiece(7, this.row);

            if (!(rook instanceof Rook) || !rook.isFirstMove) {
                return true;
            }

            return board.getPiece(5, this.row) != null ||
                    board.getPiece(6, this.row) != null;
        }

        // Queenside castling
        if (isFirstMove && row == this.row && col == this.col - 2) {

            Piece rook = board.getPiece(0, this.row);

            if (!(rook instanceof Rook) || !rook.isFirstMove) {
                return true;
            }

            return board.getPiece(1, this.row) != null ||
                    board.getPiece(2, this.row) != null ||
                    board.getPiece(3, this.row) != null;
        }

        return false;
    }
}