package gui;

import gameplay.GameLogic;
import main.Main;
import painter.ImageLoader;
import painter.Painter;
import pieces.*;

import javax.swing.*;
import java.awt.*;


public class Gui {
    public static boolean promotionMenuOpen = false;
    public static void promotionMenu(int row, int col, Piece piece){
        if(piece.isWhite() && row == 0 || !piece.isWhite() && row ==  7){
            JButton[] button = new JButton[4];
            String[] imagePaths = new String[4];
            Piece[] pieceOptions = new Piece[4];
            promotionMenuOpen = true;
            if(piece.isWhite() && row == 0){
                String[] whiteImagePaths = {"resources/white_queen.png","resources/white_knight.png","resources/white_rook.png","resources/white_bishop.png"};
                imagePaths = whiteImagePaths;
                pieceOptions = new Piece[]{new Queen(row,col,true, ImageLoader.loadImage(whiteImagePaths[0])),
                        new Knight(row,col,true, ImageLoader.loadImage(whiteImagePaths[1])),
                        new Rook(row,col,true, ImageLoader.loadImage(whiteImagePaths[2])),
                        new Bishop(row,col,true, ImageLoader.loadImage(whiteImagePaths[3]))};


            } else if (!piece.isWhite() && row == 7) {
                String[] blackImagePaths = {"resources/black_queen.png","resources/black_knight.png","resources/black_rook.png","resources/black_bishop.png"};
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

                    GameLogic.promote(piece,pieceOptions1[finalI]);
                    Main.panel.remove(button[0]);
                    Main.panel.remove(button[1]);
                    Main.panel.remove(button[2]);
                    Main.panel.remove(button[3]);
                    promotionMenuOpen = false;
                    Main.panel.repaint();
                });
                Main.panel.add(button[i]);
            }
        }

    }
}
