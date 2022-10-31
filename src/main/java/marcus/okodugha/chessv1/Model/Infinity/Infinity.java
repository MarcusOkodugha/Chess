package marcus.okodugha.chessv1.Model.Infinity;

import marcus.okodugha.chessv1.Model.*;
import marcus.okodugha.chessv1.Model.Color;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Infinity {
    Board board;
    Rules rules;
    int nrOfAiMoves;
    int nrOfMadeOpeningMoves;
    Move bestMove=new Move();
    Color color;
    Openings opening;
    public ArrayList<Move> allLegalAiMoves = new ArrayList<Move>();//todo add all legal mvoes from the right color



    public Infinity(Board board, Color color) {
        this.board = board;
        this.color = color;
        this.rules= new Rules(board);
        this.opening=new Openings(color);
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

    public void infinityMove() {
        if (!board.isWhiteTurn()&&color==Color.BLACK) {
            updateAllLegalAiMoves();
            if (board.allLegalBlackMoves.size() == 0) {
                if (board.blackKingIsInCheck){
                    System.out.println("Checkmate White Wins!!!");
                }else { System.out.println("Stalemate its a Draw no legal black moves");}
                board.gamIsRunning = false;
                return;
            }
            playOpeningThenCalculatedMoves();
            return;
        }
        if (board.isWhiteTurn()&&color==Color.WHITE) {
            updateAllLegalAiMoves();
            if (board.allLegalWhiteMoves.size() == 0) {
                if (board.whiteKingIsInCheck){
                    System.out.println("Checkmate Black Wins!!!");
                }else { System.out.println("Stalemate its a Draw no legal white moves");}
                board.gamIsRunning = false;
                return;
            }
//            playOpeningThenCalculatedMoves();
//            makeHighValueMovesElseOpening();
            makeRetardedMove();
        }
    }

    public void makeRetardedMove() {
        Random random = new Random();
        int r = random.nextInt(allLegalAiMoves.size()) ;

        board.movePiece(allLegalAiMoves.get(r));
//        System.out.println("infinity tride to make a move");
        nrOfAiMoves++;
    }

    public void makeCalculatedMove() {

        nrOfAiMoves++;
        getRandomMove();

        getHighestValueKillMove();

        board.movePiece(bestMove);
    }



    public void playOpeningThenCalculatedMoves() {
//        System.out.print(color);

        if (nrOfMadeOpeningMoves <=opening.getOpening().size()&&board.listContainsMove(allLegalAiMoves,opening.getOpeningMove(nrOfMadeOpeningMoves))){
            bestMove=opening.getOpeningMove(nrOfMadeOpeningMoves);
            nrOfMadeOpeningMoves++;
//            System.out.println("opening move");
        }
        if (bestMove==null){
            bestMove=getSmarterKillMove();
//            System.out.println("smart kill move");
        }
//        if (bestMove==null){
//            bestMove=getSafeMove();
//            System.out.println("safe move");
//        }
        if (bestMove==null){
            bestMove=getRandomMove();
//            System.out.println("random move");
        }
        nrOfAiMoves++;
        board.movePiece(bestMove);
        bestMove=null;
    }


    public void makeHighValueMovesElseOpening(){
        bestMove=getSmarterKillMove();
        System.out.print(color);
        System.out.print(" "+bestMove+" ");
        if (bestMove==null&&nrOfMadeOpeningMoves <=opening.getOpening().size()&&board.listContainsMove(allLegalAiMoves,opening.getOpeningMove(nrOfMadeOpeningMoves))){
            bestMove=opening.getOpeningMove(nrOfAiMoves);
            System.out.print(nrOfMadeOpeningMoves);nrOfMadeOpeningMoves++;
            System.out.println("opening move");
        }
        if (bestMove==null){
            bestMove=getSafeMove();
            System.out.println("safe move");
        }
        if (bestMove==null){
            bestMove=getRandomMove();
        }
        nrOfAiMoves++;
        board.movePiece(bestMove);
        bestMove=null;
    }

    private Move getOpeningMove() {
        bestMove=null;
        for (int i = 0; i < opening.getOpening().size(); i++) {
            if (board.listContainsMove(allLegalAiMoves, opening.getOpeningMove(i))) {
                bestMove = opening.getOpeningMove(nrOfAiMoves);
                return bestMove;
            }
        }
        return bestMove;
    }

    private Move getRandomMove() {
        Random random = new Random();
        int r = random.nextInt(allLegalAiMoves.size()) ;
        bestMove= new Move(allLegalAiMoves.get(r).srcX,allLegalAiMoves.get(r).srcY,allLegalAiMoves.get(r).destX,allLegalAiMoves.get(r).destY);
        return bestMove;
    }

    private int getRandomNumber(int maxRand) {
        Random random = new Random();
        return random.nextInt(maxRand) ;
    }
    //todo make a try to checkKing method

    private Move getHighestValueKillMove() {
        bestMove=null;
        for (Move b: allLegalAiMoves) {
            if (!destPointIsEmpty(new Point(b.destX,b.destY))){
                bestMove=b;
                if (board.getBoard().get(b.destY).get(b.destX).getPieceType().getValue()>board.getBoard().get(bestMove.destY).get(bestMove.destX).getPieceType().getValue()){//kill highest value piece
                    bestMove = new Move(b.srcX,b.srcY,b.destX,b.destY);
                }
            }
        }
        return bestMove;
    }

    private Move getSmarterKillMove(){
        bestMove=null;
        for (Move m: allLegalAiMoves) {
            if (!destPointIsEmpty(new Point(m.destX,m.destY))){//kill move
                if (bestMove==null) bestMove=m;
                if (board.getBoard().get(m.destY).get(m.destX).getPieceType().getValue()>=board.getBoard().get(m.srcY).get(m.srcX).getPieceType().getValue()){//killed piece is more worth or equal to the src piece
                    if (bestMove==null) bestMove=m;

                    if (board.getBoard().get(m.destY).get(m.destX).getPieceType().getValue()>board.getBoard().get(bestMove.destY).get(bestMove.destX).getPieceType().getValue()){//kill highest value piece
                        bestMove = new Move(m.srcX,m.srcY,m.destX,m.destY);
                    }
                }

            }
        }
        return bestMove;
    }

    private Move getSafeMove(){
        Collections.shuffle(allLegalAiMoves);
        if (color==Color.WHITE){
            for (Move m:allLegalAiMoves) {
                if (!board.blackAttacks[m.destX][m.destY])return m;//if black not attacks square return safe move m
            }
        }
        if (color==Color.BLACK){
            for (Move m:allLegalAiMoves) {
                if (!board.whiteAttacks[m.destX][m.destY])return m;//if black not attacks square return safe move m
            }
        }
        return null;
    }

    private boolean destPointIsEmpty(Point point){
        if (board.getBoard().get(point.y).get(point.x).getPieceType()!= PieceType.EMPTY)return false;
        return true;
    }


}
