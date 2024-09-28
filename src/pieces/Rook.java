package pieces;


import java.awt.image.BufferedImage;

public class Rook extends Piece implements firstMovable,MovingLikeARook{
    private boolean moved = false;
    public Rook(int row, int col, Boolean isWhite, BufferedImage image) {
        super(row, col, isWhite,  image);
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


