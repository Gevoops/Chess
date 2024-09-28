package pieces;


import java.awt.image.BufferedImage;

public class Bishop extends Piece implements MovingLikeABishop{
    public Bishop(int row, int col, Boolean isWhite, BufferedImage image) {
        super(row, col, isWhite,  image);
    }



    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        return super.isLegalMove(target_row,target_col) && isLegalBishopMove(target_row,target_col);
    }
}
