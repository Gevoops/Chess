package pieces;
import painter.*;
import java.util.ArrayList;
import gameplay.GameLogic;
import mainPackage.Main;

public abstract class Piece {
    private int row;
    private int col;
    private final String color;
    private String name;
    private int x;
    private int y;

    public Piece(int row, int col, String color,String name){
        this.row = row;
        this.col = col;
        this.color = color;
        this.name = name;
        x = col * Painter.getTileSize()+ Painter.getOffset();
        y = row* Painter.getTileSize()+ Painter.getOffset();
    }
    public void move(int target_row , int target_col) {
        if(target_col < 8 && target_col >= 0 && target_row >= 0 && target_row < 8 &&(target_col != this.getCol() || target_row != this.getRow()) &&isLegalMove(target_row,target_col)) {
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
        if(this instanceof firstMovable){
            ((firstMovable) this).setMovedTrue();
        }
    }
    public abstract boolean isLegalMove(int target_row , int target_col);

    public static ArrayList<Piece> init_pieces(){
        ArrayList<Piece> pieces = new ArrayList<>();
        //white pieces
        pieces.add(new Pawn(6,0, "white","wp1"));
        pieces.add(new Pawn(6,1, "white","wp2"));
        pieces.add(new Pawn(6,2, "white","wp3"));
        pieces.add(new Pawn(6,3, "white","wp4"));
        pieces.add(new Pawn(6,4, "white","wp5"));
        pieces.add(new Pawn(6,5, "white","wp6"));
        pieces.add(new Pawn(6,6, "white","wp7"));
        pieces.add(new Pawn(6,7, "white","wp8"));

        pieces.add(new Knight(7,6,"white","wn1"));
        pieces.add(new Knight(7,1,"white","wn2"));

        pieces.add(new Bishop(7,5,"white","wb1"));
        pieces.add(new Bishop(7,2,"white","wb2"));

        pieces.add(new Rook(7,0,"white","wr1"));
        pieces.add(new Rook(7,7,"white","wr2"));

        pieces.add(new Queen(7,3,"white","wq"));

        pieces.add(new King(7,4,"white","wk"));




        //black pieces
        pieces.add(new Pawn(1,0, "black","bp1"));
        pieces.add(new Pawn(1,1, "black","bp2"));
        pieces.add(new Pawn(1,2, "black","bp3"));
        pieces.add(new Pawn(1,3, "black","bp4"));
        pieces.add(new Pawn(1,4, "black","bp5"));
        pieces.add(new Pawn(1,5, "black","bp6"));
        pieces.add(new Pawn(1,6, "black","bp7"));
        pieces.add(new Pawn(1,7, "black","bp8"));

        pieces.add(new Knight(0,6,"black","bn1"));
        pieces.add(new Knight(0,1,"black","bn2"));

        pieces.add(new Bishop(0,5,"black","bb1"));
        pieces.add(new Bishop(0,2,"black","bb2"));

        pieces.add(new Rook(0,0,"black","br1"));
        pieces.add(new Rook(0,7,"black","br2"));

        pieces.add(new Queen(0,3,"black","bq"));

        pieces.add(new King(0,4,"black","bk"));


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

    public String getColor() {
        return color;
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
