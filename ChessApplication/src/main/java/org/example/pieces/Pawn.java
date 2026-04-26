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
}