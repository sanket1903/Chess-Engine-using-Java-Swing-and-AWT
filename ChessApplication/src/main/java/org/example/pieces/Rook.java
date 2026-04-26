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
}