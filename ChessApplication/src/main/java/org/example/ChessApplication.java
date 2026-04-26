package org.example;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ChessApplication {
    static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000)); // to set minimum size of box
        frame.setLocationRelativeTo(null);

        Board board = new Board();
        frame.add(board);

        frame.setVisible(true);

    }
}
