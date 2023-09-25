package marcus.okodugha.chessv1.Model;

import marcus.okodugha.chessv1.Model.Infinity.Eval;
import marcus.okodugha.chessv1.Model.Infinity.Infinity;
import marcus.okodugha.chessv1.View.Sound;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static marcus.okodugha.chessv1.Model.BordUtilities.getBoardUtilitiesInstance;
import static marcus.okodugha.chessv1.View.BoardView.getViewInstance;

public class Board {
    public static final int column =8;
    public static final int row = 8;
    public final int maxGameStates=900;
    public static Piece emptyPiece = new Piece(Color.NOCOLOR,PieceType.EMPTY,12);
    public ArrayList<Point> legalMoves = new ArrayList<>();
    public ArrayList<Move> allLegalMoves = new ArrayList<>();
    public ArrayList<Move> testlist = new ArrayList<>();
    public ArrayList<Move> allLegalBlackMoves = new ArrayList<>();
    public ArrayList<Move> allLegalWhiteMoves = new ArrayList<>();
    public boolean[][] blackAttacks =new boolean[row][column];
    public boolean[][] whiteAttacks =new boolean[row][column];
    public boolean autoMove=false;

    Eval eval = new Eval();
    public ArrayList<ArrayList<ArrayList<Piece>>> gameStateList2 = new ArrayList<>();
    Infinity infinityWhite;
    Infinity infinityBlack;
//    private ArrayList<ArrayList<Piece>> board;
    public Move latestMove;
    public ArrayList<ArrayList<Piece>> boardAfterMove=new ArrayList<>();
    private Rules rules;
    public int nrOfMoves=0;
    public boolean whiteKingIsInCheck;
    public boolean blackKingIsInCheck;
    public boolean whiteKingIsInCheck2;
    public boolean blackKingIsInCheck2;
    public boolean gamIsRunning=true;
    public static Board bordInstance;

    public static Board getBoardInstance(){
        if (bordInstance ==null){
            bordInstance =new Board();
        }
        return bordInstance;
    }

    public void initBoard(){
        board= new ArrayList<>();
        this.rules = new Rules(this);
        initList(board);
        for (int i = 0; i < maxGameStates; i++) {
            gameStateList2.add(new ArrayList<>());
            initList(gameStateList2.get(i));
        }
        getBoardUtilitiesInstance().copyAndAdd(board);
        this.infinityWhite = new Infinity(this,Color.WHITE);
        this.infinityBlack = new Infinity(this,Color.BLACK);
//        getAllLegalMoves();
    }

    public void movePiece(Move move) {
        int srcX= move.srcX; int srcY= move.srcY; int destX= move.destX; int destY= move.destY;
        if (!gamIsRunning)return;
        if (!DevTools.devMode) {//todo remve outer if leav iner if
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
        }
        latestMove=move;
        Piece destPiece = board.get(move.destY).get(move.destX);
        board.get(srcY).get(srcX).setFirstMove(false);

        getBoardUtilitiesInstance().handelMoveType(move,true);
        eval.getEval(board);
        nrOfMoves++;
        getBoardUtilitiesInstance().copyAndAdd(board);

        if (DevTools.autoBlack){
//            Timer timer = new Timer();//todo impliment deley
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    infinityBlack.infinityMove();
//                }
//            }, 1000, 1000);
////            timer.cancel();
            infinityBlack.infinityMove();//original
        }



        getAllLegalMoves();
        Sound.makeSound(destPiece);
        getViewInstance().show();

            System.out.println(getTurnColor());

    }
    public ArrayList<Move> getAllLegalMoves(Board board){//Heltekande funktion för att retunera alla lagliga moves
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
                            if (getBoardUtilitiesInstance().quickMove(new Move(j,i,l,k),board)){
                                allLegalMoves.add(new Move(j,i,l,k));
                                if (board.get(i).get(j).getColor()==Color.WHITE){
                                    allLegalWhiteMoves.add(new Move(j,i,l,k));
                                    whiteAttacks[l][k]=true;
                                    testlist.add(new Move(j,i,l,k));//todo remove
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

    public boolean isWhiteTurn()
        {
        return nrOfMoves%2==0;
    }
    public Color getTurnColor()
    {
        if (nrOfMoves%2==0)return Color.WHITE;
        return Color.BLACK;
    }


    public void initList(ArrayList<ArrayList<Piece>> board){
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
//        board.get(3).set(3,new Piece(Color.BLACK,PieceType.KING,6));

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

    public void setLatestMove(Move latestMove) {
        this.latestMove = latestMove;
    }

    public boolean listContainsMove(ArrayList<Move> list, Move move){
        if ((list==null)||(move==null)){return false;}
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
