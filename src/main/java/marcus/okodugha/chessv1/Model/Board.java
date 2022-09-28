package marcus.okodugha.chessv1.Model;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    public static final int column =8;
    public static final int row = 8;
    private ArrayList<ArrayList<Piece>> board;
    private Piece emptyPiece = new Piece(Color.NOCOLOR,PieceType.EMPTY,12);
    Rules rules;
    static int nrOfMoves=0;
    public ArrayList<Point> legalMoves = new ArrayList<Point>();



    public Board() {
        board= new ArrayList<>();
        this.rules = new Rules(this);
        initBoard();
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

    public void movePiece(int srcX,int srcY,int destX,int destY){
        if (rules.isLegalMove(srcX,srcY,destX,destY,board.get(srcY).get(srcX))){
            board.get(srcY).get(srcX).firstMove=false;
            board.get(destY).set(destX,board.get(srcY).get(srcX));
            board.get(srcY).set(srcX,emptyPiece);
            nrOfMoves++;
            System.out.println("nr of moves: "+nrOfMoves);
        }else {
            System.out.println("illegal move");
        }
    }


    private void initBoard(){
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


    public void showBoardInTerminal(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.format("%-15s",board.get(i).get(j));
            }
            System.out.println("\n");
        }
    }
    public ArrayList<ArrayList<Piece>> getBoard() {
        return board;
    }
}
