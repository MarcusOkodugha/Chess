package marcus.okodugha.chessv1.Model;

import marcus.okodugha.chessv1.Model.Infinity.Infinity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    public static final int column =8;
    public static final int row = 8;
    public Piece emptyPiece = new Piece(Color.NOCOLOR,PieceType.EMPTY,12);
    public ArrayList<Point> legalMoves = new ArrayList<Point>();
    public ArrayList<Point> allLegalMoves = new ArrayList<Point>();
//    public ArrayList<Point> allLegalBlackMoves = new ArrayList<Point>();
    public ArrayList<Move> allLegalBlackMoves = new ArrayList<Move>();
    public ArrayList<Move> allLegalWhiteMoves = new ArrayList<Move>();

    public ArrayList<ArrayList<ArrayList<Piece>>> gameStateList2 = new ArrayList<>();
    Infinity infinity;
    Infinity infinity2;
    private ArrayList<ArrayList<Piece>> board;

    private Rules rules;
    int nrOfMoves=0;
    public boolean whiteKingIsInCheck;
    public boolean blackKingIsInCheck;
    private boolean gamIsRunning=true;

    public Board() {
        board= new ArrayList<>();
        this.rules = new Rules(this);
        initBoard(board);
        for (int i = 0; i < 270; i++) {
            gameStateList2.add(new ArrayList<>());
            initBoard(gameStateList2.get(i));
        }
        copyAndAdd(board);
        this.infinity = new Infinity(this,Color.WHITE);
        this.infinity2 = new Infinity(this,Color.BLACK);
    }

    public ArrayList<Point> getLegalMoves(int srcX,int srcY,Piece piece) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (rules.isLegalMove(srcX,srcY,j,i,board.get(srcY).get(srcX))){
                    legalMoves.add(new Point(j,i));
                }
            }
        }
        return legalMoves;
    }

    public ArrayList<Point> getAllLegalMoves() {
        whiteKingIsInCheck=false;
        blackKingIsInCheck=false;
        allLegalMoves.clear();
        allLegalWhiteMoves.clear();
        allLegalBlackMoves.clear();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < row; k++) {
                    for (int l = 0; l < column; l++) {
                        if (rules.isLegalMove(j,i,l,k,board.get(i).get(j))){
                            if (rules.kingIsInCheck(l,k)!=emptyPiece.getColor()){
                                if (rules.kingIsInCheck(l,k)==Color.WHITE)whiteKingIsInCheck=true;
                                if (rules.kingIsInCheck(l,k)==Color.BLACK)blackKingIsInCheck=true;
                            }
                            allLegalMoves.add(new Point(l,k));

                            if (board.get(i).get(j).getColor()==Color.WHITE){
                                allLegalWhiteMoves.add(new Move(j,i,l,k));
                            }
                            if (board.get(i).get(j).getColor()==Color.BLACK){
                                allLegalBlackMoves.add(new Move(j,i,l,k));
                            }
                        }
                    }
                }
            }
        }
        return allLegalMoves;
    }



    public void movePiece(int srcX, int srcY, int destX, int destY){

        if (!gamIsRunning)return;

        if (board.get(srcY).get(srcX).getColor()==Color.WHITE&&!isWhiteTurn()){
            System.out.println("not whites turn");
            return;
        }
        if (board.get(srcY).get(srcX).getColor()==Color.BLACK&&isWhiteTurn()){
            System.out.println("not blacks turn");
            return;
        }

        if (!rules.isLegalMove(srcX,srcY,destX,destY,board.get(srcY).get(srcX))){//move not legal
            System.out.println("move not legal");
            return;
        }

        board.get(srcY).get(srcX).setFirstMove(false);
        //castling
        if (rules.destPieceIsSameColor(destX,destY,board.get(srcY).get(srcX))){
            if (srcX<destX){//move is to the right
                board.get(destY).set(destX-1,board.get(srcY).get(srcX));//king
                board.get(destY).set(destX-2,board.get(srcY).get(destX));//rook
                board.get(srcY).set(srcX,emptyPiece);
                board.get(srcY).set(destX,emptyPiece);
                System.out.println("rokad commplet");
            }
            if (destX<srcX){//move is to the left
                board.get(destY).set(destX+2,board.get(srcY).get(srcX));//king
                board.get(destY).set(destX+3,board.get(srcY).get(destX));//rook
                board.get(srcY).set(srcX,emptyPiece);
                board.get(srcY).set(destX,emptyPiece);
                System.out.println("rokad commplet");
            }
        }

        if (rules.pawnPromotion(srcX,srcY,destX,destY,board.get(srcY).get(srcX))){
            board.get(srcY).set(srcX,emptyPiece);

        } else {//normal move
                board.get(destY).set(destX,board.get(srcY).get(srcX));
                board.get(srcY).set(srcX,emptyPiece);
        }
        //every legal move
        nrOfMoves++;

        copyAndAdd(board);
        getAllLegalMoves();


//        if (isWhiteTurn()){
////            getAllLegalMoves();
//            infinity.updateAllLegalAiMoves();
////            getAllLegalMovesForBlack();
//            if (allLegalWhiteMoves.size() == 0){
//                System.out.println("no legal White moves Black Wins!!!!");
//                gamIsRunning=false;
//                return;
//            }
////            infinity.makeRetardedMove();
//            infinity.makeCalculatedMove();
//        }

        if (!isWhiteTurn()){
            infinity2.updateAllLegalAiMoves();
            if (allLegalBlackMoves.size() == 0){
                System.out.println("no legal Black moves White Wins!!!!");
                gamIsRunning=false;
                return;
            }
//            infinity2.makeCalculatedMove();
            infinity2.playOpeningThenCalculatedMoves();
        }

        if (!isWhiteTurn()&&whiteKingIsInCheck||isWhiteTurn()&&blackKingIsInCheck){
//            System.out.println("cant move white king is in check!");
            undoMove();
        }
    }

    public void copyAndAdd(ArrayList<ArrayList<Piece>> inBoard){
        Piece [][] piece = new Piece [row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                piece[i][j] = new Piece();
                piece[i][j]=inBoard.get(i).get(j);
                gameStateList2.get(nrOfMoves).get(i).add(j,piece[i][j]);
            }
        }
    }

    public void undoMove(){
        gamIsRunning =true;
        if (nrOfMoves<=0){
            System.out.println("cant undo board is reset!");
            return;
        }
        nrOfMoves--;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                board.get(i).set(j,gameStateList2.get(nrOfMoves).get(i).get(j));
            }
        }
    }
    public void resetBoard(){
        gamIsRunning =true;
        nrOfMoves=0;
        initBoard(board);
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                board.get(i).set(j,gameStateList2.get(nrOfMoves).get(i).get(j));
                board.get(i).get(j).setFirstMove(true);
            }
        }
        System.out.println("game reset");
    }

    private boolean isWhiteTurn(){
        return nrOfMoves%2==0;
    }

    private void initBoard(ArrayList<ArrayList<Piece>> board){
        for (int i = 0; i < row; i++) {
            board.add(new ArrayList<Piece>());
        }
        //fil board with empty pieces
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j <column; j++) {
                board.get(j).add(new Piece(Color.NOCOLOR,PieceType.EMPTY,12));
            }
        }
        //fil board with pawns
        for (int i = 0; i < row; i++) {
            board.get(1).set(i,new Piece(Color.BLACK,PieceType.PAWN,11));
            board.get(6).set(i,new Piece(Color.WHITE,PieceType.PAWN,5));
        }
        //white back row
        board.get(7).set(0,new Piece(Color.WHITE,PieceType.ROOK,4));
        board.get(7).set(1,new Piece(Color.WHITE,PieceType.KNIGHT,3));
        board.get(7).set(2,new Piece(Color.WHITE,PieceType.BISHOP,2));
        board.get(7).set(3,new Piece(Color.WHITE,PieceType.QUEEN,1));
        board.get(7).set(4,new Piece(Color.WHITE,PieceType.KING,0));
        board.get(7).set(5,new Piece(Color.WHITE,PieceType.BISHOP,2));
        board.get(7).set(6,new Piece(Color.WHITE,PieceType.KNIGHT,3));
        board.get(7).set(7,new Piece(Color.WHITE,PieceType.ROOK,4));
        //black back row
        board.get(0).set(0,new Piece(Color.BLACK,PieceType.ROOK,10));
        board.get(0).set(1,new Piece(Color.BLACK,PieceType.KNIGHT,9));
        board.get(0).set(2,new Piece(Color.BLACK,PieceType.BISHOP,8));
        board.get(0).set(3,new Piece(Color.BLACK,PieceType.QUEEN,7));
        board.get(0).set(4,new Piece(Color.BLACK,PieceType.KING,6));
        board.get(0).set(5,new Piece(Color.BLACK,PieceType.BISHOP,8));
        board.get(0).set(6,new Piece(Color.BLACK,PieceType.KNIGHT,9));
        board.get(0).set(7,new Piece(Color.BLACK,PieceType.ROOK,10));

    }


    public void showBoardInTerminal(Board board){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.format("%-15s",board.board.get(i).get(j));
            }
            System.out.println("\n");
        }
    }
    public ArrayList<ArrayList<Piece>> getBoard() {
        return board;
    }
}
