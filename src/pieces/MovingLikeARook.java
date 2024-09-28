package pieces;

import gameplay.GameLogic;

public interface MovingLikeARook {
     default boolean isLegalRookMove(int target_row, int target_col) {
        return  rookMove(target_row,target_col,((Piece)this).getRow(),((Piece)this).getCol()) && checkCollision(target_row, target_col,(Piece)this);
    }

     private boolean checkCollision(int target_row,int target_col, Piece piece){
        int row_index = piece.getRow();
        int col_index = piece.getCol();
        boolean secondEnemyColor = false;
        int col_increment = 0,row_increment = 0;
        int distance;
        if(target_col != col_index){
            col_increment = target_col - col_index > 0 ? 1 : -1;
            distance = Math.abs(target_col - col_index);
            col_index+=col_increment;
        } else {
            row_increment = target_row - row_index > 0 ? 1 : -1;
            distance = Math.abs(target_row - row_index);
            row_index+=row_increment;
        }

        for (int i = 0; i < distance;i++,row_index += row_increment,col_index+=col_increment){
            if(GameLogic.piecePosition[row_index][col_index] != null){
                if(piece.isWhite() == (GameLogic.piecePosition[row_index][col_index].isWhite()) || (target_col != col_index || target_row != row_index)){
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


     private boolean rookMove(int target_row, int target_col, int current_row, int current_col){
        return (target_col == current_col || target_row == current_row);
    }
}
