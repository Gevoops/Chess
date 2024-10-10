package pieces;
import gui.Gui;
import painter.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import gameplay.GameLogic;
import main.Main;


public abstract class Piece {
    protected int row;
    protected int col;
    protected final boolean isWhite;

    private int x;
    private int y;
    private Image image;



    public Piece(int row, int col, Boolean isWhite, BufferedImage image){
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        x = col * Painter.getTileSize()+ Painter.getOffset();
        y = row* Painter.getTileSize()+ Painter.getOffset();
        this.image = image;
    }

    public Piece(Piece piece) {
        row = piece.row;
        col = piece.col;
        isWhite = piece.isWhite;
        x = piece.x;
        y = piece.y;
        image = piece.image;
    }
    public void move(int target_row , int target_col) {
        if((this.isWhite == GameLogic.isWhiteTurn) && isLegalMove(target_row,target_col) && GameLogic.kingWillBeSafe(target_row, target_col, this) && !Gui.promotionMenuOpen) {
            if(this instanceof  King){
                if(target_col - col >= 2){
                    ((Rook)GameLogic.piecePosition[target_row][7]).setMovedTrue();
                    GameLogic.piecePosition[target_row][7].changePosition(target_row,5);
                    target_col = 6;
                } else if (target_col - col <= -2){
                    ((Rook)GameLogic.piecePosition[target_row][0]).setMovedTrue();
                    GameLogic.piecePosition[target_row][0].changePosition(target_row,3);
                    target_col = 2;
                }
            }
            GameLogic.lastDoubleStepMovedPawn = null;
            if (this instanceof Pawn){
                if(Math.abs(target_row - row) == 2){
                    GameLogic.lastDoubleStepMovedPawn = this;
                }
                Gui.promotionMenu(target_row,target_col,this);
            }
            if(GameLogic.piecePosition[target_row][target_col] != null){
                GameLogic.eat(GameLogic.piecePosition[target_row][target_col]);
            }
            this.changePosition(target_row,target_col);
            if(this instanceof firstMovable){
                ((firstMovable) this).setMovedTrue();
            }
            if(!Gui.promotionMenuOpen){
                GameLogic.isWhiteTurn = !GameLogic.isWhiteTurn;
                GameLogic.updateAttacksAndChecks();
                GameLogic.checkOrMateOrStale();
            }
        } else{
            this.setX(Painter.boardToPixelPos(this.col));
            this.setY(Painter.boardToPixelPos(this.row));
        }
        Main.panel.repaint();
    }
    public void changePosition(int target_row, int target_col){
        GameLogic.piecePosition[this.getRow()][this.getCol()] = null;
        GameLogic.piecePosition[target_row][target_col] = this;
        this.setX(Painter.boardToPixelPos(target_col));
        this.setY(Painter.boardToPixelPos(target_row));
        this.setCol(target_col);
        this.setRow(target_row);
    }
    public boolean isLegalMove(int target_row , int target_col) {
        return  (target_col < 8 && target_col >= 0 && target_row >= 0 && target_row < 8 && (target_col != this.getCol() || target_row != this.getRow()));
    }

    public static void initPieces(){
        ArrayList<Piece> pieces = new ArrayList<>();
        //white pieces
        pieces.add(new Pawn(6,0, true,ImageLoader.loadImage("resources/wp.png")));
        pieces.add(new Pawn(6,1, true,ImageLoader.loadImage("resources/wp.png")));
        pieces.add(new Pawn(6,2, true,ImageLoader.loadImage("resources/wp.png")));
        pieces.add(new Pawn(6,3, true,ImageLoader.loadImage("resources/wp.png")));
        pieces.add(new Pawn(6,4, true,ImageLoader.loadImage("resources/wp.png")));
        pieces.add(new Pawn(6,5, true,ImageLoader.loadImage("resources/wp.png")));
        pieces.add(new Pawn(6,6, true,ImageLoader.loadImage("resources/wp.png")));

        pieces.add(new Pawn(6,7, true,ImageLoader.loadImage("resources/wp.png")));
        pieces.add(new Knight(7,6,true,ImageLoader.loadImage("resources/wn.png")));
        pieces.add(new Knight(7,1,true,ImageLoader.loadImage("resources/wn.png")));

        pieces.add(new Bishop(7,5,true,ImageLoader.loadImage("resources/wb.png")));
        pieces.add(new Bishop(7,2,true,ImageLoader.loadImage("resources/wb.png")));

        pieces.add(new Rook(7,0,true,ImageLoader.loadImage("resources/wr.png")));
        pieces.add(new Rook(7,7,true,ImageLoader.loadImage("resources/wr.png")));

        pieces.add(new Queen(7,3,true,ImageLoader.loadImage("resources/wq.png")));

        pieces.add(new King(7,4,true,ImageLoader.loadImage("resources/wk.png")));




        //black pieces
        pieces.add(new Pawn(1,0, false,ImageLoader.loadImage("resources/bp.png")));
        pieces.add(new Pawn(1,1, false,ImageLoader.loadImage("resources/bp.png")));
        pieces.add(new Pawn(1,2, false,ImageLoader.loadImage("resources/bp.png")));
        pieces.add(new Pawn(1,3, false,ImageLoader.loadImage("resources/bp.png")));
        pieces.add(new Pawn(1,4, false,ImageLoader.loadImage("resources/bp.png")));
        pieces.add(new Pawn(1,5, false,ImageLoader.loadImage("resources/bp.png")));
        pieces.add(new Pawn(1,6, false,ImageLoader.loadImage("resources/bp.png")));
        pieces.add(new Pawn(1,7, false,ImageLoader.loadImage("resources/bp.png")));

        pieces.add(new Knight(0,6,false,ImageLoader.loadImage("resources/bn.png")));
        pieces.add(new Knight(0,1,false,ImageLoader.loadImage("resources/bn.png")));

        pieces.add(new Bishop(0,5,false,ImageLoader.loadImage("resources/bb.png")));
        pieces.add(new Bishop(0,2,false,ImageLoader.loadImage("resources/bb.png")));

        pieces.add(new Rook(0,0,false,ImageLoader.loadImage("resources/br.png")));
        pieces.add(new Rook(0,7,false,ImageLoader.loadImage("resources/br.png")));

        pieces.add(new Queen(0,3,false,ImageLoader.loadImage("resources/bq.png")));

        pieces.add(new King(0,4,false,ImageLoader.loadImage("resources/bk.png")));



        GameLogic.pieces = pieces;
        Painter.scaleImages();
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
