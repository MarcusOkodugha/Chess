package marcus.okodugha.chessv1.View;

import marcus.okodugha.chessv1.Model.*;

//import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static marcus.okodugha.chessv1.Model.Board.column;
import static marcus.okodugha.chessv1.Model.Board.row;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class BoardView  {
        Board board;
        boolean flipBlack =false,flipWhite = false;
        JFrame frame = new JFrame();
        BufferedImage all = ImageIO.read(new File("C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\chess.png"));
        Image imgs[] = new Image[12];
        Icon imgsIcons[] = new Icon[12];
        JLabel[][] numberTiles;
        JPanel panel = new JPanel();

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        int pressCount=0;
        int srcX=0;
        int srcY=0;
        Rules rules;
        int destRow=0;
        int destColumn=0;
        private Piece emptyPiece = new Piece(marcus.okodugha.chessv1.Model.Color.NOCOLOR,PieceType.EMPTY,12);

    PointerInfo pointerInfo = MouseInfo.getPointerInfo();



    public BoardView(Board board, Rules rules) throws IOException {
        this.board = board;
        this.rules = rules;
        initBoardView();
    }

    public void initBoardView() throws IOException {
        frame.setBounds(10,10,512,512);
//        frame.setSize(528,552);
        frame.setLayout(new BorderLayout());
        numberTiles=new JLabel[row][column];

        int ind = 0;
        for (int i = 0; i < 400; i += 200) {
            for (int j = 0; j < 1200; j += 200) {
                imgs[ind] = all.getSubimage(j, i, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                if (ind==4){
                }else {

                }
                    imgsIcons[ind] = new ImageIcon(all.getSubimage(j, i, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH));
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

        Color lightBlueColor = new Color(235, 233,210);
        Color beige = new Color(75, 115, 153);
    public void showBoard(){
        panel.setLayout(new GridLayout(row,column));
        boolean white = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel tile = new JLabel();
                if (white) {
                    tile.setBackground(beige);
                } else {
                    tile.setBackground(lightBlueColor);
                }
                tile.setBounds(i*64,j*64,64,64);
                if(board.getBoard().get(i).get(j).getImageIndex() != 12){
                    Icon icon = new ImageIcon(imgs[board.getBoard().get(i).get(j).getImageIndex()]);
                    tile.setIcon(icon);
                }
                numberTiles[i][j]=tile;
                numberTiles[i][j].setOpaque(true);
                panel.add(numberTiles[i][j]);
                white = !white;
            }
            white = !white;
        }
//        frame.add(panel4,BorderLayout.NORTH);
//        frame.add(panel2,BorderLayout.EAST);
//        frame.add(panel1,BorderLayout.SOUTH);
//        frame.add(panel3,BorderLayout.WEST);

        frame.add(panel,BorderLayout.CENTER); //add gridview
        frame.addMouseMotionListener(mouseMotionListener);
        frame.addMouseListener(mouseListener);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);

    }

    public void show(){
        boolean white = true;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (white) {
                    numberTiles[i][j].setBackground(beige);
                } else {
                    numberTiles[i][j].setBackground(lightBlueColor);
                }
                if(board.getBoard().get(i).get(j).getImageIndex() != 12){
                    numberTiles[i][j].setIcon(imgsIcons[board.getBoard().get(i).get(j).getImageIndex()]);
                }
                if (board.getBoard().get(i).get(j).getImageIndex() == 12){
                    numberTiles[i][j].setIcon(null);
                }
                white = !white;
            }
            white = !white;

        }

    }
        Icon holdingPieceIcon = null;

        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {

            currentTime++;
            if (lastTime+5<currentTime){
                panel.repaint();
                lastTime=currentTime;
            }
                paintOnMouse(holdingPieceIcon,e);
        }
        @Override
        public void mouseMoved(MouseEvent e) {

        }
    };
    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }
        @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("mouse pressed");
                srcX=e.getX()/64;
            srcY=e.getY()/64;
            if (board.getBoard().get(srcY).get(srcX).getPieceType()!= PieceType.EMPTY){//selected peice is not empty
                    holdingPieceIcon= imgsIcons[board.getBoard().get(srcY).get(srcX).getImageIndex()];
            }
            //legalMoves
            show();
            int k=0;
            for (Point p:board.getLegalMoves(srcX,srcY,board.getBoard().get(srcY).get(srcX))) {
                if (numberTiles[(board.legalMoves.get(k).y)][(board.legalMoves.get(k).x)].getIcon()==null){
                    numberTiles[board.legalMoves.get(k).y][board.legalMoves.get(k).x].setBackground(Color.GREEN);
                }else {
                    numberTiles[board.legalMoves.get(k).y][board.legalMoves.get(k).x].setBackground(Color.RED);
                }
                k++;
            }
            board.legalMoves.clear();
//           show();//todo chek if redundet call
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            board.movePiece(srcX,srcY,e.getX()/64,e.getY()/64);
            panel.repaint();
            show();
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
    int xMouseOffset =32;
    int yMouseOffset=32;
    static int currentTime=0;
    static int lastTime=0;
    private void paintOnMouse(Icon icon, MouseEvent e){
        icon.paintIcon(panel, frame.getGraphics(), e.getX()-xMouseOffset,e.getY()-yMouseOffset);

    }




}