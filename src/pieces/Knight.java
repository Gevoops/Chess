package pieces;

import gameplay.GameLogic;

import java.awt.image.BufferedImage;

public class Knight extends Piece{
    public Knight(int row, int col, Boolean isWhite, BufferedImage image) {
        super(row, col, isWhite,image);
    }

    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        return super.isLegalMove(target_row,target_col)
                && Math.abs(target_row - row) + Math.abs(target_col - col) == 3
                && target_row != row && target_col != col
                && (GameLogic.piecePosition[target_row][target_col] == null ||
                GameLogic.piecePosition[target_row][target_col].isWhite != isWhite);
    }
}
