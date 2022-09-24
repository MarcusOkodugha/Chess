package marcus.okodugha.chessv1.View;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import marcus.okodugha.chessv1.Model.Board;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static marcus.okodugha.chessv1.Model.Board.column;
import static marcus.okodugha.chessv1.Model.Board.row;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BoardView implements MouseListener {
        Board board;
        boolean flipBlack =false,flipWhite = false;
        JFrame frame = new JFrame();
        BufferedImage all = ImageIO.read(new File("C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\chess.png"));
        Image imgs[] = new Image[12];
        JLabel[][] numberTiles;
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

    public BoardView(Board board) throws IOException {
        this.board = board;
        initBoardView();
    }

    public void initBoardView() throws IOException {

        frame.setSize(728,752);
        frame.setLayout(new BorderLayout());
        numberTiles=new JLabel[row][column];

        int ind = 0;
        for (int i = 0; i < 400; i += 200) {
            for (int j = 0; j < 1200; j += 200) {
                imgs[ind] = all.getSubimage(j, i, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }

        //testing
        panel1.setBackground(Color.GRAY);
        panel1.setPreferredSize(new Dimension(100,100));

        panel2.setBackground(Color.GRAY);
        panel2.setPreferredSize(new Dimension(100,100));

        panel3.setBackground(Color.GRAY);
        panel3.setPreferredSize(new Dimension(100,100));

        panel4.setBackground(Color.GRAY);
        panel4.setPreferredSize(new Dimension(100,100));

        panel5.setBackground(Color.MAGENTA);
        panel5.setPreferredSize(new Dimension(100,100));


    }

    public void showBoard(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(row,column));
        Color lightBlueColor = new Color(116, 171, 228);
        boolean white = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel tile = new JLabel();
                if (white) {
                    tile.setBackground(Color.white);
                } else {
                    tile.setBackground(lightBlueColor);
                }
                tile.setBounds(i*64,j*64,64,64);

                if(board.getBoard().get(i).get(j).getImageIndex() != 12){
                    Icon icon = new ImageIcon(imgs[board.getBoard().get(i).get(j).getImageIndex()]);
                    tile.setIcon(icon);
                }
                tile.addMouseListener(this);
                numberTiles[i][j]=tile;
                numberTiles[i][j].setOpaque(true);
//                numberTiles[i][j].setVisible(true);

                panel.add(numberTiles[i][j]);
                white = !white;
            }
            white = !white;
        }
        frame.add(panel4,BorderLayout.NORTH);
        frame.add(panel2,BorderLayout.EAST);
        frame.add(panel1,BorderLayout.SOUTH);
        frame.add(panel3,BorderLayout.WEST);

        frame.add(panel,BorderLayout.CENTER); //add gridview
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse Clicked");
        numberTiles[1][1].setBackground(Color.RED);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
