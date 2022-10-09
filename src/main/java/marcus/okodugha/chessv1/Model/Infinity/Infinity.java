package marcus.okodugha.chessv1.Model.Infinity;

import marcus.okodugha.chessv1.Model.Board;
import marcus.okodugha.chessv1.Model.Color;
import marcus.okodugha.chessv1.Model.Move;
import marcus.okodugha.chessv1.Model.PieceType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static marcus.okodugha.chessv1.Model.Board.column;
import static marcus.okodugha.chessv1.Model.Board.row;

public class Infinity {
    Board board;
    int nrOfBlackMoves;
    Move bestMove=new Move();
    Color color;
    Openings opening= new Openings();
    public ArrayList<Move> allLegalAiMoves = new ArrayList<Move>();//todo add all legal mvoes from the right color



    public Infinity(Board board, Color color) {
        this.board = board;
        this.color = color;
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

    public void makeRetardedMove(){

        Random random = new Random();
        int r = random.nextInt(allLegalAiMoves.size()) ;
        System.out.println(r);
        System.out.println("black has "+allLegalAiMoves.size()+" legal moves ");
        board.movePiece(allLegalAiMoves.get(r).srcX,allLegalAiMoves.get(r).srcY,allLegalAiMoves.get(r).destX,allLegalAiMoves.get(r).destY);
        System.out.println("infinity tride to make a move");
        nrOfBlackMoves++;
    }

    public void makeCalculatedMove(){

        nrOfBlackMoves++;
        Random random = new Random();
        int r = random.nextInt(allLegalAiMoves.size()) ;

        bestMove= new Move(allLegalAiMoves.get(r).srcX,allLegalAiMoves.get(r).srcY,allLegalAiMoves.get(r).destX,allLegalAiMoves.get(r).destY);

        for (Move b: allLegalAiMoves) {
            if (!destPointIsEmpty(new Point(b.destX,b.destY))){
                if (board.getBoard().get(b.destY).get(b.destX).getPieceType().ordinal()>board.getBoard().get(bestMove.destY).get(bestMove.destX).getPieceType().ordinal()){
                    bestMove = new Move(b.srcX,b.srcY,b.destX,b.destY);
                }
            }
        }

        board.movePiece(bestMove.srcX,bestMove.srcY,bestMove.destX,bestMove.destY);
    }



    public void playOpeningThenCalculatedMoves(){


        if (nrOfBlackMoves<8){
            bestMove=opening.getOpeningMove(nrOfBlackMoves);
        }

        if (nrOfBlackMoves>=8){
            Random random = new Random();
            int r = random.nextInt(allLegalAiMoves.size()) ;

            bestMove= new Move(allLegalAiMoves.get(r).srcX,allLegalAiMoves.get(r).srcY,allLegalAiMoves.get(r).destX,allLegalAiMoves.get(r).destY);

            for (Move b: allLegalAiMoves) {
                if (!destPointIsEmpty(new Point(b.destX,b.destY))){
                    if (board.getBoard().get(b.destY).get(b.destX).getPieceType().ordinal()>board.getBoard().get(bestMove.destY).get(bestMove.destX).getPieceType().ordinal()){
                        bestMove = new Move(b.srcX,b.srcY,b.destX,b.destY);
                    }
                }
            }


        }

        nrOfBlackMoves++;
        board.movePiece(bestMove.srcX,bestMove.srcY,bestMove.destX,bestMove.destY);
    }

    private boolean destPointIsEmpty(Point point){
        if (board.getBoard().get(point.y).get(point.x).getPieceType()!= PieceType.EMPTY)return false;

        return true;
    }


}
