package mainPackage;

import gameplay.GameLogic;
import painter.Painter;
import gameplay.MouseHandler;
import pieces.Piece;

import javax.swing.*;


public class Main {

    public static Painter panel;



    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Chess");
        window.setLocationRelativeTo(null);

        window.setSize(1000,1000);

        GameLogic.initPosition(Piece.init_pieces());

        panel = new Painter();
        window.add(panel);

        MouseHandler mouse = new MouseHandler();
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);
        window.setVisible(true);



    }
}