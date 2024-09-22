package pieces;
import painter.*;
import java.util.ArrayList;
import gameplay.GameLogic;
import mainPackage.Main;

public abstract class Piece {
    protected int row;
    protected int col;
    private final boolean isWhite;
    private String name;
    private int x;
    private int y;

    public Piece(int row, int col, Boolean isWhite,String name){
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.name = name;
        x = col * Painter.getTileSize()+ Painter.getOffset();
        y = row* Painter.getTileSize()+ Painter.getOffset();
    }
    public void move(int target_row , int target_col) {
        if((this.isWhite == GameLogic.isWhiteTurn) &&isLegalMove(target_row,target_col) && !GameLogic.willKingBeInCheck(target_row,target_col,this)) {
            if(this instanceof  King){
                if(((King) this).isValidKingSideCastle(target_row,target_col)){
                    ((Rook)GameLogic.piece_position[target_row][target_col + 1]).setMovedTrue();
                    GameLogic.piece_position[target_row][target_col + 1].setPosition(target_row,target_col - 1);
                } else if (((King) this).isValidQueenSideCastle(target_row,target_col)){
                    ((Rook)GameLogic.piece_position[target_row][target_col - 2]).setMovedTrue();
                    GameLogic.piece_position[target_row][target_col  -2].setPosition(target_row,target_col + 1);
                }
            }
            this.setPosition(target_row,target_col);
            if(this instanceof firstMovable){
                ((firstMovable) this).setMovedTrue();
            }
            GameLogic.isWhiteTurn = !GameLogic.isWhiteTurn;
            GameLogic.whiteAttacks();
            GameLogic.blackAttacks();


        } else{
            this.setX(Painter.BoardToPixelPos(this.col));
            this.setY(Painter.BoardToPixelPos(this.row));
        }
        Main.panel.repaint();
    }
    public void setPosition(int target_row, int target_col){
        GameLogic.piece_position[this.getRow()][this.getCol()] = null;
        GameLogic.piece_position[target_row][target_col] = this;
        this.setX(Painter.BoardToPixelPos(target_col));
        this.setY(Painter.BoardToPixelPos(target_row));
        this.setCol(target_col);
        this.setRow(target_row);
    }
    public boolean isLegalMove(int target_row , int target_col) {
        return  (target_col < 8 && target_col >= 0 && target_row >= 0 && target_row < 8 && (target_col != this.getCol() || target_row != this.getRow()));
    }

    public static ArrayList<Piece> init_pieces(){
        ArrayList<Piece> pieces = new ArrayList<>();
        //white pieces
        pieces.add(new Pawn(6,0, true,"wp1"));
        pieces.add(new Pawn(6,1, true,"wp2"));
        pieces.add(new Pawn(6,2, true,"wp3"));
        pieces.add(new Pawn(6,3, true,"wp4"));
        pieces.add(new Pawn(6,4, true,"wp5"));
        pieces.add(new Pawn(6,5, true,"wp6"));
        pieces.add(new Pawn(6,6, true,"wp7"));
        pieces.add(new Pawn(6,7, true,"wp8"));

        pieces.add(new Knight(7,6,true,"wn1"));
        pieces.add(new Knight(7,1,true,"wn2"));

        pieces.add(new Bishop(7,5,true,"wb1"));
        pieces.add(new Bishop(7,2,true,"wb2"));

        pieces.add(new Rook(7,0,true,"wr1"));
        pieces.add(new Rook(7,7,true,"wr2"));

        pieces.add(new Queen(7,3,true,"wq"));

        pieces.add(new King(7,4,true,"wk"));




        //black pieces
        pieces.add(new Pawn(1,0, false,"bp1"));
        pieces.add(new Pawn(1,1, false,"bp2"));
        pieces.add(new Pawn(1,2, false,"bp3"));
        pieces.add(new Pawn(1,3, false,"bp4"));
        pieces.add(new Pawn(1,4, false,"bp5"));
        pieces.add(new Pawn(1,5, false,"bp6"));
        pieces.add(new Pawn(1,6, false,"bp7"));
        pieces.add(new Pawn(1,7, false,"bp8"));

        pieces.add(new Knight(0,6,false,"bn1"));
        pieces.add(new Knight(0,1,false,"bn2"));

        pieces.add(new Bishop(0,5,false,"bb1"));
        pieces.add(new Bishop(0,2,false,"bb2"));

        pieces.add(new Rook(0,0,false,"br1"));
        pieces.add(new Rook(0,7,false,"br2"));

        pieces.add(new Queen(0,3,false,"bq"));

        pieces.add(new King(0,4,false,"bk"));


        return pieces;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


}
