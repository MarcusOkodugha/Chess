package marcus.okodugha.chessv1.View;
import marcus.okodugha.chessv1.Model.*;

import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import static marcus.okodugha.chessv1.Model.Board.column;
import static marcus.okodugha.chessv1.Model.Board.row;

public class BoardView  {
    Color beige = new Color(235, 233,210);
    Color lightBlueColor = new Color(75, 115, 153);
    Board board;
    boolean flipBlack =false,flipWhite = false;
    JFrame frame = new JFrame();
    BufferedImage all = ImageIO.read(new File("C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\chess.png"));
    Image[] imgs = new Image[12];
    Icon[] imgsIcons = new Icon[12];
    JLabel[][] numberTiles;
    JLabel[][] invicebelLables;
    JPanel panel = new JPanel();
//    JLayeredPane panel = new JLayeredPane();

    JPanel menuPanel = new JPanel();
    JButton undoButton = new JButton("Undo");
    JButton resetButton = new JButton("Reset");
    JButton infinityWhiteButton = new JButton("Infinity White");
    JButton button1 = new JButton("1");
    JButton moveButton = new JButton("move");
    JButton infinityBlackButton = new JButton("Infinity Black");

    int srcX=0;
    int srcY=0;
    private final int blockSize =94;




    public BoardView(Board board) throws IOException {
        this.board = board;
        initBoardView();
    }

    public void initBoardView() throws IOException {
//        frame.setBounds(1020,300,752,752); //todo för stor skärm
        frame.setBounds(400,0,752,792);
//        frame.setSize(528,552);
        frame.setLayout(new BorderLayout());
        numberTiles=new JLabel[row][column];
        invicebelLables=new JLabel[row][column];

        int ind = 0;
        for (int i = 0; i < 400; i += 200) {
            for (int j = 0; j < 1200; j += 200) {
                imgs[ind] = all.getSubimage(j, i, 200, 200).getScaledInstance( blockSize, blockSize, BufferedImage.SCALE_SMOOTH);
                imgsIcons[ind] = new ImageIcon(all.getSubimage(j, i, 200, 200).getScaledInstance(blockSize,blockSize, BufferedImage.SCALE_SMOOTH));
                ind++;
            }
        }
//        menuPanel.setBackground();
        menuPanel.setPreferredSize(new Dimension(100,40));
        panel.setLayout(new GridLayout(row,column));
        boolean white = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel tile = new JLabel();
                JLabel invicebelLabel = new JLabel();
                if (white) {
                    tile.setBackground(beige);
                } else {
                    tile.setBackground(lightBlueColor);
                }
                tile.setBounds(i*blockSize,j*blockSize,blockSize,blockSize);
//                invicebelLabel.setBounds(i*biggerSize,j*biggerSize,biggerSize,biggerSize);
                if(board.getBoard().get(i).get(j).getImageIndex() != 12){
                    Icon icon = new ImageIcon(imgs[board.getBoard().get(i).get(j).getImageIndex()]);
//                    invicebelLabel.setIcon(icon);
//                    invicebelLabel.setBackground(Color.RED);
                    tile.setIcon(icon);
                }

                numberTiles[i][j]=tile;
                numberTiles[i][j].setOpaque(true);
                if (i==1&&j==1){
                    numberTiles[1][1].setBounds(1*blockSize,1*blockSize,-blockSize,-blockSize);//todo remvoe rotation test
                    numberTiles[1][1].setBackground(Color.BLUE);//todo remvoe rotation test
                }
//                panel.setLayer(numberTiles[i][j],JLayeredPane.DEFAULT_LAYER);
                panel.add(numberTiles[i][j]);

//                invicebelLables[i][j]=invicebelLabel;
//                invicebelLables[i][j].setOpaque(true);
//                panel.setLayer(invicebelLables[i][j],1);
//                panel.add(invicebelLables[i][j],2,0);

                white = !white;
            }
            white = !white;
        }


        //menu Panel and buttons
        menuPanel.add(button1);
        button1.setPreferredSize(new Dimension(100,30));
        button1.addActionListener(actionListener);

        menuPanel.add(moveButton);
        moveButton.setPreferredSize(new Dimension(100,30));
        moveButton.addActionListener(actionListener);

        menuPanel.add(infinityBlackButton);
        infinityBlackButton.setPreferredSize(new Dimension(100,30));
        infinityBlackButton.addActionListener(actionListener);

        menuPanel.add(infinityWhiteButton);
        infinityWhiteButton.setPreferredSize(new Dimension(100,30));
        infinityWhiteButton.addActionListener(actionListener);

        menuPanel.add(undoButton);
        undoButton.setPreferredSize(new Dimension(100,30));
        undoButton.addActionListener(actionListener);

        menuPanel.add(resetButton);
        resetButton.setPreferredSize(new Dimension(100,30));
        resetButton.addActionListener(actionListener);

        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(40,40));
        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(40,40));
        JPanel estPanel = new JPanel();
        estPanel.setPreferredSize(new Dimension(40,40));
        //frame
        frame.add(menuPanel,BorderLayout.SOUTH);
//        frame.add(estPanel,BorderLayout.EAST);
//        frame.add(westPanel,BorderLayout.WEST);
//        frame.add(southPanel,BorderLayout.SOUTH);
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
//                    invicebelLables[i][j].setIcon(imgsIcons[board.getBoard().get(i).get(j).getImageIndex()]);
//                    invicebelLables[i][j].setBackground(Color.RED);//todo remove
                }
                if (board.getBoard().get(i).get(j).getImageIndex() == 12){
                    numberTiles[i][j].setIcon(null);
//                    invicebelLables[i][j].setIcon(null);
                }
                white = !white;
            }
            white = !white;
        }
        if (board.getLatestMove()!=null){
            paintTile(board.getLatestMove().srcX,board.getLatestMove().srcY,new Color(255,247,0));//yellow
            paintTile(board.getLatestMove().destX,board.getLatestMove().destY,new Color(204,153,255));//purple

        }

    }


    private void showLegalMoves(MouseEvent e){
        int k=0;
        show();
        for (Point p:board.getLegalMovesForPiece(srcX,srcY)) {
            panel.repaint();
            if (holdingPieceIcon!=null){
                paintOnMouse(holdingPieceIcon,e);
            }
            if (numberTiles[(board.legalMoves.get(k).y)][(board.legalMoves.get(k).x)].getIcon()==null){
                if (numberTiles[(board.legalMoves.get(k).y)][(board.legalMoves.get(k).x)].getBackground()==lightBlueColor){
                    numberTiles[board.legalMoves.get(k).y][board.legalMoves.get(k).x].setBackground(new Color(62,156,123));//dark green
                }else {
                    numberTiles[board.legalMoves.get(k).y][board.legalMoves.get(k).x].setBackground(new Color(174,238,163));//light green
                }
            }else {
                if (numberTiles[(board.legalMoves.get(k).y)][(board.legalMoves.get(k).x)].getBackground()==lightBlueColor){
                    numberTiles[board.legalMoves.get(k).y][board.legalMoves.get(k).x].setBackground(new Color(237,12,15));//dark Red
                }else {
                    numberTiles[board.legalMoves.get(k).y][board.legalMoves.get(k).x].setBackground(new Color(249,70,63));//light Red
                }
            }
            k++;
        }
        if (!board.legalMoves.isEmpty())paintTile(srcX,srcY,new Color(255,247,0));

        board.legalMoves.clear();
    }

    Icon holdingPieceIcon = null;
    Point pressPoint = new Point(-1,-1);
    MouseMotionListener mouseMotionListener = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
            currentTime++;
            if (lastTime+5<currentTime){
                panel.repaint();
                if (holdingPieceIcon!=null){
                    paintOnMouse(holdingPieceIcon,e);
                }
                lastTime=currentTime;
            }
            if (holdingPieceIcon!=null){
                paintOnMouse(holdingPieceIcon,e);
            }
        }
        @Override
        public void mouseMoved(MouseEvent e) {

        }
    };
    Point activPoint = new Point(-1,-1);

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

            if (activPoint.x!=-1&&activPoint.y!=-1){//second click
//                System.out.println("second click");
                board.movePiece(new Move(activPoint.x,activPoint.y,e.getX()/blockSize,e.getY()/blockSize));
                board.legalMoves.clear();
                show();
                activPoint.x=-1;
                activPoint.y=-1;
            }
            if (activPoint.x==-1&&activPoint.y==-1){//first click
//                System.out.println("first click");
                srcX=e.getX()/blockSize;
                srcY=e.getY()/blockSize;
                show();
                showLegalMoves(e);
//                paintTile(srcX,srcY,new Color(255,247,0));
                activPoint.x=srcX;
                activPoint.y=srcY;
            }
        }
        @Override
            public void mousePressed(MouseEvent e) {
            srcX=e.getX()/blockSize;
            srcY=e.getY()/blockSize;
//            System.out.println("mouse pressed on tile x = "+srcX+" y= "+srcY);
            if (board.getBoard().get(srcY).get(srcX).getPieceType()!= PieceType.EMPTY){//selected peice is not empty
                    holdingPieceIcon= imgsIcons[board.getBoard().get(srcY).get(srcX).getImageIndex()];
                    pressPoint = new Point(srcX,srcY);
            }
            // show legalMoves
            show();
            showLegalMoves(e);
//            paintTile(srcX,srcY,new Color(255,247,0));

        }
        @Override
        public void mouseReleased(MouseEvent e) {
            holdingPieceIcon=null;//reset holdigPieceIcon when mouse is released
            pressPoint=null;
            if (srcX==e.getX()/blockSize&&srcY==e.getY()/blockSize){//keeps showing legalmoves if mouse is released on srcX srcY
                showLegalMoves(e);
//                paintTile(srcX,srcY,new Color(255,247,0));
            }else {
                board.movePiece(new Move(srcX,srcY,e.getX()/blockSize,e.getY()/blockSize));
                panel.repaint();
                show();
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
    int xMouseOffset =blockSize/2;
    int yMouseOffset=blockSize/2;
    static int currentTime=0;
    static int lastTime=0;
    private void paintOnMouse(Icon icon, MouseEvent e){
        icon.paintIcon(panel, frame.getGraphics(), e.getX()-xMouseOffset,e.getY()-yMouseOffset);
//        invicebelLables[pressPoint.x][pressPoint.y].setBounds(e.getX(),e.getY(),biggerSize,biggerSize);
    }


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==undoButton){
                board.undoMove();
                show();
            }
            if (e.getSource()==resetButton){
                board.resetBoard();
                show();
            }
            if (e.getSource()==infinityWhiteButton){
                if (board.isWhiteTurn()){
                    board.getInfinityWhite().updateAllLegalAiMoves();
                    if (board.allLegalWhiteMoves.size() == 0){
                        System.out.println("no legal White moves Black Wins!!!!");
                        board.gamIsRunning=false;
                        return;
                    }
//                    board.getInfinityWhite().makeRetardedMove();
                board.getInfinityWhite().makeCalculatedMove();
                }show();
            }
            if (e.getSource()==button1){

                show();
            }
            if (e.getSource()==moveButton){
                if (board.isWhiteTurn()){
                    board.getInfinityWhite().infinityMove();
                }else {
                    board.getInfinityBlack().infinityMove();
                }
                show();
            }
            if (e.getSource()== infinityBlackButton){
                if (!board.isWhiteTurn()){
                    board.getInfinityBlack().updateAllLegalAiMoves();
                    if (board.allLegalBlackMoves.size() == 0){
                        System.out.println("no legal Black moves White Wins!!!!");
                        board.gamIsRunning=false;
                        return;
                    }
                   board.getInfinityBlack().makeHighValueMovesElseOpening();
                }
                show();
            }
        }
    };

    private void paintTile(int srcX, int srcY, Color color){
        numberTiles[srcY][srcX].setBackground(color);
    }


}