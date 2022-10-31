package marcus.okodugha.chessv1.Model;

public enum PieceType {
    EMPTY(0),PAWN(1),KNIGHT(3),BISHOP(3),ROOK(5),QUEEN(9),KING(100);

    PieceType(int value) {
    }

    public int getValue(){
        int value=0;
        switch (this){
            case EMPTY -> value=0;
            case PAWN -> value=1;
            case KNIGHT -> value=3;
            case BISHOP -> value=3;
            case ROOK -> value=5;
            case QUEEN -> value=9 ;
            case KING -> value=100;
        }
        return value;
    }



}

