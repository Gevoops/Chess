package pieces;

import gameplay.GameLogic;
import main.Main;
import painter.ImageLoader;
import painter.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Pawn extends Piece implements firstMovable {
    private boolean moved = false;
    public Pawn(int row, int col, Boolean isWhite, BufferedImage image) {
        super(row, col, isWhite, image);
    }



    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        int direction = isWhite() ? -1 : 1;
        int curr_row = row, curr_col = col;

        if (super.isLegalMove(target_row, target_col)) {
            if(curr_col == target_col && checkCollision(target_row,target_col)){
                if(curr_row + direction == target_row){
                    return true;
                } else return !moved && curr_row + direction * 2 == target_row;
            } else if(GameLogic.lastDoubleStepMovedPawn != null && GameLogic.lastDoubleStepMovedPawn.getRow() == row && Math.abs(GameLogic.lastDoubleStepMovedPawn.col - col) == 1 && target_row == row + direction && target_col == GameLogic.lastDoubleStepMovedPawn.col &&GameLogic.piecePosition[target_row][target_col] == null){
                GameLogic.eat(GameLogic.piecePosition[target_row - direction][target_col]);
                return true;
            }else return Math.abs(target_col - curr_col) == 1 && target_row == curr_row + direction && GameLogic.piecePosition[target_row][target_col] != null && GameLogic.piecePosition[target_row][target_col].isWhite() != this.isWhite();
        }else {
            return false;
        }
    }
    public boolean checkCollision(int target_row, int target_col){
        return GameLogic.piecePosition[target_row][target_col] == null;
    }

    public void promotion(int row,int col,Piece piece){
        if(isWhite() && row == 0 || !isWhite() && row ==  7){
            JButton[] button = new JButton[4];
            String[] imagePaths = new String[4];
            Piece[] pieceOptions = new Piece[4];
            GameLogic.promotionMenuOpen = true;
            if(isWhite() && row == 0){
                String[] whiteImagePaths = {"resources/white_queen.png","resources/white_knight.png","resources/white_rook.png","resources/white_bishop.png"};
                imagePaths = whiteImagePaths;
                Piece[] whitePieceOptions = {new Queen(row,col,true, ImageLoader.loadImage(whiteImagePaths[0])),
                                             new Knight(row,col,true, ImageLoader.loadImage(whiteImagePaths[1])),
                                             new Rook(row,col,true, ImageLoader.loadImage(whiteImagePaths[2])),
                                             new Bishop(row,col,true, ImageLoader.loadImage(whiteImagePaths[3]))};
                pieceOptions = whitePieceOptions;


            } else if (!isWhite() && row == 7) {
                String[] blackImagePaths = {"resources/black_queen.png","resources/black_knight.png","resources/black_rook.png","resources/black_bishop.png"};
                imagePaths = blackImagePaths;
                Piece[] whitePieceOptions = {new Queen(row,col,false, ImageLoader.loadImage(blackImagePaths[0])),
                        new Knight(row,col,false, ImageLoader.loadImage(blackImagePaths[1])),
                        new Rook(row,col,false, ImageLoader.loadImage(blackImagePaths[2])),
                        new Bishop(row,col,false, ImageLoader.loadImage(blackImagePaths[3]))};
                pieceOptions = whitePieceOptions;
            }
            for (int i = 0; i < 4; i ++){
                button[i] = new JButton(new ImageIcon(imagePaths[i]));
                button[i].setOpaque(true);
                button[i].setBackground(new Color(137,232,148));
                button[i].setBounds( Painter.flippedPixPos(Painter.boardToPixelPos(col)) ,Painter.flippedPixPos(this.getY() + (isWhite() ? 1 : -1) * i * Painter.getTileSize()), Painter.getTileSize(),Painter.getTileSize());
                int finalI = i;
                Piece[] pieceOptions1= pieceOptions;

                button[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameLogic.pieces.remove(piece);
                        GameLogic.pieces.add(pieceOptions1[finalI]);
                        GameLogic.piecePosition[row][col] = pieceOptions1[finalI];
                        GameLogic.whiteAttacks();
                        GameLogic.blackAttacks();
                        GameLogic.checkOrMateOrStale();
                        Main.panel.remove(button[0]);
                        Main.panel.remove(button[1]);
                        Main.panel.remove(button[2]);
                        Main.panel.remove(button[3]);
                        GameLogic.promotionMenuOpen = false;
                        Main.panel.repaint();
                    }
                });
                Main.panel.add(button[i]);
            }
        }

    }
    public void setMovedTrue(){
        moved = true;
    }
}
