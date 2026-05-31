package org.example;

import org.example.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public int tileSize = 85;

    int cols = 8;
    int rows = 8;

    public boolean whiteTurn = true;
    ArrayList<Piece> pieceList = new ArrayList<>();

    ArrayList<Piece> whiteCaptured = new ArrayList<>();
    ArrayList<Piece> blackCaptured = new ArrayList<>();

    public Piece selectedPiece;

    Input input = new Input(this);

    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize+200, rows * tileSize));
        this.setBackground(Color.green);

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPieces();
    }

    public boolean isKingInCheck(boolean whiteKing) {

        Piece king = null;

        // Find king
        for (Piece piece : pieceList) {

            if (piece instanceof King &&
                    piece.isWhite == whiteKing) {

                king = piece;
                break;
            }
        }

        if (king == null) {
            return false;
        }

        // Check if any enemy piece attacks king
        for (Piece piece : pieceList) {

            if (piece.isWhite != whiteKing) {

                if (piece.isValidMovement(
                        king.col,
                        king.row
                ) &&
                        !piece.moveCollidesWithPiece(
                                king.col,
                                king.row
                        )) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isValidMove(Move move) {

        if (move == null || move.piece == null) {
            return false;
        }

        if (sameTeam(move.piece, move.capture)) {
            return false;
        }

        if(!move.piece.isValidMovement(move.newCol, move.newRow)){
            return false;
        }

        if(move.piece.moveCollidesWithPiece(move.newCol, move.newRow)){
            return false;
        }

        return true;
    }

    public boolean sameTeam(Piece p1, Piece p2) {

        if (p1 == null || p2 == null) {
            return false;
        }

        return p1.isWhite == p2.isWhite;
    }

    public boolean makeMove(Move move) {

        if (move == null || move.piece == null) {
            return false;
        }

        // Castling
        if (move.piece instanceof King &&
                Math.abs(move.newCol - move.oldCol) == 2) {

            // Kingside castling
            if (move.newCol > move.oldCol) {

                Piece rook = getPiece(7, move.oldRow);

                if (rook != null) {

                    rook.col = 5;
                    rook.row = move.oldRow;

                    rook.xPos = 5 * tileSize;
                    rook.yPos = move.oldRow * tileSize;

                    rook.isFirstMove = false;
                }
            }

            // Queenside castling
            else {

                Piece rook = getPiece(0, move.oldRow);

                if (rook != null) {

                    rook.col = 3;
                    rook.row = move.oldRow;

                    rook.xPos = 3 * tileSize;
                    rook.yPos = move.oldRow * tileSize;

                    rook.isFirstMove = false;
                }
            }
        }

        // Capture opponent piece
        capture(move);

        // Move selected piece
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;

        move.piece.xPos = move.newCol * tileSize;
        move.piece.yPos = move.newRow * tileSize;

        // Mark piece as moved
        move.piece.isFirstMove = false;

        // Change turn
        whiteTurn = !whiteTurn;

        repaint();

        return true;
    }

    public void capture(Move move) {

        if (move.capture != null) {

            pieceList.remove(move.capture);

            if (move.capture.isWhite) {
                whiteCaptured.add(move.capture);
            } else {
                blackCaptured.add(move.capture);
            }
        }
    }

    public Piece getPiece(int col, int row) {

        for (Piece piece : pieceList) {

            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }

        return null;
    }

    private void addPieces() {

        // Black Pieces
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 0, 1, false));
        pieceList.add(new Bishop(this, 0, 2, false));
        pieceList.add(new Queen(this, 0, 3, false));
        pieceList.add(new King(this, 0, 4, false));
        pieceList.add(new Bishop(this, 0, 5, false));
        pieceList.add(new Knight(this, 0, 6, false));
        pieceList.add(new Rook(this, 0, 7, false));

        // Black Pawns
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, 1, i, false));
        }

        // White Pawns
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, 6, i, true));
        }

        // White Pieces
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
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw Board
        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                if ((row + col) % 2 == 0) {
                    g2d.setColor(new Color(227, 198, 181));
                } else {
                    g2d.setColor(new Color(157, 105, 53));
                }

                g2d.fillRect(
                        col * tileSize,
                        row * tileSize,
                        tileSize,
                        tileSize
                );
            }
        }

        // Highlight king in check
        for (Piece piece : pieceList) {

            if (piece instanceof King &&
                    isKingInCheck(piece.isWhite)) {

                g2d.setColor(
                        new Color(180, 0, 0, 180)
                );

                g2d.fillRect(
                        piece.col * tileSize,
                        piece.row * tileSize,
                        tileSize,
                        tileSize
                );
            }
        }

        // Highlight Valid Moves
        if (selectedPiece != null) {

            for (int row = 0; row < rows; row++) {

                for (int col = 0; col < cols; col++) {

                    try {

                        Move move = new Move(
                                this,
                                selectedPiece,
                                col,
                                row
                        );

                        if (isValidMove(move)) {
                            g2d.setColor(new Color(68, 180, 57, 190));
                            g2d.fillRect(
                                    col * tileSize,
                                    row * tileSize,
                                    tileSize,
                                    tileSize
                            );
                        }

                    } catch (Exception ignored) {
                    }
                }
            }
        }

        // Draw Pieces
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }

        // Side panel
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(cols * tileSize, 0, 200, rows * tileSize);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Captured Black", cols * tileSize + 20, 20);
        g2d.drawString("Captured White", cols * tileSize + 20, 250);

// Captured black pieces
        int x = cols * tileSize + 20;
        int y = 40;

        for (Piece piece : blackCaptured) {

            g2d.drawImage(
                    piece.sprite,
                    x,
                    y,
                    tileSize / 2,
                    tileSize / 2,
                    null
            );

            y += tileSize / 2 + 5;
        }

// Captured white pieces
        x = cols * tileSize + 20;
        y = 270;

        for (Piece piece : whiteCaptured) {

            g2d.drawImage(
                    piece.sprite,
                    x,
                    y,
                    tileSize / 2,
                    tileSize / 2,
                    null
            );

            y += tileSize / 2 + 5;
        }
    }
}