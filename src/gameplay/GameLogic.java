package gameplay;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;

import java.util.ArrayList;

public class GameLogic {

    public static Piece[][] piece_position;
    public static boolean[][] whiteAttacks = new boolean[8][8];
    public static boolean[][] blackAttacks = new boolean[8][8];
    public static boolean isWhiteTurn = true;
    public static Piece lastDoubleStepMovedPawn = null;

    public static void initPosition( ArrayList<Piece> pieces){
        piece_position = new Piece[8][8];
        for (int i = 0; i < 8;i++) {
            for (int j =0; j < 8; j ++) {
                piece_position[i][j] = null;
            }
        }
        for(Piece piece : pieces){
            piece_position[piece.getRow()][piece.getCol()] = piece;
        }
    }

    public static void whiteAttacks(){
        whiteAttacks = new boolean[8][8];
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(piece_position[i][j] != null && piece_position[i][j].isWhite())
                    attack(piece_position[i][j],whiteAttacks);
            }
        }
    }
    public static void blackAttacks(){
        blackAttacks = new boolean[8][8];
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(piece_position[i][j] != null && !piece_position[i][j].isWhite())
                   attack(piece_position[i][j],blackAttacks);
            }
        }
    }
    private static void attack(Piece piece,boolean[][] board){
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j++){
                if(!(piece instanceof Pawn)) {
                    if (piece.isLegalMove(i, j)) {
                        board[i][j] = true;
                    }
                }else {
                    if(i == piece.getRow() + (piece.isWhite() ? -1 : 1) && Math.abs(j - piece.getCol()) == 1){
                        board[i][j] = true;
                    }
                }
            }
        }
    }

    public static boolean willKingBeInCheck(int target_row, int target_col, Piece movingPiece){
        boolean result = false;
        int initial_row = movingPiece.getRow();
        int initial_col = movingPiece.getCol();
        Piece potentially_eaten; // save potentially eaten piece to return it later.
        potentially_eaten = piece_position[target_row][target_col];

        movingPiece.setPosition(target_row,target_col);
        piece_position[initial_row][initial_col] = null;
        if(movingPiece.isWhite()){
            blackAttacks();
            if(blackAttacks[King.whiteKing.getRow()][King.whiteKing.getCol()]){
               result = true;
            }
        } else {
            whiteAttacks();
            if(whiteAttacks[King.blackKing.getRow()][King.blackKing.getCol()]){
                result = true;
            }
        }
        movingPiece.setPosition(initial_row,initial_col);
        piece_position[target_row][target_col] = potentially_eaten;

        return result;
    }
}
