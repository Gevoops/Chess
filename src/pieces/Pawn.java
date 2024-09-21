package pieces;

import gameplay.GameLogic;

public class Pawn extends Piece implements firstMovable {
    private boolean moved = false;
    public Pawn(int row, int col, String color,String name) {
        super(row, col, color,name);
    }



    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        int direction;
        int curr_row = this.getRow(),curr_col = this.getCol();
        if(this.getColor().equals("white")){
            direction = -1;
        } else {
            direction = 1;
        }
        if(curr_col == target_col && checkCollision(target_row,target_col)){
            if(curr_row + direction == target_row){
                return true;
            } else return !moved && curr_row + direction * 2 == target_row;
        } else return Math.abs(target_col - curr_col) == 1 && target_row == curr_row + direction && GameLogic.piece_position[target_row][target_col] != null && !GameLogic.piece_position[target_row][target_col].getColor().equals(this.getColor());
    }
    public boolean checkCollision(int target_row, int target_col){
        return GameLogic.piece_position[target_row][target_col] == null;
    }
    public void setMovedTrue(){
        moved = true;
    }
}
