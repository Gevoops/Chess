package pieces;

import gameplay.GameLogic;

public interface MovingLikeABishop {
    default boolean isLegalBishopMove(int target_row, int target_col) {
        return  bishopMove(target_row,target_col,((Piece)this).getRow(),((Piece)this).getCol()) && checkCollision(target_row, target_col,(Piece)this);
    }

    private boolean checkCollision(int target_row,int target_col,Piece piece){
        int row_index = piece.getRow();
        int col_index = piece.getCol();
        int row_increment = target_row - row_index > 0 ? 1 : -1;
        int col_increment = target_col - col_index > 0 ? 1 : -1;
        boolean secondEnemyColor = false;
        col_index += col_increment;
        row_index += row_increment;

        for (int i = 0; i < Math.abs(target_row - piece.getRow());i++,row_index += row_increment, col_index += col_increment){
            if(GameLogic.piece_position[row_index][col_index] != null){
                if(piece.isWhite() == GameLogic.piece_position[row_index][col_index].isWhite() || (target_row != row_index)){
                    return false;
                } else {
                    if(secondEnemyColor){
                        return false;
                    } else {
                        secondEnemyColor = true;
                    }
                }
            }
        }
        return true;
    }

    private boolean bishopMove(int target_row, int target_col, int current_row, int current_col){
        return Math.abs(target_col - current_col) == Math.abs(target_row - current_row);
    }
}
