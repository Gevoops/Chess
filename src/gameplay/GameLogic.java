package gameplay;

import pieces.Piece;

import java.util.ArrayList;

public class GameLogic {

    public static Piece[][] piece_position;
    public static boolean[][] whiteAttacks = new boolean[8][8];
    public static boolean[][] blackAttacks = new boolean[8][8];

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
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(piece_position[i][j]!= null)
                    attack(piece_position[i][j],whiteAttacks);
            }
        }
    }
    public static void blackAttacks(){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(piece_position[i][j]!= null)
                   attack(piece_position[i][j],blackAttacks);
            }
        }
    }
    private static void attack(Piece piece,boolean[][] board){
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j++){
                board[i][j] = piece.isLegalMove(i,j);
            }
        }
    }


}
