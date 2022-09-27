package marcus.okodugha.chessv1.Model;

import java.awt.*;
import java.util.ArrayList;

public class Rules2 {
    private Board board;
    private Point intersectionPoint = new Point();

    public ArrayList<Point> legalMoves = new ArrayList<Point>();


    public ArrayList<Point> getLegalMoves(int srcX, int srcY,Piece piece) {


        return legalMoves;
    }

    public Rules2(Board board) {
        this.board = board;

        legalMoves.add(new Point(5,7));

//        legalMoves.add(new Point(4,7));

    }
    public boolean isLegalMove(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (piece.getPieceType() == PieceType.PAWN) {
            if (isPawnMoveLegal(srcX, srcY, destX, destY, piece)) {
                return true;
            }
        }
        if (piece.getPieceType() == PieceType.BISHOP) {
            if (isBishopMoveLegal(srcX, srcY, destX, destY, piece)) {
                return true;
            }
        }
        if (piece.getPieceType() == PieceType.ROOK) {
            if (isRookMoveLegal(srcX, srcY, destX, destY, piece)) {
                return true;
            }
        }
        if (piece.getPieceType() == PieceType.KING) {
            if (isKingMoveLegal(srcX, srcY, destX, destY, piece)) {
                return true;
            }
        }
        if (piece.getPieceType() == PieceType.QUEEN) {
            if (isQueenMoveLegal(srcX, srcY, destX, destY, piece)) {
                return true;
            }
        }
        if (piece.getPieceType() == PieceType.KNIGHT) {
            if (isKnightMoveLegal(srcX, srcY, destX, destY, piece)) {
                return true;
            }
        }
        return false;
    }

    // srcX,srcY,destX,destY //todo remove
    //piece specific rules
    public boolean isPawnMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsForward(srcY, destY) && piece.getColor() == Color.WHITE || !moveIsForward(srcY, destY) && piece.getColor() == Color.BLACK) {//pawn moves in the right direction depending on color
            if (((moveIsOneStep(srcX, srcY, destX, destY) || moveIsTowSteps(srcY, destY, piece)) && moveIsStraight(srcX, srcY, destX, destY)) && moveIsStraightUpOrDown(srcX, destX)) {//pawn moves one or two steps in a straight line
                if (destIsEmpty(destX, destY)) {//destination squarer is empty
                    return true;
                }
            }
            if (moveIsStraightDiagonal(srcX, srcY, destX, destY) && moveIsOneStep(srcX, srcY, destX, destY)) {//move is straight diagonal and move is one step
                if (!destIsEmpty(destX, destY) && !destPieceIsSameColor(destX, destY, piece))
                    return true;//dest is not empty
            }
        }
        return false;
    }
    private boolean isBishopMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsStraightDiagonal(srcX, srcY, destX, destY)) {
            if (intersection(srcX, srcY, destX, destY) == null) {
                if (destIsEmpty(destX, destY)) return true;
                if (!destPieceIsSameColor(destX, destY, piece)) return true;
            }
        }return false;
    }

    private boolean isRookMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsStraight(srcX, srcY, destX, destY)) {
            if (intersection(srcX, srcY, destX, destY) == null) {
                if (destIsEmpty(destX, destY)) return true;
                if (!destPieceIsSameColor(destX, destY, piece)) return true;
            }
        }return false;
    }

    private boolean isKingMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsOneStep(srcX, srcY, destX, destY)) {
            if (destIsEmpty(destX, destY)) return true;
            if (!destPieceIsSameColor(destX, destY, piece)) return true;
        }return false;
    }

    private boolean isQueenMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsStraight(srcX, srcY, destX, destY) || moveIsStraightDiagonal(srcX, srcY, destX, destY)) {
            if (intersection(srcX, srcY, destX, destY) == null) {
                if (destIsEmpty(destX, destY)) return true;
                if (!destPieceIsSameColor(destX, destY, piece)) return true;
            }
        }return false;
    }

    private boolean isKnightMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece){
        //2up och 1vänster                   2up och 1höger               1up och 2 höger                 1ner 2höger                      2ner 1 höger                     2ner 1vänser                 1ner 2 vänster                   1up 2vänster
        if ((srcX-1==destX&&srcY-2==destY)||(srcX+1==destX&&srcY-2==destY)||(srcX+2==destX&&srcY-1==destY)||(srcX+2==destX&&srcY+1==destY)||(srcX+1==destX&&srcY+2==destY)||(srcX-1==destX&&srcY+2==destY)||(srcX-2==destX&&srcY+1==destY)||(srcX-2==destX&&srcY-1==destY)){
            if (destIsEmpty(destX, destY)) return true;
            if (!destPieceIsSameColor(destX, destY, piece)) return true;
        }
        return false;
    }
    //basic rules
    private boolean moveIsForward(int srcY, int destY) {
        return srcY > destY;
    }
    private boolean moveIsOneStep(int srcX, int srcY, int destX, int destY) {
        if (srcX == destX || srcX == destX + 1 || srcX == destX - 1) {
            if (srcY == destY || srcY == destY + 1 || srcY == destY - 1) {
                return true;
            }
        }
        return false;
    }
    private boolean moveIsTowSteps(int srcY, int destY, Piece piece) {
        if ((srcY == destY + 2 || srcY == destY - 2) && piece.firstMove) {
            return true;
        }
        return false;
    }
    private boolean moveIsStraight(int srcX, int srcY, int destX, int destY) {
        return srcX == destX || srcY == destY;
    }
    private boolean moveIsStraightUpOrDown(int srcX, int destX) {
        return srcX == destX;
    }
    private boolean moveIsStraightDiagonal(int srcX, int srcY, int destX, int destY) {
        for (int i = 1; i <= 7; i++) {
            if (srcX == destX + i && srcY == destY + i || srcX == destX - i && srcY == destY - i || srcX == destX + i && srcY == destY - i || srcX == destX - i && srcY == destY + i)
                return true;
        }
        return false;
    }
    private boolean destIsEmpty(int destX, int destY) {
        return board.getBoard().get(destY).get(destX).getPieceType() == PieceType.EMPTY;//destination squarer is empty
    }
    private boolean destPieceIsSameColor(int destX, int destY, Piece piece) {
        return board.getBoard().get(destY).get(destX).getColor() == piece.getColor();
    }
    private Point intersection(int srcX, int srcY, int destX, int destY) {
        intersectionPoint.x=0;
        intersectionPoint.y=0;
        if (moveIsStraight(srcX, srcY, destX, destY)) {//straight move
            System.out.println("move is straight");
            if (srcX > destX) {//move is left
                for (int i = destX; i < srcX; i++) {
                    if (board.getBoard().get(srcY).get(i).getPieceType()!=PieceType.EMPTY) {
                        intersectionPoint.x = i;
                        intersectionPoint.y = srcY;
                        System.out.println("illegal left move");
                    }
                }
            }
            if (srcX < destX) {//move is right
                for (int i = srcX+1; i < destX; i++) {
                    if (board.getBoard().get(srcY).get(i).getPieceType()!=PieceType.EMPTY) {
                        intersectionPoint.x = i;
                        intersectionPoint.y = srcY;
                        System.out.println("illegal right move "+i);
                    }
                }
            }
            if (srcY > destY) {//move up
                for (int i = destY; i < srcY; i++) {
                    if (board.getBoard().get(i).get(srcX).getPieceType()!=PieceType.EMPTY) {
                        intersectionPoint.x = srcX;
                        intersectionPoint.y = i;
                        System.out.println("illegal up move");
                    }
                    System.out.println("adding points ");
                    legalMoves.add(new Point(5,6));

                }
            }
            if (srcY < destY) {//move down
                for (int i = srcY+1; i < destY; i++) {
                    if (board.getBoard().get(i).get(srcX).getPieceType()!=PieceType.EMPTY) {
                        intersectionPoint.x = srcX;
                        intersectionPoint.y = i;
                        System.out.println("illegal down move "+i);
                    }
                }
            }
        }

        if (moveIsStraightDiagonal(srcX, srcY, destX, destY)) {//diagonal moves
            System.out.println("move is diagonal");
            int diagonalY=srcY;
            if (srcX > destX&&srcY < destY) {//move is left and down
                for (int i = srcX-1; i > destX; i--) {
                    if (board.getBoard().get(diagonalY+1).get(i).getPieceType()!=PieceType.EMPTY) {
                        intersectionPoint.x = i-1;
                        intersectionPoint.y = diagonalY+1;
                        System.out.println("illegal left and down move "+i);
                    }
                    diagonalY++;
                }
            }
            if (srcX < destX&& srcY < destY) {//move is right down
                for (int i = srcX+1; i < destX; i++) {
                    if (board.getBoard().get(diagonalY+1).get(i).getPieceType()!=PieceType.EMPTY) {
                        intersectionPoint.x = i+1;
                        intersectionPoint.y = diagonalY+1;
                        System.out.println("illegal right and down move "+i);
                    }
                    diagonalY++;
                }
            }
            if (srcX > destX&&srcY > destY) {//left and  up
                for (int i = srcX-1; i > destX; i--) {
                    if (board.getBoard().get(diagonalY-1).get(i).getPieceType()!=PieceType.EMPTY) {
                        intersectionPoint.x = i-1;
                        intersectionPoint.y = diagonalY-1;
                        System.out.println("illegal left and up move "+i);
                    }
                    diagonalY--;
                }
            }
            if (srcX < destX && srcY > destY) {//right and up
                System.out.println("move is right and up ");//todo remove
                for (int i = srcX+1; i < destX; i++) {
                    if (board.getBoard().get(diagonalY-1).get(i).getPieceType()!=PieceType.EMPTY) {
                        intersectionPoint.x = i+1;
                        intersectionPoint.y = diagonalY-1;
                        System.out.println("illegal right and up move "+i);

                    }
                    diagonalY--;
                }
            }
        }

        if ((intersectionPoint.x==destX&&intersectionPoint.y==destY)&&isEnemy(srcX,srcY,destX,destY))return null;//is enemy kill

        if (intersectionPoint.x !=0) return intersectionPoint;

        return null;
    }
    private boolean isEnemy(int srcX, int srcY, int destX, int destY){
        return board.getBoard().get(srcY).get(srcX).getColor()!=board.getBoard().get(destY).get(destX).getColor();
    }


}