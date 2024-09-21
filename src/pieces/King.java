package pieces;

import gameplay.GameLogic;

public class King extends Piece implements firstMovable {
    private boolean moved = false;

    public King(int row, int col, String color, String name) {
        super(row, col, color, name);
    }




    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        if(Math.abs(target_col -this.getCol()) <= 1 && Math.abs(target_row - this.getRow())<= 1) {
            return GameLogic.piece_position[target_row][target_col] == null || !GameLogic.piece_position[target_row][target_col].getColor().equals(this.getColor());
        }else if(target_row == this.getRow() && Math.abs(target_col - this.getCol()) == 2) {
            return isValidKingSideCastle(target_row,target_col) || isValidQueenSideCastle(target_row,target_col);
        } else {
            return false;
        }
    }
    public boolean isValidKingSideCastle(int target_row, int target_col){
        return !moved && target_row == this.getRow() && target_col == this.getCol() + 2
                && GameLogic.piece_position[target_row][target_col + 1] != null
                && GameLogic.piece_position[target_row][target_col + 1] instanceof Rook
                && !((Rook) GameLogic.piece_position[target_row][target_col + 1]).getMoved()
                && GameLogic.piece_position[target_row][target_col] == null
                && GameLogic.piece_position[target_row][target_col - 1] == null;
    }
    public boolean isValidQueenSideCastle(int target_row, int target_col){
        return !moved && target_row == this.getRow() && target_col == this.getCol() -2
                && GameLogic.piece_position[target_row][target_col - 2] != null
                && GameLogic.piece_position[target_row][target_col - 2] instanceof Rook
                && !((Rook) GameLogic.piece_position[target_row][target_col - 2]).getMoved()
                && GameLogic.piece_position[target_row][target_col - 1] == null
                && GameLogic.piece_position[target_row][target_col] == null
                && GameLogic.piece_position[target_row][target_col + 1] == null;
    }
    public void setMovedTrue(){
        moved = true;
    }
    public boolean getFirstMove(){
        return moved;
    }
}
