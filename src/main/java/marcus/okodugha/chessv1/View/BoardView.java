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
import java.util.Timer;

import static marcus.okodugha.chessv1.Model.Board.*;
import static marcus.okodugha.chessv1.Model.BordUtilities.getBoardUtilitiesInstance;

public class BoardView  {
    private Board board = getBoardInstance();
    private JFrame frame = new JFrame();
    private JLayeredPane panel = new JLayeredPane();
    private static BoardView viewInstance = null;

    private BoardView() {
    }

    public static BoardView getViewInstance() {
        if (viewInstance ==null){
            viewInstance =new BoardView();
        }
        return viewInstance;
    }
    JPanel labelHoldingPanel = new JPanel(new FlowLayout());
    boolean promotionPromptIsActive;
    JOptionPane jOptionPane = new JOptionPane();

    public void promotionPrompt(marcus.okodugha.chessv1.Model.Color color){
        promotionPromptIsActive=true;
        JLabel knightLabel = null;
        JLabel bishopLabel= null;
        JLabel rookLabel= null;
        JLabel queenLabel =null;
        if (color== marcus.okodugha.chessv1.Model.Color.WHITE){
           knightLabel = new JLabel(imageIcons[3]);//white knight
           bishopLabel = new JLabel(imageIcons[2]);//white bishop
           rookLabel = new JLabel(imageIcons[4]);//white rook
           queenLabel = new JLabel(imageIcons[1]);//white queen
        }
        if (color== marcus.okodugha.chessv1.Model.Color.BLACK){
           knightLabel = new JLabel(imageIcons[9]);//black knight
           bishopLabel = new JLabel(imageIcons[8]);//black bishop
           rookLabel = new JLabel(imageIcons[10]);//black rook
           queenLabel = new JLabel(imageIcons[7]);//black queen
        }
        labelHoldingPanel.add(knightLabel);
        labelHoldingPanel.add(bishopLabel);
        labelHoldingPanel.add(rookLabel);
        labelHoldingPanel.add(queenLabel);

        jOptionPane.add(labelHoldingPanel);
        labelHoldingPanel.addMouseListener(mouseListener);
//        jOptionPane.showOptionDialog(frame,labelHoldingPanel , "Promotion!",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,null,new Object[]{},null);
        jOptionPane.showConfirmDialog(frame,labelHoldingPanel);

        labelHoldingPanel.remove(knightLabel);
        labelHoldingPanel.remove(bishopLabel);
        labelHoldingPanel.remove(rookLabel);
        labelHoldingPanel.remove(queenLabel);
        promotionPromptIsActive =false;

    }



    private void handlePromotionClick(MouseEvent e){
        if (!promotionPromptIsActive)return;
        System.out.println("handel promotion click");
        Rectangle rectangle = new Rectangle(((e.getX())/blockSize)*blockSize,((e.getY())/blockSize)*blockSize,blockSize,blockSize);
        Rectangle knightRect = new Rectangle(0,0,blockSize,blockSize);
        Rectangle bishopRect = new Rectangle(blockSize,0,blockSize,blockSize);
        Rectangle rookRect = new Rectangle((blockSize * 2),0,blockSize,blockSize);
        Rectangle queenRect = new Rectangle((blockSize * 3),0,blockSize,blockSize);

        int blackImageIndexAdder=0;
        marcus.okodugha.chessv1.Model.Color color = getBoardInstance().getTurnColor();
        if (color== marcus.okodugha.chessv1.Model.Color.BLACK)blackImageIndexAdder=6;
        if (knightRect.equals(rectangle)) {
            getBoardUtilitiesInstance().setPiece(new Piece(color,PieceType.KNIGHT,3+blackImageIndexAdder));
        }
        if (bishopRect.equals(rectangle)) {
            getBoardUtilitiesInstance().setPiece(new Piece(color,PieceType.BISHOP,2+blackImageIndexAdder));
        }
        if (rookRect.equals(rectangle)){
            getBoardUtilitiesInstance().setPiece(new Piece(color,PieceType.ROOK,4+blackImageIndexAdder));
        }
        if (queenRect.equals(rectangle)){
            getBoardUtilitiesInstance().setPiece(new Piece(color,PieceType.QUEEN,1+blackImageIndexAdder));
        }
        System.out.println("after handel click");

    }

    public void initBoardView() {
        BufferedImage piecesImage = initBufferedImage("C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\chess.png");//init piece image map
        ImageIcon goldenBoarderImg = initImageIcons("C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\goldenBorder90px.png");//init boarder
        frame.setBounds(400,10,(blockSize*8)+13,(blockSize*8)+76);
        frame.setLayout(new BorderLayout());
        numberTiles=new JLabel[row][column];
        creatSubImages(piecesImage);


        menuPanel.setPreferredSize(new Dimension(100,40));
        boolean white = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel tile = new JLabel();
                if (white) {
                    tile.setBackground(beige);
                } else {
                    tile.setBackground(lightBlueColor);
                }
                tile.setBounds(j*blockSize,i*blockSize,blockSize,blockSize);
                if(board.getBoard().get(i).get(j).getImageIndex() != 12){
                    Icon icon = imageIcons[board.getBoard().get(i).get(j).getImageIndex()];
                    tile.setIcon(icon);
                }
                numberTiles[i][j]=tile;
                numberTiles[i][j].setOpaque(true);
                panel.add(numberTiles[i][j]);
                white = !white;
            }
            white = !white;
        }
        dynamicTile.setBounds(0,0,blockSize,blockSize);
        panel.add(dynamicTile,JLayeredPane.DRAG_LAYER);
        boarderTile.setBounds(0,0,blockSize,blockSize);
        boarderTile.setIcon(goldenBoarderImg);
        boarderTile.setOpaque(false);
        panel.add(boarderTile,JLayeredPane.POPUP_LAYER);
        initButtonsAndMenu();//menu Panel and buttons
        frame.add(menuPanel,BorderLayout.SOUTH);//frame
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
                    numberTiles[i][j].setIcon(imageIcons[board.getBoard().get(i).get(j).getImageIndex()]);
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
            paintTile(board.getLatestMove().srcX,board.getLatestMove().srcY,new Color(250, 246,122));//yellow
//            250, 246,122
//            paintTile(board.getLatestMove().destX,board.getLatestMove().destY,new Color(204,153,255));//purple
            paintTile(board.getLatestMove().destX,board.getLatestMove().destY,new Color(247, 244, 131));//slightliy darker yellow

        }
//        //todo rmove test
//        for (Move m:board.testlist) {
//            numberTiles[m.destY][m.destX].setBackground(Color.RED);//todo remove
//        }

    }

    private void showLegalMovesForPiece(MouseEvent e){
        int k=0;
        show();
        for (Point p:getBoardUtilitiesInstance().getLegalMovesForPiece(srcX,srcY)) {
            panel.repaint();
            if (holdingPieceIcon!=null){
                paintIconOnMouse(holdingPieceIcon,e);
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
        dynamicTile.setIcon(null);
    }

    MouseMotionListener mouseMotionListener = new MouseMotionListener() {@Override
        public void mouseDragged(MouseEvent e) {
            paintIconOnMouse(holdingPieceIcon,e);
            paintBoarderOnMouse(e);
            numberTiles[srcY][srcX].setIcon(null);

        }@Override
        public void mouseMoved(MouseEvent e) {paintBoarderOnMouse(e);}
    };

    MouseListener mouseListener = new MouseListener() {@Override
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
                showLegalMovesForPiece(e);
                activPoint.x=srcX;
                activPoint.y=srcY;
            }
        handlePromotionClick(e);
        }@Override
            public void mousePressed(MouseEvent e) {
            srcX=e.getX()/blockSize;
            srcY=e.getY()/blockSize;
            if (board.getBoard().get(srcY).get(srcX).getPieceType()!= PieceType.EMPTY){//selected peice is not empty
                    holdingPieceIcon= imageIcons[board.getBoard().get(srcY).get(srcX).getImageIndex()];
            }
            // show legalMoves
            show();
            showLegalMovesForPiece(e);
        }@Override
        public void mouseReleased(MouseEvent e) {
            holdingPieceIcon=null;//reset holdigPieceIcon when mouse is released
            if (srcX==e.getX()/blockSize&&srcY==e.getY()/blockSize){//keeps showing legalmoves if mouse is released on srcX srcY
                showLegalMovesForPiece(e);
            }else {
                board.movePiece(new Move(srcX,srcY,e.getX()/blockSize,e.getY()/blockSize));
                dynamicTile.setIcon(null);
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

    private void paintIconOnMouse(Icon icon, MouseEvent e){
        dynamicTile.setIcon(icon);
        dynamicTile.setBounds(e.getX()-xMouseOffset,e.getY()-yMouseOffset,blockSize,blockSize);

    }
    private void paintBoarderOnMouse(MouseEvent e){
        boarderTile.setBounds(((e.getX())/blockSize)*blockSize,((e.getY()-10)/blockSize)*blockSize,blockSize,blockSize);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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
                System.out.println("auto black "+DevTools.autoBlack);
            }
        }
    };

    private void paintTile(int srcX, int srcY, Color color){
        numberTiles[srcY][srcX].setBackground(color);
    }

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
    //Utility data
    private Color beige = new Color(235, 233,210);
    private Color lightBlueColor = new Color(75, 115, 153);
    private Icon[] imageIcons = new Icon[12];
    private JLabel[][] numberTiles;
    private JLabel dynamicTile = new JLabel();
    private JLabel boarderTile = new JLabel();
    private JPanel menuPanel = new JPanel();
    private JButton undoButton = new JButton("Undo");
    private JButton resetButton = new JButton("Reset");
    private JButton autoBlack = new JButton("AutoBlack");
    private JButton devButton = new JButton("Dev");
    private JButton autoButton = new JButton("Auto");
    private JButton moveButton = new JButton("Move");
    private int srcX=0;
    private int srcY=0;
    private final int blockSize =90;
    private int xMouseOffset =blockSize/2;
    private int yMouseOffset=blockSize/2;
    private Point activPoint = new Point(-1,-1);
    private Icon holdingPieceIcon = null;
    private Timer timer;






    //Utility methods
    private ImageIcon initImageIcons(String pathname)  {
        try {
            BufferedImage image = ImageIO.read(new File(pathname));
            return new ImageIcon(image);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private BufferedImage initBufferedImage(String pathname){
        try {
            return ImageIO.read(new File(pathname));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private void creatSubImages(BufferedImage piecesImage) {
        int ind = 0;
        for (int i = 0; i < 400; i += 200) {
            for (int j = 0; j < 1200; j += 200) {
                imageIcons[ind] = new ImageIcon(piecesImage.getSubimage(j, i, 200, 200).getScaledInstance(blockSize,blockSize, BufferedImage.SCALE_SMOOTH));
                ind++;
            }
        }
    }

    private void initButtonsAndMenu() {
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
    }
}
