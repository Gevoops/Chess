public class Pawn extends Piece {
    private boolean firstMove;
    public Pawn(int row, int col, String color,String name) {
        super(row, col, color,name);
        firstMove = true;
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
                firstMove = false;
                return true;
            } else if (firstMove && curr_row + direction * 2 == target_row){
                firstMove = false;
                return true;
            }
        } else if(Math.abs(target_col - curr_col) == 1 && target_row == curr_row + direction && Main.piece_position[target_row][target_col]!= null && !Main.piece_position[target_row][target_col].getColor().equals(this.getColor()))  {
            firstMove = false;
            return true;
        }
        return false;
    }
    public boolean checkCollision(int target_row, int target_col){
        return Main.piece_position[target_row][target_col] == null;
    }
}
