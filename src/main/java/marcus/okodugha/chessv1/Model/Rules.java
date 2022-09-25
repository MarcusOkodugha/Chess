package marcus.okodugha.chessv1.Model;

public class Rules {

    private Board board;
    public Rules(Board board) {
        this.board=board;
    }

    public boolean isLegalMove(int srcX, int srcY, int destX, int destY, Piece piece){
        if (piece.getPieceType()==PieceType.PAWN){
            if (isPawnMoveLegal(srcX,srcY,destX,destY,piece)){
                return true;
            }
        }
        if (piece.getPieceType()==PieceType.BISHOP){
            if (isBishopMoveLegal(srcX,srcY,destX,destY,piece)){
                return true;
            }
        }
        if (piece.getPieceType()==PieceType.ROOK){
            if (isRookMoveLegal(srcX,srcY,destX,destY,piece)){
                return true;
            }
        }
        if (piece.getPieceType()==PieceType.KING){
            if (isKingMoveLegal(srcX,srcY,destX,destY,piece)){
                return true;
            }
        }
        if (piece.getPieceType()==PieceType.QUEEN){
            if (isQueenMoveLegal(srcX,srcY,destX,destY,piece)){
                return true;
            }
        }
        return false;
    }
    // srcX,srcY,destX,destY //todo remove

    //piece specific rules
    public boolean isPawnMoveLegal(int srcX,int srcY,int destX,int destY,Piece piece){
            if (moveIsForward(srcY,destY)&&piece.getColor()==Color.WHITE||!moveIsForward(srcY,destY)&&piece.getColor()==Color.BLACK){//pawn moves in the right direction depending on color
                if (((moveIsOneStep(srcX,srcY,destX,destY)||moveIsTowSteps(srcY,destY,piece))&&moveIsStraight(srcX,srcY,destX,destY))&&moveIsStraightUpOrDown(srcX,srcY,destX,destY)){//pawn moves one or two steps in a straight line
                    if (destIsEmpty(destX,destY)){//destination squarer is empty
                        return true;
                    }
                }
                if(moveIsStraightDiagonal(srcX,srcY,destX,destY)&&moveIsOneStep(srcX,srcY,destX,destY)){//move is straight diagonal and move is one step
                    if (!destIsEmpty(destX,destY)&&!destPieceIsSameColor(destX,destY,piece))return true;//dest is not empty
                }
            }
        return false;
    }

    private boolean isBishopMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsStraightDiagonal(srcX,srcY,destX,destY)){
            if (destIsEmpty(destX,destY))return true;
            if (!destPieceIsSameColor(destX,destY,piece))return true;
        }
            return false;
        }

    private boolean isRookMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsStraight(srcX,srcY,destX,destY)){
            if (destIsEmpty(destX,destY))return true;
            if (!destPieceIsSameColor(destX,destY,piece))return true;
        }
        return false;
    }
    private boolean isKingMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsOneStep(srcX,srcY,destX,destY)){
            if (destIsEmpty(destX,destY))return true;
            if (!destPieceIsSameColor(destX,destY,piece))return true;
        }
        return false;
    }

    private boolean isQueenMoveLegal(int srcX, int srcY, int destX, int destY, Piece piece) {
        if (moveIsStraight(srcX,srcY,destX,destY)||moveIsStraightDiagonal(srcX,srcY,destX,destY)){
            if (destIsEmpty(destX,destY))return true;
            if (!destPieceIsSameColor(destX,destY,piece))return true;
        }
        return false;
    }
//basic rules
    private boolean moveIsForward(int srcY,int destY){
        return srcY > destY;
    }
    private boolean moveIsOneStep(int srcX,int srcY,int destX,int destY){
        if (srcX==destX||srcX==destX+1||srcX==destX-1){
            if (srcY==destY||srcY==destY+1||srcY==destY-1){
                return true;
            }
        }
        return false;
    }
    private boolean moveIsTowSteps(int srcY,int destY,Piece piece){
        if ((srcY==destY+2||srcY==destY-2)&&piece.firstMove){
            return true;
        }
        return false;
    }
    private boolean moveIsStraight(int srcX,int srcY,int destX,int destY){
        return srcX == destX || srcY == destY;
    }
    private boolean moveIsStraightUpOrDown(int srcX,int srcY,int destX,int destY){
        return srcX == destX;
    }
    private boolean moveIsStraightDiagonal(int srcX, int srcY, int destX, int destY){
        for (int i = 1; i <=7; i++) {
            if (srcX==destX+i&&srcY==destY+i||srcX==destX-i&&srcY==destY-i||srcX==destX+i&&srcY==destY-i||srcX==destX-i&&srcY==destY+i)return true;
        }
        return false;
    }
    private boolean destIsEmpty(int destX,int destY){
        return board.getBoard().get(destY).get(destX).getPieceType()==PieceType.EMPTY;//destination squarer is empty
    }
    private boolean destPieceIsSameColor(int destX,int destY,Piece piece){
        return board.getBoard().get(destY).get(destX).getColor()==piece.getColor();
    }

}

