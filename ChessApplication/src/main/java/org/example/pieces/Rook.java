package org.example.pieces;

import org.example.Board;
import java.awt.image.BufferedImage;

public class Rook extends Piece {

    public Rook(Board board, int row, int col, boolean isWhite) {
        super(board);

        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.name = "Rook";

        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.sprite = sheet.getSubimage(
                4 * sheetScale,
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

        if(col == this.col && row == this.row)
            return false;

        return col == this.col || row == this.row;
    }

    @Override
    public boolean moveCollidesWithPiece(int col, int row) {

        // Left
        if(this.col > col)
            for(int c = this.col - 1; c > col; c--)
                if(board.getPiece(c, this.row) != null)
                    return true;

        // Right
        if(this.col < col)
            for(int c = this.col + 1; c < col; c++)
                if(board.getPiece(c, this.row) != null)
                    return true;

        // Up
        if(this.row > row)
            for(int r = this.row - 1; r > row; r--)
                if(board.getPiece(this.col, r) != null)
                    return true;

        // Down
        if(this.row < row)
            for(int r = this.row + 1; r < row; r++)
                if(board.getPiece(this.col, r) != null)
                    return true;

        return false;
    }
}