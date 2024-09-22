package pieces;


public class Queen extends Piece implements MovingLikeARook,MovingLikeABishop{
    public Queen(int row, int col, Boolean isWhite,String name) {
        super(row, col, isWhite ,name);
    }

    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        return(super.isLegalMove(target_row, target_col)
                && (isLegalRookMove(target_row,target_col) ||
                isLegalBishopMove(target_row,target_col)));
    }
}
