package main;

import gameplay.GameLogic;
import gui.Gui;
import painter.Painter;
import gui.MouseHandler;
import pieces.Piece;

import javax.swing.*;



public class Main {

    public static Painter panel;
    public static JFrame window;


    public static void main(String[] args) {

        Gui gui = new Gui();

        Piece.initPieces();
        GameLogic.initPosition();

        panel = new Painter();
        panel.setLayout(null);

        window = gui.initWindow();

        window.add(panel);



        MouseHandler mouse = new MouseHandler();
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);

        gui.addFlipButton();
        gui.addResetButton();





    }
}