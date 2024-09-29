package pieces;

import gameplay.GameLogic;
import java.awt.image.BufferedImage;

public class Pawn extends Piece implements firstMovable {
    private boolean moved = false;
    private final int direction;
    public Pawn(int row, int col, Boolean isWhite, BufferedImage image) {
        super(row, col, isWhite, image);
        direction = isWhite ? -1 : 1;
    }

    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        return super.isLegalMove(target_row, target_col) &&
                (isLegalPush(target_row,target_col)
                || isLegalEat(target_row, target_col)
                || isLegalEnpassant(target_row,target_col));
    }

    private boolean checkCollision(int target_row, int target_col){
        return GameLogic.piecePosition[target_row][target_col] == null && (GameLogic.piecePosition[row + direction][col] == null);
    }

    private boolean isLegalPush(int target_row, int target_col) {
        return col == target_col && checkCollision(target_row, target_col)
                && (row + direction == target_row || !moved && row + direction * 2 == target_row);
    }

    private boolean isLegalEat(int target_row, int target_col){
        return Math.abs(target_col - col) == 1 && target_row == row + direction && GameLogic.piecePosition[target_row][target_col] != null && GameLogic.piecePosition[target_row][target_col].isWhite() != this.isWhite();
    }
    private boolean isLegalEnpassant(int target_row, int target_col){
        if(GameLogic.lastDoubleStepMovedPawn != null
                && GameLogic.lastDoubleStepMovedPawn.getRow() == row
                && target_row == row + direction && target_col == GameLogic.lastDoubleStepMovedPawn.col ){

            GameLogic.eat(GameLogic.piecePosition[target_row - direction][target_col]);
            return true;
        } else {
            return false;
        }
    }

    public void setMovedTrue(){
        moved = true;
    }

    public int getDirection() {
        return direction;
    }
}

