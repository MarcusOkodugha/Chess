package marcus.okodugha.chessv1.Model.Infinity;

import marcus.okodugha.chessv1.Model.*;
import marcus.okodugha.chessv1.Model.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Infinity {
    Board board;
    Rules rules;
    int nrOfAiMoves;
    Move bestMove=new Move();
    Color color;
    Openings opening= new Openings();
    public ArrayList<Move> allLegalAiMoves = new ArrayList<Move>();//todo add all legal mvoes from the right color



    public Infinity(Board board, Color color) {
        this.board = board;
        this.color = color;
        this.rules= new Rules(board);
        updateAllLegalAiMoves();
    }

    public void updateAllLegalAiMoves(){
        allLegalAiMoves.clear();
        board.getAllLegalMoves();

        if (color==Color.WHITE){
            allLegalAiMoves.addAll(board.allLegalWhiteMoves);
        }

        if (color==Color.BLACK){
            allLegalAiMoves.addAll(board.allLegalBlackMoves);
        }
    }

    public void infinityMove(){
        if (!board.isWhiteTurn()&&color==Color.BLACK) {
            updateAllLegalAiMoves();
            if (board.allLegalBlackMoves.size() == 0) {
                System.out.println("no legal Black moves White Wins!!!!");
                board.gamIsRunning = false;
                return;
            }
//                    board.getInfinityWhite().makeRetardedMove();
            makeHighValueMovesElseOpening();
            return;
        }
        if (board.isWhiteTurn()&&color==Color.WHITE) {
            updateAllLegalAiMoves();
            if (board.allLegalWhiteMoves.size() == 0) {
                System.out.println("no legal White moves Black Wins!!!!");
                board.gamIsRunning = false;
                return;
            }
//                    board.getInfinityWhite().makeRetardedMove();
            makeHighValueMovesElseOpening();
            return;
        }
    }

    public void makeRetardedMove(){
        Random random = new Random();
        int r = random.nextInt(allLegalAiMoves.size()) ;

        board.movePiece(allLegalAiMoves.get(r));
//        System.out.println("infinity tride to make a move");
        nrOfAiMoves++;
    }

    public void makeCalculatedMove(){

        nrOfAiMoves++;
        Random random = new Random();
        int r = random.nextInt(allLegalAiMoves.size()) ;

        bestMove= new Move(allLegalAiMoves.get(r).srcX,allLegalAiMoves.get(r).srcY,allLegalAiMoves.get(r).destX,allLegalAiMoves.get(r).destY);

        killHighestValue();

        board.movePiece(bestMove);
    }



    public void playOpeningThenCalculatedMoves(){
        if (nrOfAiMoves <8&&board.listContainsMove(allLegalAiMoves,opening.getOpeningMove(nrOfAiMoves))){
            bestMove=opening.getOpeningMove(nrOfAiMoves);
        }else {bestMove=null;}
        if (bestMove==null){

            Random random = new Random();
            int r = random.nextInt(allLegalAiMoves.size()) ;

            bestMove= new Move(allLegalAiMoves.get(r).srcX,allLegalAiMoves.get(r).srcY,allLegalAiMoves.get(r).destX,allLegalAiMoves.get(r).destY);

            killHighestValue();

        }

        nrOfAiMoves++;
        board.movePiece(bestMove);
        bestMove=null;
    }


    public void makeHighValueMovesElseOpening(){

        if (nrOfAiMoves <8&&board.listContainsMove(allLegalAiMoves,opening.getOpeningMove(nrOfAiMoves))){
            bestMove=opening.getOpeningMove(nrOfAiMoves);
        }else {bestMove=null;}
        if (bestMove==null){
            Random random = new Random();
            int r = random.nextInt(allLegalAiMoves.size()) ;
            bestMove= new Move(allLegalAiMoves.get(r).srcX,allLegalAiMoves.get(r).srcY,allLegalAiMoves.get(r).destX,allLegalAiMoves.get(r).destY);
            killHighestValue();
        }
        nrOfAiMoves++;
        board.movePiece(bestMove);
        bestMove=null;
    }


    //todo make a try to checkKing method

    private Move killHighestValue() {
        for (Move b: allLegalAiMoves) {
            if (!destPointIsEmpty(new Point(b.destX,b.destY))){
                if (board.getBoard().get(b.destY).get(b.destX).getPieceType().ordinal()>board.getBoard().get(bestMove.destY).get(bestMove.destX).getPieceType().ordinal()){//kill highest value piece
                    bestMove = new Move(b.srcX,b.srcY,b.destX,b.destY);
                }
            }
        }
        return bestMove;
    }

    private boolean destPointIsEmpty(Point point){
        if (board.getBoard().get(point.y).get(point.x).getPieceType()!= PieceType.EMPTY)return false;
        return true;
    }







}
