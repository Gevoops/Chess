package pieces;

import gameplay.GameLogic;

public class Pawn extends Piece implements firstMovable {
    private boolean moved = false;
    public Pawn(int row, int col, Boolean isWhite,String name) {
        super(row, col, isWhite,name);
    }



    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        int direction;
        int curr_row = this.getRow(), curr_col = this.getCol();
        if (this.isWhite()) {
            direction = -1;
        } else {
            direction = 1;
        }
        if (super.isLegalMove(target_row, target_col)) {
            if(curr_col == target_col && checkCollision(target_row,target_col)){
                if(curr_row + direction == target_row){
                    return true;
                } else return !moved && curr_row + direction * 2 == target_row;
            } else return Math.abs(target_col - curr_col) == 1 && target_row == curr_row + direction && GameLogic.piece_position[target_row][target_col] != null && !GameLogic.piece_position[target_row][target_col].isWhite() == this.isWhite();
        }else {
            return false;
        }
    }
    public boolean checkCollision(int target_row, int target_col){
        return GameLogic.piece_position[target_row][target_col] == null;
    }
    public void setMovedTrue(){
        moved = true;
    }
}
