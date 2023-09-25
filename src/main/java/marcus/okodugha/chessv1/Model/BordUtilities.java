package marcus.okodugha.chessv1.Model;

import java.awt.*;
import java.util.ArrayList;

import static marcus.okodugha.chessv1.Model.Board.*;
import static marcus.okodugha.chessv1.View.BoardView.getViewInstance;

public class BordUtilities {

    public static BordUtilities bordUtilitiesInstance;

    public BordUtilities() {
    }
       public static BordUtilities getBoardUtilitiesInstance(){
        if (bordUtilitiesInstance ==null){
            bordUtilitiesInstance =new BordUtilities();
        }
        return bordUtilitiesInstance;
    }
    public void simulateNMoves(int n){
        //Här vill jag för varje lagliga move genomföra det movet.
        //Sedan vill jag kolla varje lagliga move efter det movet.
        //Sedan vill jag göra varje lagliga move från den nya utgångspunket osv till ett djup av n.
        //Sedan vill jag retunera det bästa första movet som ger mig högs EVAL efer n moves
        Rules rules = new Rules(getBoardInstance());
        Board board = getBoardInstance();
        board.getAllLegalMoves();
        for (Move m1: board.getAllLegalMoves()) {
            for (Move m2:quickMoveGetNewList(m1)) {

            };

        }

    }
    public ArrayList<Point> getLegalMovesForPiece(int srcX, int srcY){
        Rules rules = new Rules(getBoardInstance());
        Board board= getBoardInstance();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (rules.isLegalMove(srcX,srcY,j,i,board.getBoard().get(srcY).get(srcX))){
                    if (board.listContainsMove(board.allLegalMoves,new Move(srcX,srcY,j,i))){
                        board.legalMoves.add(new Point(j,i));
                    }
                }
            }
        }
        return board.legalMoves;
    }


    public void getAllLegalMoves2(){
        Rules rules = new Rules(getBoardInstance());
        Board board= getBoardInstance();

        board.whiteKingIsInCheck2=false;
        board.blackKingIsInCheck2=false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < row; k++) {
                    for (int l = 0; l < column; l++) {
                        if (rules.isLegalMove(j,i,l,k,board.getBoard().get(i).get(j))){
                            if (rules.kingIsInCheck(l,k)!=board.emptyPiece.getColor()){
                                if (rules.kingIsInCheck(l,k)== Color.WHITE)board.whiteKingIsInCheck2=true;
                                if (rules.kingIsInCheck(l,k)==Color.BLACK)board.blackKingIsInCheck2=true;
                            }
                        }
                    }
                }
            }
        }
    }

    public void revertMove(int srcX, int srcY, int destX, int destY, Piece srcPiece, Piece destPiece) {
        Rules rules = new Rules(getBoardInstance());
        Board board= getBoardInstance();
        if (rules.destPieceIsSameColor(destX, destY,board.getBoard().get(srcY).get(srcX))){
            board.getBoard().get(destY).set(destX, destPiece);
            board.getBoard().get(srcY).set(srcX, srcPiece);
            if (srcX < destX){//move is to the right
                board.getBoard().get(destY).set(destX -1,board.emptyPiece);//king
                board.getBoard().get(destY).set(destX -2,board.emptyPiece);//rook
            }
            if (destX < srcX){//move is to the left
                board.getBoard().get(destY).set(destX +2,board.emptyPiece);//king
                board.getBoard().get(destY).set(destX +3,board.emptyPiece);//rook
            }
        }
        board.getBoard().get(destY).set(destX, destPiece);
        board.getBoard().get(srcY).set(srcX, srcPiece);
    }

    public boolean quickMove(Move move,Board board){
//        Board board= getBoardInstance();

        int srcX= move.srcX; int srcY= move.srcY; int destX= move.destX; int destY= move.destY;
        Piece srcPiece; Piece destPiece; srcPiece=board.getBoard().get(srcY).get(srcX); destPiece=board.getBoard().get(destY).get(destX);

        handelMoveType(move,false);

        getBoardUtilitiesInstance().getAllLegalMoves2();

        getBoardUtilitiesInstance().revertMove(srcX, srcY, destX, destY, srcPiece, destPiece);

        if (board.getBoard().get(srcY).get(srcX).getColor()==Color.WHITE&&board.whiteKingIsInCheck2||board.getBoard().get(srcY).get(srcX).getColor()==Color.BLACK&&board.blackKingIsInCheck2){//your king will be in check
            return false;
        }
        return true;
    }

    public ArrayList<Move> quickMoveGetNewList(Move move){
        //här vill jag ha en lista med moves efter att jag genomfört movet
        Board boardCopy = new Board();


    }

    public void handelMoveType(Move move,boolean realMove) {
        int srcX= move.srcX; int srcY= move.srcY; int destX= move.destX; int destY= move.destY;
        Rules rules = new Rules(getBoardInstance());
        Board board= getBoardInstance();
        if (rules.destPieceIsSameColor(destX,destY,board.getBoard().get(srcY).get(srcX))){
            if (srcX<destX){//move is to the right
                board.getBoard().get(destY).set(destX-1,board.getBoard().get(srcY).get(srcX));//king
                board.getBoard().get(destY).set(destX-2,board.getBoard().get(srcY).get(destX));//rook
                board.getBoard().get(srcY).set(srcX,board.emptyPiece);
                board.getBoard().get(srcY).set(destX,board.emptyPiece);
            }
            if (destX<srcX){//move is to the left
                board.getBoard().get(destY).set(destX+2,board.getBoard().get(srcY).get(srcX));//king
                board.getBoard().get(destY).set(destX+3,board.getBoard().get(srcY).get(destX));//rook
                board.getBoard().get(srcY).set(srcX,board.emptyPiece);
                board.getBoard().get(srcY).set(destX,board.emptyPiece);
            }
        }
        if (rules.pawnPromotion(move,board.getBoard().get(srcY).get(srcX))){
            if (realMove){//real move is only true when called from movePiece never quickMove
                getViewInstance().promotionPrompt(board.getBoard().get(srcY).get(srcX).getColor());
                board.getBoard().get(srcY).set(srcX,board.emptyPiece);
            }
        } else {//normal move
            board.getBoard().get(destY).set(destX,board.getBoard().get(srcY).get(srcX));
            board.getBoard().get(srcY).set(srcX,board.emptyPiece);
        }
    }
    public void copyAndAdd(ArrayList<ArrayList<Piece>> inBoard){
        Board board= getBoardInstance();
        if (board.nrOfMoves==board.maxGameStates){
            System.out.println("stopped playing due to max move limit nr of moves :"+board.nrOfMoves);
            board.gamIsRunning =false;
            return;
        }

        Piece [][] piece = new Piece [row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                piece[i][j] = new Piece();
                piece[i][j]=inBoard.get(i).get(j);
                board.gameStateList2.get(board.nrOfMoves).get(i).add(j,piece[i][j]);
            }
        }
    }

    public void undoMove(){
        Board board= getBoardInstance();
        board.gamIsRunning =true;
        if (board.nrOfMoves<=0){
            System.out.println("cant undo board is reset!");
            return;
        }
        board.nrOfMoves--;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                board.getBoard().get(i).set(j,board.gameStateList2.get(board.nrOfMoves).get(i).get(j));
            }
        }
    }

    public void resetBoard(){
        Board board= getBoardInstance();
        board.initBoard();
        board.gamIsRunning =true;
        board.nrOfMoves=0;
        board.setLatestMove(null);
//        autoMove=false;
        board.initList(board.getBoard());
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                board.getBoard().get(i).set(j,board.gameStateList2.get(board.nrOfMoves).get(i).get(j));
                board.getBoard().get(i).get(j).setFirstMove(true);
            }
        }
        System.out.println("game reset");
    }

    public void setPiece(Piece piece){
        Board board =getBoardInstance();
        board.getBoard().get(board.latestMove.destY).set(board.latestMove.destX,piece);
    }
}
