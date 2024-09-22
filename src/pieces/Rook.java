package pieces;



public class Rook extends Piece implements firstMovable,MovingLikeARook{
    private boolean moved = false;
    public Rook(int row, int col, Boolean isWhite,String name) {
        super(row, col, isWhite,name);
    }


    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        return super.isLegalMove(target_row, target_col) && isLegalRookMove(target_row, target_col);
    }

    public boolean getMoved(){
        return moved;
    }
    public void setMovedTrue(){
        moved = true;
    }

}


