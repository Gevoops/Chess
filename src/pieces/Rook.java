package pieces;

import gameplay.GameLogic;

public class Rook extends Piece implements firstMovable {
    private boolean moved = false;
    public Rook(int row, int col, String color,String name) {
        super(row, col, color,name);
    }


    @Override
    public boolean isLegalMove(int target_row, int target_col) {
        return (target_col == this.getCol() || target_row == this.getRow()) && checkCollision(target_row, target_col);
    }

    public boolean checkCollision(int target_row,int target_col){
        int row_index = this.getRow();
        int col_index = this.getCol();
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
            if(GameLogic.piece_position[row_index][col_index] != null){
                if(this.getColor().equals(GameLogic.piece_position[row_index][col_index].getColor())){
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
    public boolean getMoved(){
        return moved;
    }
    public void setMovedTrue(){
        moved = true;
    }

}


