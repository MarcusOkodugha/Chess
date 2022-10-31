package marcus.okodugha.chessv1.View;
import marcus.okodugha.chessv1.Model.*;
//git hub test dev 2
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Timer;

import static marcus.okodugha.chessv1.Model.Board.*;
import static marcus.okodugha.chessv1.Model.BordUtilities.getBoardUtilitiesInstance;

public class BoardView  {
    Color beige = new Color(235, 233,210);
    Color lightBlueColor = new Color(75, 115, 153);
    Board board = getBoardInstance();
    JFrame frame = new JFrame();

    Image[] imgs = new Image[12];
    Icon[] imgsIcons = new Icon[12];
    JLabel[][] numberTiles;
    JLabel[][] invicebelLables;
    JPanel panel = new JPanel();

    JPanel menuPanel = new JPanel();
    JButton undoButton = new JButton("Undo");
    JButton resetButton = new JButton("Reset");
    JButton autoBlack = new JButton("AutoBlack");
    JButton devButton = new JButton("Dev");
    JButton autoButton = new JButton("Auto");
    JButton moveButton = new JButton("Move");

    private int srcX=0;
    private int srcY=0;
    private final int blockSize =94;
    private static BoardView viewInstance = null;

    public BoardView() {
    }

    public static BoardView getViewInstance() {
        if (viewInstance ==null){
            viewInstance =new BoardView();
        }
        return viewInstance;
    }

    BufferedImage all;
    {
        try {
            all = ImageIO.read(new File("C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\chess.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initBoardView() {
        frame.setBounds(400,0,752,792);
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

        menuPanel.setPreferredSize(new Dimension(100,40));
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
                tile.setBounds(i*blockSize,j*blockSize,blockSize,blockSize);
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

        //menu Panel and buttons



        menuPanel.add(autoButton);
        autoButton.setPreferredSize(new Dimension(100,30));
        autoButton.addActionListener(actionListener);

        menuPanel.add(moveButton);
        moveButton.setPreferredSize(new Dimension(100,30));
        moveButton.addActionListener(actionListener);

        menuPanel.add(autoBlack);
        autoBlack.setPreferredSize(new Dimension(100,30));
        autoBlack.addActionListener(actionListener);

        menuPanel.add(devButton);
        devButton.setPreferredSize(new Dimension(100,30));
        devButton.addActionListener(actionListener);

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
                Piece piece = board.getBoard().get(i).get(j);
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
                if (piece.getPieceType()==PieceType.KING&&(board.whiteKingIsInCheck&&piece.getColor()== marcus.okodugha.chessv1.Model.Color.WHITE||board.blackKingIsInCheck&&piece.getColor()== marcus.okodugha.chessv1.Model.Color.BLACK)){
                    paintTile(j,i,new Color(153,0,0));//paints kings that are in check red
                }
                white = !white;
            }
            white = !white;
        }
        if (board.getLatestMove()!=null){
            paintTile(board.getLatestMove().srcX,board.getLatestMove().srcY,new Color(255,247,0));//yellow
            paintTile(board.getLatestMove().destX,board.getLatestMove().destY,new Color(204,153,255));//purple

        }
//        //todo rmove test
//        for (Move m:board.testlist) {
//            numberTiles[m.destY][m.destX].setBackground(Color.RED);//todo remove
//        }
    }

    private void showLegalMoves(MouseEvent e){
        int k=0;
        show();
        for (Point p:getBoardUtilitiesInstance().getLegalMovesForPiece(srcX,srcY)) {
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
        public void mouseMoved(MouseEvent e) {       }
    };
    Point activPoint = new Point(-1,-1);

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (activPoint.x!=-1&&activPoint.y!=-1){
                board.movePiece(new Move(activPoint.x,activPoint.y,e.getX()/blockSize,e.getY()/blockSize));
                board.legalMoves.clear();
                show();
                activPoint.x=-1;
                activPoint.y=-1;
            }
            if (activPoint.x==-1&&activPoint.y==-1){
                srcX=e.getX()/blockSize;
                srcY=e.getY()/blockSize;
                show();
                showLegalMoves(e);
                activPoint.x=srcX;
                activPoint.y=srcY;
            }
        }
        @Override
            public void mousePressed(MouseEvent e) {
            srcX=e.getX()/blockSize;
            srcY=e.getY()/blockSize;
            if (board.getBoard().get(srcY).get(srcX).getPieceType()!= PieceType.EMPTY){//selected peice is not empty
                    holdingPieceIcon= imgsIcons[board.getBoard().get(srcY).get(srcX).getImageIndex()];
                    pressPoint = new Point(srcX,srcY);
            }
            // show legalMoves
            show();
            showLegalMoves(e);

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
    }


    ActionListener actionListener = e -> {
        if (e.getSource()==undoButton){
            getBoardUtilitiesInstance().undoMove();
            show();
        }
        if (e.getSource()==resetButton){
            getBoardUtilitiesInstance().resetBoard();
            show();
        }
        if (e.getSource()== autoButton){//toggle autoMove
            autoMove();
        }
        if (e.getSource()==moveButton){
            move();
        }
        if (e.getSource()== devButton){
            DevTools.devMode= !DevTools.devMode;//toggle devMode
            System.out.println("dev mode "+DevTools.devMode);
        }
        if (e.getSource()== autoBlack){
            DevTools.autoBlack=!DevTools.autoBlack;

        }
    };

    private void paintTile(int srcX, int srcY, Color color){
        numberTiles[srcY][srcX].setBackground(color);
    }

    Timer timer;
    public void autoMove() {//todo make privet
        board.autoMove= !board.autoMove;
        if (!board.autoMove){
            timer.cancel();
        }else {
            timer = new Timer();
            timer.schedule(new TimerClass(), 0, 1000);
        }
    }

    private void move() {
        if (board.isWhiteTurn()){
            board.getInfinityWhite().infinityMove();
        }else {
            board.getInfinityBlack().infinityMove();
        }
    }

}