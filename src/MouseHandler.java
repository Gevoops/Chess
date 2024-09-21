import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseHandler extends MouseAdapter implements MouseMotionListener {
        private Piece piece;
        private boolean piece_lifted;
        private Piece lifted_piece;
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            System.out.println("click test");
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            if(isInsideBoard(e.getX(),e.getY()) ){
                if(!((piece = Main.piece_position[(e.getY() - Drawer.getOffset())/Drawer.getTileSize()][(e.getX()- Drawer.getOffset())/Drawer.getTileSize()])==null) && !piece_lifted){
                    piece_lifted = true;
                    lifted_piece = piece;
                }
            }
            if(piece_lifted){
                lifted_piece.setX(e.getX() - Drawer.getTileSize()/2);
                lifted_piece.setY(e.getY() - Drawer.getTileSize()/2);
                Main.panel.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if(piece_lifted) {
                lifted_piece.move(Drawer.pixelToBoardPos(e.getY()),Drawer.pixelToBoardPos(e.getX()));
                lifted_piece = null;
                piece_lifted = false;
                Main.panel.repaint();
            }
        }
    private boolean isInsideBoard(int mouseX, int mouseY){
        return mouseX <= Drawer.getTileSize() * 8 + Drawer.getOffset() && mouseX >= Drawer.getOffset()
                && mouseY <= Drawer.getTileSize() * 8 + Drawer.getOffset() && mouseY >= Drawer.getOffset();
    }

}
