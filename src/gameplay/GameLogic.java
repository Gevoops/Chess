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

    public static int checkCount = 0;

    public static void initPosition(){
        piecePosition = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
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
        for(Piece piece : pieces) {
            if(piece.isWhite()){
                attack(piece,whiteAttacks);
            }
        }
    }

    public static void blackAttacks(){
        blackAttacks = new boolean[8][8];
        for(Piece piece : pieces) {
            if(!piece.isWhite()){
                attack(piece,blackAttacks);
            }
        }
    }
    private static void attack(Piece piece,boolean[][] attackMatrix){
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j++){
                if(piece instanceof Pawn) {
                    if(i == piece.getRow() + ((Pawn)piece).getDirection() && Math.abs(j - piece.getCol()) == 1){
                        attackMatrix[i][j] = true;
                    }
                }else {
                    if (piece.isLegalMove(i, j)) {
                        attackMatrix[i][j] = true;
                    }
                }
            }
        }
    }
    public static boolean checkLegalMoveExists(boolean amWhite){
        ArrayList<Piece> temp_pieces = new ArrayList<>(pieces); //must copy to avoid ConcurrentModificationException
        for (Piece piece : temp_pieces){
            if(amWhite && piece.isWhite() && checkLegalMoveExists(piece)){
                return true;
            } else if (!amWhite && !piece.isWhite() && checkLegalMoveExists(piece)){
                return true;
            }
        }
        return false;
    }
    private static boolean checkLegalMoveExists(Piece piece){
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j++){
                if(piece.isLegalMove(i,j) && GameLogic.kingWillBeSafe(i, j, piece)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean kingWillBeSafe(int target_row, int target_col, Piece movingPiece){
        boolean result = true;
        int initial_row = movingPiece.getRow();
        int initial_col = movingPiece.getCol();
        Piece potentially_eaten; // save potentially eaten piece to put it back after moving simulation.
        potentially_eaten = piecePosition[target_row][target_col];
        pieces.remove(potentially_eaten);
        movingPiece.changePosition(target_row,target_col);
        updateAttacksAndChecks();
        if(movingPiece.isWhite() && King.whiteKing.isInCheck() || !movingPiece.isWhite() && King.blackKing.isInCheck()){
            result = false;
        }
        movingPiece.changePosition(initial_row,initial_col);
        piecePosition[target_row][target_col] = potentially_eaten;
        if(potentially_eaten != null) {
            pieces.add(potentially_eaten);
        }
        updateAttacksAndChecks();
        // restore everything

        return result;
    }
    public static void checkOrMateOrStale(){
        boolean check = false;
        if(King.whiteKing.isInCheck() || King.blackKing.isInCheck()){
            System.out.println("check! " + ++checkCount);
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

    public static void updateAttacksAndChecks(){
        whiteAttacks();
        blackAttacks();
        updateChecks();
    }
    public static void updateChecks(){
        King.whiteKing.setInCheck(blackAttacks[King.whiteKing.getRow()][King.whiteKing.getCol()]);
        King.blackKing.setInCheck(whiteAttacks[King.blackKing.getRow()][King.blackKing.getCol()]);
    }

    public static void eat(Piece eaten){
        piecePosition[eaten.getRow()][eaten.getCol()] = null;
        pieces.remove(eaten);
    }

    public static void promote(Piece pawn, Piece target_piece){
        GameLogic.pieces.remove(pawn);
        GameLogic.pieces.add(target_piece);
        GameLogic.piecePosition[pawn.getRow()][pawn.getCol()] = target_piece;
        GameLogic.updateAttacksAndChecks();
        GameLogic.checkOrMateOrStale();
    }
}
