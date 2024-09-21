package pieces;

import gameplay.GameLogic;

public class Knight extends Piece{
    public Knight(int row, int col, Boolean isWhite,String name) {
        super(row, col, isWhite,name);
    }


    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        if(super.isLegalMove(target_row,target_col) &&  Math.abs(target_row - this.getRow()) + Math.abs(target_col - this.getCol()) == 3 && target_row != this.getRow() && target_col != this.getCol()){
            return GameLogic.piece_position[target_row][target_col] == null || !GameLogic.piece_position[target_row][target_col].isWhite() == isWhite();
        }else {
            return false;
        }
    }
}
