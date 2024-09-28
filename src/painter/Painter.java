package painter;

import gameplay.GameLogic;
import main.Main;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Painter extends JPanel implements ActionListener {
    private static final int tileSize = 128;
    public static int flip = 0;

    private static final int offset = 128;
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Painter.drawBoard(g);
        drawPieces(g);
        drawTurn(g);
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
                g.fillRect(boardToPixelPos(i), boardToPixelPos(j),tileSize,tileSize);
            }
        }
    }
    public static void  drawTurn(Graphics g){
        Font customFont = new Font("SansSerif", Font.BOLD, 40);
        g.setFont(customFont);
        g.setColor(Color.black);
        if(GameLogic.isWhiteTurn){
            g.drawString("White's turn", offset/2,offset/2);
        }else{
            g.drawString("Black's turn", offset/2,offset/2);
        }

    }

    public static void drawPieces(Graphics g){
        for(Piece piece : GameLogic.pieces) {
            g.drawImage(piece.getImage(), flippedPixPos(piece.getX()) , flippedPixPos(piece.getY()) ,tileSize,tileSize, Main.panel);
        }

    }
    public static int flippedPixPos(int pixelPos){
        int posinbord = (pixelPos - offset);
        int posinsquare = posinbord % tileSize + (posinbord < 0 ? tileSize : 0);
        if(flip == 1){
            return (7  - pixelToBoardPos(pixelPos)) * tileSize + posinsquare  + offset;
        } else {
            return pixelPos;
        }
    }
    public static int pixelToBoardPos(int coordinate){
        return (coordinate - offset)/tileSize + (coordinate - offset < 0 ? -1 : 0); //make negative numbers round properly
    }
    public static int boardToPixelPos(int coordinate){
        return coordinate * tileSize + offset;
    }

    public static int getTileSize() {
        return tileSize;
    }

    public static int getOffset() {
        return offset;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        flip = 1 - flip;
        repaint();
    }
}
