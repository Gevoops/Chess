package gameplay;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;

import java.util.ArrayList;

public class GameLogic {

    public static Piece[][] piecePosition;
    public static ArrayList<Piece> pieces;
    public static boolean[][] whiteAttacks = new boolean[8][8];
    public static boolean[][] blackAttacks = new boolean[8][8];
    public static boolean isWhiteTurn = true;
    public static Piece lastDoubleStepMovedPawn = null;
    public static boolean promotionMenuOpen = false;
    public static int checkCount = 0;

    public static void initPosition(){
        piecePosition = new Piece[8][8];
        for (int i = 0; i < 8;i++) {
            for (int j =0; j < 8; j ++) {
                piecePosition[i][j] = null;
            }
        }
        for(Piece piece : pieces){
            piecePosition[piece.getRow()][piece.getCol()] = piece;
        }
    }


    public static void whiteAttacks(){
        whiteAttacks = new boolean[8][8];
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(piecePosition[i][j] != null && piecePosition[i][j].isWhite())
                    attack(piecePosition[i][j],whiteAttacks);
            }
        }
    }
    public static void blackAttacks(){
        blackAttacks = new boolean[8][8];
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(piecePosition[i][j] != null && !piecePosition[i][j].isWhite())
                   attack(piecePosition[i][j],blackAttacks);
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
    public static boolean checkLegalMoveExists(boolean amWhite){
        for (int i =0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(amWhite && piecePosition[i][j] != null && piecePosition[i][j].isWhite() && checkLegalMoveExists(piecePosition[i][j])){
                    return true;
                } else if (!amWhite && piecePosition[i][j] != null && !piecePosition[i][j].isWhite() && checkLegalMoveExists(piecePosition[i][j])){
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean checkLegalMoveExists(Piece piece){
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j++){
                if(piece.isLegalMove(i,j) && !GameLogic.willKingBeInCheck(i,j,piece)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean willKingBeInCheck(int target_row, int target_col, Piece movingPiece){
        boolean result = false;
        int initial_row = movingPiece.getRow();
        int initial_col = movingPiece.getCol();
        Piece potentially_eaten; // save potentially eaten piece to return it later.
        potentially_eaten = piecePosition[target_row][target_col];

        movingPiece.setPosition(target_row,target_col);
        piecePosition[initial_row][initial_col] = null;
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
        piecePosition[target_row][target_col] = potentially_eaten;

        return result;
    }
    public static void checkOrMateOrStale(){
        boolean check = false;
        if(inCheck(isWhiteTurn)){
            System.out.println("check! " + ++checkCount );
            check = true;
        }
        if(!checkLegalMoveExists(isWhiteTurn)){
            if(check){
                System.out.println("and mate!");
            }else{
                System.out.println("stalemate");
            }
        }

    }
    public static boolean inCheck(Boolean white){
        if(white) {
            return blackAttacks[King.whiteKing.getRow()][King.whiteKing.getCol()];
        } else {
            return whiteAttacks[King.blackKing.getRow()][King.blackKing.getCol()];
        }
    }

    public static void eat(Piece eaten){
        piecePosition[eaten.getRow()][eaten.getCol()] = null;
        pieces.remove(eaten);
    }
}
