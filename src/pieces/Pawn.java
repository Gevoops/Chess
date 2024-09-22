package pieces;

import gameplay.GameLogic;

public class Pawn extends Piece implements firstMovable {
    private boolean moved = false;
    public Pawn(int row, int col, Boolean isWhite,String name) {
        super(row, col, isWhite,name);
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
            } else if(GameLogic.lastDoubleStepMovedPawn != null && GameLogic.lastDoubleStepMovedPawn.getRow() == row && Math.abs(GameLogic.lastDoubleStepMovedPawn.col - col) == 1 && target_row == row + direction && target_col == GameLogic.lastDoubleStepMovedPawn.col &&GameLogic.piece_position[target_row][target_col] == null){
                return true;
            }else return Math.abs(target_col - curr_col) == 1 && target_row == curr_row + direction && GameLogic.piece_position[target_row][target_col] != null && GameLogic.piece_position[target_row][target_col].isWhite() != this.isWhite();
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
