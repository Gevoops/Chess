import javax.swing.*;


public class Main {

    public static Drawer panel;
    public static Piece[][] piece_position;


    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Chess");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setSize(1000,1000);

        panel = new Drawer();
        window.add(panel);

        piece_position = new Piece[8][8];
        GamePlay.initPosition(piece_position,Piece.init_pieces());
        MouseHandler mouse = new MouseHandler();
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);
    }
}