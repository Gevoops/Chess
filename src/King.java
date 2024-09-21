public class King extends Piece{
    public King(int row, int col, String color,String name) {
        super(row, col, color,name);
    }


    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        if(Math.abs(target_col -this.getCol()) <= 1 && Math.abs(target_row - this.getRow())<= 1){
            return Main.piece_position[target_row][target_col] == null || !Main.piece_position[target_row][target_col].getColor().equals(this.getColor());
        } else {
            return false;
        }

    }
}
