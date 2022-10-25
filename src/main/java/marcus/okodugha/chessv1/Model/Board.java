package marcus.okodugha.chessv1.Model;

import javafx.animation.AnimationTimer;
import marcus.okodugha.chessv1.Model.Infinity.Eval;
import marcus.okodugha.chessv1.Model.Infinity.Infinity;
import marcus.okodugha.chessv1.View.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Board {
    public static final int column =8;
    public static final int row = 8;
    private final int maxGameStates=900;
    public Piece emptyPiece = new Piece(Color.NOCOLOR,PieceType.EMPTY,12);
    public ArrayList<Point> legalMoves = new ArrayList<>();
    public ArrayList<Move> allLegalMoves = new ArrayList<>();
    public ArrayList<Move> testlist = new ArrayList<>();
    public ArrayList<Move> allLegalBlackMoves = new ArrayList<>();
    public ArrayList<Move> allLegalWhiteMoves = new ArrayList<>();
    public boolean[][] blackAttacks =new boolean[row][column];
    public boolean[][] whiteAttacks =new boolean[row][column];
    public boolean autoMove=false;

    Eval eval = new Eval(this);
    public ArrayList<ArrayList<ArrayList<Piece>>> gameStateList2 = new ArrayList<>();
    Infinity infinityWhite;
    Infinity infinityBlack;
    private ArrayList<ArrayList<Piece>> board;
    private Move latestMove;
    public ArrayList<ArrayList<Piece>> boardAfterMove=new ArrayList<>();
    private Rules rules;
    int nrOfMoves=0;
    public boolean whiteKingIsInCheck;
    public boolean blackKingIsInCheck;
    public boolean whiteKingIsInCheck2;
    public boolean blackKingIsInCheck2;
    public boolean gamIsRunning=true;

    public Board() {
        board= new ArrayList<>();
        this.rules = new Rules(this);
        initBoard(board);
        for (int i = 0; i < maxGameStates; i++) {
            gameStateList2.add(new ArrayList<>());
            initBoard(gameStateList2.get(i));
        }
        copyAndAdd(board);
        this.infinityWhite = new Infinity(this,Color.WHITE);
        this.infinityBlack = new Infinity(this,Color.BLACK);
    }

    public ArrayList<Point> getLegalMovesForPiece(int srcX, int srcY){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (rules.isLegalMove(srcX,srcY,j,i,board.get(srcY).get(srcX))){
                    if (listContainsMove(allLegalMoves,new Move(srcX,srcY,j,i))){
                        legalMoves.add(new Point(j,i));
                    }
                }
            }
        }
        return legalMoves;
    }

    public ArrayList<Move> getAllLegalMoves(){
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
                            if (quickMove(new Move(j,i,l,k))){
                                allLegalMoves.add(new Move(j,i,l,k));
                                if (board.get(i).get(j).getColor()==Color.WHITE){
                                    allLegalWhiteMoves.add(new Move(j,i,l,k));
                                    whiteAttacks[l][k]=true;
                                    testlist.add(new Move(j,i,l,k));
                                }else {whiteAttacks[l][k]=false;}
                                if (board.get(i).get(j).getColor()==Color.BLACK){
                                    allLegalBlackMoves.add(new Move(j,i,l,k));
                                    blackAttacks[l][k]=true;
                                }else {blackAttacks[l][k]=false;}
                            }
                        }
                    }
                }
            }
        }
        return allLegalMoves;
    }


    public void getAllLegalMoves2(){
        whiteKingIsInCheck2=false;
        blackKingIsInCheck2=false;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < row; k++) {
                    for (int l = 0; l < column; l++) {
                        if (rules.isLegalMove(j,i,l,k,board.get(i).get(j))){
                            if (rules.kingIsInCheck(l,k)!=emptyPiece.getColor()){
                                if (rules.kingIsInCheck(l,k)==Color.WHITE)whiteKingIsInCheck2=true;
                                if (rules.kingIsInCheck(l,k)==Color.BLACK)blackKingIsInCheck2=true;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean quickMove(Move move){
        int srcX= move.srcX; int srcY= move.srcY; int destX= move.destX; int destY= move.destY;
        Piece srcPiece; Piece destPiece; srcPiece=board.get(srcY).get(srcX); destPiece=board.get(destY).get(destX);

        handelMoveType(srcX, srcY, destX, destY);

        getAllLegalMoves2();

        revertMove(srcX, srcY, destX, destY, srcPiece, destPiece);

        if (board.get(srcY).get(srcX).getColor()==Color.WHITE&&whiteKingIsInCheck2||board.get(srcY).get(srcX).getColor()==Color.BLACK&&blackKingIsInCheck2){//your king will be in check
            return false;
        }
        return true;
    }

    private void revertMove(int srcX, int srcY, int destX, int destY, Piece srcPiece, Piece destPiece) {
        if (rules.destPieceIsSameColor(destX, destY,board.get(srcY).get(srcX))){
            board.get(destY).set(destX, destPiece);
            board.get(srcY).set(srcX, srcPiece);
            if (srcX < destX){//move is to the right
                board.get(destY).set(destX -1,emptyPiece);//king
                board.get(destY).set(destX -2,emptyPiece);//rook
            }
            if (destX < srcX){//move is to the left
                board.get(destY).set(destX +2,emptyPiece);//king
                board.get(destY).set(destX +3,emptyPiece);//rook
            }
        }
        board.get(destY).set(destX, destPiece);
        board.get(srcY).set(srcX, srcPiece);
    }

    public void movePiece(Move move)  {
        int srcX= move.srcX; int srcY= move.srcY; int destX= move.destX; int destY= move.destY;
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
        if (!listContainsMove(allLegalMoves,move)){
            System.out.println("list did not contain move move therfore not legal");
            return;
        }
        latestMove=move;
        board.get(srcY).get(srcX).setFirstMove(false);

        handelMoveType(srcX, srcY, destX, destY);
        Sound.makeSound();
//        eval.getEval();
        nrOfMoves++;
        copyAndAdd(board);
        autoMove();


//            infinityWhite.infinityMove();
//            infinityBlack.infinityMove();


    }

    public void autoMove(){
        if (!autoMove){
            return;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        infinityWhite.infinityMove();
        infinityBlack.infinityMove();
//            if (isWhiteTurn()){ board.getInfinityWhite().infinityMove();
//            }else {board.getInfinityBlack().infinityMove();}

    }

    private void handelMoveType(int srcX, int srcY, int destX, int destY) {
        if (rules.destPieceIsSameColor(destX,destY,board.get(srcY).get(srcX))){
            if (srcX<destX){//move is to the right
                board.get(destY).set(destX-1,board.get(srcY).get(srcX));//king
                board.get(destY).set(destX-2,board.get(srcY).get(destX));//rook
                board.get(srcY).set(srcX,emptyPiece);
                board.get(srcY).set(destX,emptyPiece);
            }
            if (destX<srcX){//move is to the left
                board.get(destY).set(destX+2,board.get(srcY).get(srcX));//king
                board.get(destY).set(destX+3,board.get(srcY).get(destX));//rook
                board.get(srcY).set(srcX,emptyPiece);
                board.get(srcY).set(destX,emptyPiece);
            }
        }
        if (rules.pawnPromotion(srcX,srcY,destX,destY,board.get(srcY).get(srcX))){
            board.get(srcY).set(srcX,emptyPiece);

        } else {//normal move
                board.get(destY).set(destX,board.get(srcY).get(srcX));
                board.get(srcY).set(srcX,emptyPiece);
        }
    }

    public void copyAndAdd(ArrayList<ArrayList<Piece>> inBoard){

        if (nrOfMoves==maxGameStates){
            System.out.println("stopped playing due to max move limit nr of moves :"+nrOfMoves);
            gamIsRunning =false;
            return;
        }

        Piece [][] piece = new Piece [row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                piece[i][j] = new Piece();
                piece[i][j]=inBoard.get(i).get(j);
                gameStateList2.get(nrOfMoves).get(i).add(j,piece[i][j]);
            }
        }
    }
    public void copyBoard(ArrayList<ArrayList<Piece>> fromBoard,ArrayList<ArrayList<Piece>> tooBoard){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                tooBoard.get(i).set(i,fromBoard.get(i).get(j));
            }
        }
        System.out.println("copy board done");
//        show2DListInTerminal(tooBoard);
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
    public boolean isWhiteTurn(){
        return nrOfMoves%2==0;
    }
    private void initBoard(ArrayList<ArrayList<Piece>> board){
        for (int i = 0; i < row; i++) {//init boardAftermove
            boardAfterMove.add(new ArrayList<Piece>());
        }
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j <column; j++) {
                boardAfterMove.get(j).add(new Piece(Color.NOCOLOR,PieceType.EMPTY,12));
            }
        }
        //add arraylists to board
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
//        //white back row
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

        //todo remove test board
//        board.get(4).set(3,new Piece(Color.WHITE,PieceType.QUEEN,1));
//        board.get(1).set(3,new Piece(Color.BLACK,PieceType.QUEEN,7));

    }

    public void show2DListInTerminal(ArrayList<ArrayList<Piece>> tooBoard){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.format("%-15s",tooBoard.get(i).get(j));
            }
            System.out.println("\n");
        }
    }
    public ArrayList<ArrayList<Piece>> getBoard() {
        return board;
    }

    public Infinity getInfinityWhite() {
        return infinityWhite;
    }

    public Infinity getInfinityBlack() {
        return infinityBlack;
    }

    public Move getLatestMove() {
        return latestMove;
    }
    public boolean listContainsMove(ArrayList<Move> list, Move move){
        if ((list==null)||(move==null)){ System.out.println("false");return false;}
        for (Move m:list) {
            if (m.srcX==move.srcX&&m.srcY==move.srcY&&m.destX==move.destX&&m.destY==move.destY){
                return true;
            }
        }
        return false;
    }
    public boolean listContainsPoint(ArrayList<Move> list, Move move){
        if ((list==null)||(move==null))return false;
        for (Move m:list) {
            if (m.destX==move.destX&&m.destY==move.destY){
                return true;
            }
        }
        return false;
    }

}
