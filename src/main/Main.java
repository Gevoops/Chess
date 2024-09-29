package main;

import gameplay.GameLogic;
import painter.Painter;
import gui.MouseHandler;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;


public class Main {

    public static Painter panel;



    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Chess");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Get the size of the window (frame)
        window.setSize(Painter.getTileSize() * 10,Painter.getTileSize() * 10);
        Dimension windowSize = window.getSize();

        // Calculate the top-left corner to center the window's center


        int x = (screenSize.width - windowSize.width) / 2;
        int y = (screenSize.height - windowSize.height) / 2;
        window.setLocation(x,y);
        GameLogic.pieces = Piece.init_pieces();
        GameLogic.initPosition();


        panel = new Painter();
        window.add(panel);
        panel.setLayout(null);
        MouseHandler mouse = new MouseHandler();
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);

        JButton button1 = new JButton("flip board");
        button1.setBounds(window.getWidth()/2 + Painter.getOffset() + 40,Painter.getOffset()/5, Painter.getTileSize(),Painter.getTileSize()/2);
        button1.addActionListener(panel);
        panel.add(button1);

        window.setVisible(true);



    }
}