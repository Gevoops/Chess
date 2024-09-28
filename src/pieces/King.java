package pieces;

import gameplay.GameLogic;

import java.awt.image.BufferedImage;

public class King extends Piece implements firstMovable {
    private boolean moved = false;
    public static King whiteKing;
    public static King blackKing;

    public King(int row, int col, Boolean isWhite,  BufferedImage image) {

        super(row, col, isWhite,  image);
        if(isWhite){
            whiteKing = this;
        }else{
            blackKing = this;
        }
    }




    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        if(super.isLegalMove(target_row,target_col)){
            if(Math.abs(target_col -this.getCol()) <= 1 && Math.abs(target_row - this.getRow())<= 1) { // normal king step
                return GameLogic.piecePosition[target_row][target_col] == null || !GameLogic.piecePosition[target_row][target_col].isWhite() == this.isWhite();
            }else if(target_row == this.getRow() && Math.abs(target_col - this.getCol()) >= 2) { // trying to castle?
                return isValidKingSideCastle(target_row,target_col) || isValidQueenSideCastle(target_row,target_col);
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
    public boolean isValidKingSideCastle(int target_row, int target_col){
        return !moved && target_row == this.getRow() && target_col >= this.getCol() + 2
                && GameLogic.piecePosition[target_row][7] != null
                && GameLogic.piecePosition[target_row][7] instanceof Rook
                && !((Rook) GameLogic.piecePosition[target_row][7]).getMoved()
                && GameLogic.piecePosition[target_row][6] == null
                && GameLogic.piecePosition[target_row][5] == null
                && (this.isWhite() ? !GameLogic.blackAttacks[target_row][6] : !GameLogic.whiteAttacks[target_row][6])
                && (this.isWhite() ? !GameLogic.blackAttacks[target_row][5] : !GameLogic.whiteAttacks[target_row][5]);
    }
    public boolean isValidQueenSideCastle(int target_row, int target_col){
        return !moved && target_row == this.getRow() && target_col <= this.getCol() -2
                && GameLogic.piecePosition[target_row][0] != null
                && GameLogic.piecePosition[target_row][0] instanceof Rook
                && !((Rook) GameLogic.piecePosition[target_row][0]).getMoved()
                && GameLogic.piecePosition[target_row][1] == null
                && GameLogic.piecePosition[target_row][2] == null
                && GameLogic.piecePosition[target_row][3] == null
                && (this.isWhite() ? !GameLogic.blackAttacks[target_row][2] : !GameLogic.whiteAttacks[target_row][2])
                && (this.isWhite() ? !GameLogic.blackAttacks[target_row][3] : !GameLogic.whiteAttacks[target_row][3]);
    }
    public void setMovedTrue(){
        moved = true;
    }

}
