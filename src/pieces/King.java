package pieces;

import gameplay.GameLogic;

public class King extends Piece implements firstMovable {
    private boolean moved = false;
    public static Piece whiteKing;
    public static Piece blackKing;

    public King(int row, int col, Boolean isWhite, String name) {

        super(row, col, isWhite, name);
        if(isWhite){
            whiteKing = this;
        }else{
            blackKing = this;
        }
    }




    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        if(super.isLegalMove(target_row,target_col)){
            if(this.isWhite()){ // cant step into attacked square
                if(GameLogic.blackAttacks[target_row][target_col]){
                    return false;
                }
            }else{
                if (GameLogic.whiteAttacks[target_row][target_col]){
                    return false;
                }
            }
            if(Math.abs(target_col -this.getCol()) <= 1 && Math.abs(target_row - this.getRow())<= 1) { // normal king step
                return GameLogic.piece_position[target_row][target_col] == null || !GameLogic.piece_position[target_row][target_col].isWhite() == this.isWhite();
            }else if(target_row == this.getRow() && Math.abs(target_col - this.getCol()) == 2) { // trying to castle?
                return isValidKingSideCastle(target_row,target_col) || isValidQueenSideCastle(target_row,target_col);
            } else {
                return false;
            }
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
                && GameLogic.piece_position[target_row][target_col - 1] == null
                && (this.isWhite() ? !GameLogic.blackAttacks[target_row][target_col] : !GameLogic.whiteAttacks[target_row][target_col])
                && (this.isWhite() ? !GameLogic.blackAttacks[target_row][target_col - 1] : !GameLogic.whiteAttacks[target_row][target_col - 1]) ;
    }
    public boolean isValidQueenSideCastle(int target_row, int target_col){
        return !moved && target_row == this.getRow() && target_col == this.getCol() -2
                && GameLogic.piece_position[target_row][target_col - 2] != null
                && GameLogic.piece_position[target_row][target_col - 2] instanceof Rook
                && !((Rook) GameLogic.piece_position[target_row][target_col - 2]).getMoved()
                && GameLogic.piece_position[target_row][target_col - 1] == null
                && GameLogic.piece_position[target_row][target_col] == null
                && GameLogic.piece_position[target_row][target_col + 1] == null
                && (this.isWhite() ? !GameLogic.blackAttacks[target_row][target_col] : !GameLogic.whiteAttacks[target_row][target_col])
                && (this.isWhite() ? !GameLogic.blackAttacks[target_row][target_col + 1] : !GameLogic.whiteAttacks[target_row][target_col + 1]);
    }
    public void setMovedTrue(){
        moved = true;
    }

}
