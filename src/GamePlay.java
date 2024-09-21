import java.util.ArrayList;

public class GamePlay {


    public static void initPosition(Piece[][] piece_position, ArrayList<Piece> pieces){
        for (int i = 0; i < 8;i++) {
            for (int j =0; j < 8; j ++) {
                piece_position[i][j] = null;
            }
        }
        for(Piece piece : pieces){
            piece_position[piece.getRow()][piece.getCol()] = piece;
        }
    }


}
