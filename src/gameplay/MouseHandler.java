package gameplay;

import painter.Painter;
import main.Main;
import pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseHandler extends MouseAdapter implements MouseMotionListener {
        private Piece piece;
        private boolean piece_lifted;
        private Piece lifted_piece;

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(isInsideBoard(e.getX(),e.getY())){
                if(((piece = GameLogic.piecePosition[Painter.pixelToBoardPos(Painter.flippedPixPos(e.getY()))][Painter.pixelToBoardPos(Painter.flippedPixPos(e.getX()))]) != null)){

                    piece_lifted = true;
                    lifted_piece = piece;
                    GameLogic.pieces.remove(piece);
                    GameLogic.pieces.add(piece);
                }
            }
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);

            if(piece_lifted){
                lifted_piece.setX(Painter.flippedPixPos(e.getX() - Painter.getTileSize()/2));
                lifted_piece.setY(Painter.flippedPixPos(e.getY() - Painter.getTileSize()/2));
                Main.panel.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if(piece_lifted) {
                lifted_piece.move(Painter.pixelToBoardPos(Painter.flippedPixPos(e.getY())), Painter.pixelToBoardPos(Painter.flippedPixPos(e.getX())) );
                lifted_piece = null;
                piece_lifted = false;
                Main.panel.repaint();
            }
        }
    private boolean isInsideBoard(int mouseX, int mouseY){
        return mouseX <= Painter.getTileSize() * 8 + Painter.getOffset() && mouseX >= Painter.getOffset()
                && mouseY <= Painter.getTileSize() * 8 + Painter.getOffset() && mouseY >= Painter.getOffset();
    }

}
