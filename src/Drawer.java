import javax.swing.*;
import java.awt.*;


public class Drawer extends JPanel {
    private static final int tileSize = 100;


    private static final int offset = 100;
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Drawer.drawBoard(g);
        drawPieces(g);
    }

    public static void drawBoard(Graphics g) {
        Color brown = new Color(222,184,135);
        Color color;
        for(int i = 0; i < 8; i ++){
            for(int j = 0; j < 8; j++){
                if((i+j) % 2 == 0){
                    color =  Color.white;
                } else {
                    color = brown;
                }
                g.setColor(color);
                g.fillRect(BoardToPixelPos(i),BoardToPixelPos(j),tileSize,tileSize);
            }
        }
    }

    public static void drawPieces(Graphics g){
        Piece piece;
        Font customFont = new Font("Serif", Font.PLAIN, 24); // Font size 24
        g.setFont(customFont);
        g.setColor(Color.black);
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((piece = Main.piece_position[i][j] )!= null) {
                    g.drawString(piece.getName(),piece.getX() + tileSize/2, piece.getY()  + tileSize/2);
                    g.drawOval(piece.getX(),piece.getY(),tileSize,tileSize);
                }
            }
        }
    }
    public static int pixelToBoardPos(int coordinate){
        return (coordinate - offset)/tileSize;
    }
    public static int BoardToPixelPos(int coordinate){
        return coordinate * tileSize + offset;
    }

    public static int getTileSize() {
        return tileSize;
    }

    public static int getOffset() {
        return offset;
    }


}
