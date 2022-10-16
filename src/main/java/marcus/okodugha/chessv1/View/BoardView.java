package marcus.okodugha.chessv1.View;
import marcus.okodugha.chessv1.Model.*;
import marcus.okodugha.chessv1.Model.Infinity.Infinity;
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
    Color lightBlueColor = new Color(235, 233,210);
    Color beige = new Color(75, 115, 153);
    Board board;
    Infinity infinity;
    Infinity infinity2;
    boolean flipBlack =false,flipWhite = false;
    JFrame frame = new JFrame();
    BufferedImage all = ImageIO.read(new File("C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\chess.png"));
    Image imgs[] = new Image[12];
    Icon imgsIcons[] = new Icon[12];
    JLabel[][] numberTiles;
    JLabel[][] invicebelLables;
    JPanel panel = new JPanel();
//    JLayeredPane panel = new JLayeredPane();

    JPanel menuPanel = new JPanel();
    JButton undoButton = new JButton("Undo");
    JButton resetButton = new JButton("Reset");
    JButton infinityButton = new JButton("Infinity");
    JButton button1 = new JButton("1");
    JButton button2 = new JButton("2");
    JButton button3 = new JButton("3");

    int srcX=0;
    int srcY=0;
    private final int blockSize =94;




    public BoardView(Board board) throws IOException {
        this.board = board;
        initBoardView();
        this.infinity = infinity;
        this.infinity2 = infinity2;

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

        menuPanel.add(button2);
        button2.setPreferredSize(new Dimension(100,30));
        button2.addActionListener(actionListener);

        menuPanel.add(button3);
        button3.setPreferredSize(new Dimension(100,30));
        button3.addActionListener(actionListener);

        menuPanel.add(infinityButton);
        infinityButton.setPreferredSize(new Dimension(100,30));
        infinityButton.addActionListener(actionListener);

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
    }


    private void showLegalMoves(MouseEvent e){
        int k=0;

        for (Point p:board.getLegalMoves(srcX,srcY,board.getBoard().get(srcY).get(srcX))) {
            panel.repaint();
            if (holdingPieceIcon!=null){

                paintOnMouse(holdingPieceIcon,e);
            }
            if (numberTiles[(board.legalMoves.get(k).y)][(board.legalMoves.get(k).x)].getIcon()==null){
                numberTiles[board.legalMoves.get(k).y][board.legalMoves.get(k).x].setBackground(Color.GREEN);
            }else {
                numberTiles[board.legalMoves.get(k).y][board.legalMoves.get(k).x].setBackground(Color.RED);
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
        public void mouseMoved(MouseEvent e) {

        }
    };
    Point activPoint = new Point(-1,-1);

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

            if (activPoint.x!=-1&&activPoint.y!=-1){//second click
//                System.out.println("second click");
                board.movePiece(activPoint.x,activPoint.y,e.getX()/blockSize,e.getY()/blockSize);
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

        }
        @Override
        public void mouseReleased(MouseEvent e) {
            holdingPieceIcon=null;//reset holdigPieceIcon when mouse is released
            pressPoint=null;
            if (srcX==e.getX()/blockSize&&srcY==e.getY()/blockSize){//keeps showing legalmoves if mouse is released on srcX srcY
            showLegalMoves(e);
            }else {
                board.movePiece(srcX,srcY,e.getX()/blockSize,e.getY()/blockSize);
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
            if (e.getSource()==infinityButton){
                infinity.makeCalculatedMove();
                show();
            }
            if (e.getSource()==button1){

                show();
            }
            if (e.getSource()==button2){
                infinity2.makeCalculatedMove();
                show();
            }
            if (e.getSource()==button3){
                show();
            }
        }
    };


}