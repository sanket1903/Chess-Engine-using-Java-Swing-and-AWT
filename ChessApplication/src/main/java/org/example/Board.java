package org.example;

import org.example.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public int tileSize = 85;

    int cols = 8;
    int rows = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();

    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.setBackground(Color.green);

        addPieces(); // IMPORTANT
    }

    private void addPieces() {

        // ♟ Black pieces
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 0, 1, false));
        pieceList.add(new Bishop(this, 0, 2, false));
        pieceList.add(new Queen(this, 0, 3, false));
        pieceList.add(new King(this, 0, 4, false));
        pieceList.add(new Bishop(this, 0, 5, false));
        pieceList.add(new Knight(this, 0, 6, false));
        pieceList.add(new Rook(this, 0, 7, false));

        // ♟ Black pawns
        pieceList.add(new Pawn(this, 1, 0, false));
        pieceList.add(new Pawn(this, 1, 1, false));
        pieceList.add(new Pawn(this, 1, 2, false));
        pieceList.add(new Pawn(this, 1, 3, false));
        pieceList.add(new Pawn(this, 1, 4, false));
        pieceList.add(new Pawn(this, 1, 5, false));
        pieceList.add(new Pawn(this, 1, 6, false));
        pieceList.add(new Pawn(this, 1, 7, false));

        // ♙ White pawns
        pieceList.add(new Pawn(this, 6, 0, true));
        pieceList.add(new Pawn(this, 6, 1, true));
        pieceList.add(new Pawn(this, 6, 2, true));
        pieceList.add(new Pawn(this, 6, 3, true));
        pieceList.add(new Pawn(this, 6, 4, true));
        pieceList.add(new Pawn(this, 6, 5, true));
        pieceList.add(new Pawn(this, 6, 6, true));
        pieceList.add(new Pawn(this, 6, 7, true));

        pieceList.add(new Rook(this, 7, 0, true));
        pieceList.add(new Knight(this, 7, 1, true));
        pieceList.add(new Bishop(this, 7, 2, true));
        pieceList.add(new Queen(this, 7, 3, true));
        pieceList.add(new King(this, 7, 4, true));
        pieceList.add(new Bishop(this, 7, 5, true));
        pieceList.add(new Knight(this, 7, 6, true));
        pieceList.add(new Rook(this, 7, 7, true));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // draw board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0
                        ? new Color(227, 198, 181)
                        : new Color(157, 105, 53));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

        // draw pieces
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}