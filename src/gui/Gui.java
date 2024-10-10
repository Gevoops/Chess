package gui;

import gameplay.GameLogic;
import main.Main;
import painter.ImageLoader;
import painter.Painter;
import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Gui implements ActionListener {
    public static boolean promotionMenuOpen = false;
    private JButton resetButton;
    private JButton flipButton;

    public JFrame initWindow(){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Chess");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Get the size of the window (frame)
        window.setSize(Painter.getTileSize() * 10,Painter.getTileSize() * 10);
        Dimension windowSize = window.getSize();


        int x = (screenSize.width - windowSize.width) / 2;
        int y = (screenSize.height - windowSize.height) / 2;
        window.setLocation(x,y);
        window.setVisible(true);

        return window;
    }

    public void addFlipButton(){
        flipButton = new JButton("flip board");
        flipButton.setBounds(Main.window.getWidth()/2 + Painter.getOffset() + 40,Painter.getOffset()/5, Painter.getTileSize(),Painter.getTileSize()/2);
        flipButton.addActionListener(this);
        Main.panel.add(flipButton);
    }

    public void addResetButton(){
        resetButton = new JButton("reset board");
        resetButton.setBounds(Main.window.getWidth()/2 + Painter.getOffset() + Painter.getTileSize() +  40,Painter.getOffset()/5, Painter.getTileSize(),Painter.getTileSize()/2);
        resetButton.addActionListener(this);
        Main.panel.add(resetButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == flipButton){
            Painter.flip = 1 - Painter.flip;
        } else if (e.getSource() == resetButton){
            Piece.initPieces();
            GameLogic.initPosition();
            GameLogic.isWhiteTurn = true;
        }
        Main.panel.repaint();
    }
    public static void promotionMenu(int row, int col, Piece piece)
    {
        if(piece.isWhite() && row == 0 || !piece.isWhite() && row ==  7){
            JButton[] button = new JButton[4];
            String[] imagePaths = new String[4];
            Piece[] pieceOptions = new Piece[4];
            promotionMenuOpen = true;
            if(piece.isWhite() && row == 0){
                String[] whiteImagePaths = {"resources/wq.png","resources/wn.png","resources/wr.png","resources/wb.png"};
                imagePaths = whiteImagePaths;
                pieceOptions = new Piece[]{new Queen(row,col,true, ImageLoader.loadImage(whiteImagePaths[0])),
                        new Knight(row,col,true, ImageLoader.loadImage(whiteImagePaths[1])),
                        new Rook(row,col,true, ImageLoader.loadImage(whiteImagePaths[2])),
                        new Bishop(row,col,true, ImageLoader.loadImage(whiteImagePaths[3]))};


            } else if (!piece.isWhite() && row == 7) {
                String[] blackImagePaths = {"resources/bq.png","resources/bn.png","resources/br.png","resources/bb.png"};
                imagePaths = blackImagePaths;
                pieceOptions = new Piece[]{new Queen(row,col,false, ImageLoader.loadImage(blackImagePaths[0])),
                        new Knight(row,col,false, ImageLoader.loadImage(blackImagePaths[1])),
                        new Rook(row,col,false, ImageLoader.loadImage(blackImagePaths[2])),
                        new Bishop(row,col,false, ImageLoader.loadImage(blackImagePaths[3]))};
            }
            for (int i = 0; i < 4; i ++){
                button[i] = new JButton(new ImageIcon(imagePaths[i]));
                button[i].setOpaque(true);
                button[i].setBackground(new Color(137,232,148));
                button[i].setBounds( painter.Painter.flippedPixPos(painter.Painter.boardToPixelPos(col)) , painter.Painter.flippedPixPos(piece.getY() + (piece.isWhite() ? 1 : -1) * i * painter.Painter.getTileSize()), painter.Painter.getTileSize(), Painter.getTileSize());
                int finalI = i;
                Piece[] pieceOptions1= pieceOptions;

                button[i].addActionListener(e -> {


                    Main.panel.remove(button[0]);
                    Main.panel.remove(button[1]);
                    Main.panel.remove(button[2]);
                    Main.panel.remove(button[3]);
                    GameLogic.isWhiteTurn = !GameLogic.isWhiteTurn;
                    promotionMenuOpen = false;
                    GameLogic.promote(piece,pieceOptions1[finalI]);
                    Main.panel.repaint();
                });
                Main.panel.add(button[i]);
            }
        }

    }
}
