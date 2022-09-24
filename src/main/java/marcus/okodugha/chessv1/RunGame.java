//package marcus.okodugha.chessv1;
//import marcus.okodugha.chessv1.Model.Board;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import static marcus.okodugha.chessv1.Model.Board.column;
//import static marcus.okodugha.chessv1.Model.Board.row;
//
//public class RunGame {
//    public static void main(String[] args) throws IOException {
//
//        JFrame frame = new JFrame();
//        frame.setBounds(10,10,528,552);
//
//        BufferedImage all= ImageIO.read(new File("C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\chess.png"));
//        Image imgs[]=new Image[12];
//        int ind=0;
//        for (int i = 0; i < 400; i+=200) {
//            for (int j = 0; j < 1200; j+=200) {
//                imgs[ind]=all.getSubimage(j,i,200,200).getScaledInstance(64,64,BufferedImage.SCALE_SMOOTH);
//            ind++;
//            }
//        }
//
//        Board board = new Board();
//
//        //        frame.setResizable(false);
//        //        frame.setUndecorated(true);
//
//        JPanel pn = new JPanel(){
//            @Override
//            public void paint(Graphics g) {
//        System.out.println(frame.getBounds());
//                boolean white = true;
//                for (int i = 0; i < 8; i++) {
//                    for (int j = 0; j < 8; j++) {
//                        if (white){
//                            g.setColor(Color.white);
//                        }else {
//                            g.setColor(Color.PINK);
//                        }
//                        g.fillRect(i*64,j*64,64,64);
//                        white=!white;
//                    }
//                    white=!white;
//                }
//
//                for (int i = 0; i < row; i++) {//showpieces on board
//                    for (int j = 0; j < column; j++) {
//                        if (board.getBoard().get(i).get(j).getImageIndex()!=12){//if imige index = 11 dont draw
//                            g.drawImage(imgs[board.getBoard().get(i).get(j).getImageIndex()],j*64,i*64,this);
//                        }
//                    }
//
//                }
//            }
//        };
//
//        pn.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
//        pn.setLayout(new GridLayout(0,1));
//        frame.add(pn);
//        frame.setDefaultCloseOperation(3);
//        frame.setVisible(true);
//
//    }
//}
