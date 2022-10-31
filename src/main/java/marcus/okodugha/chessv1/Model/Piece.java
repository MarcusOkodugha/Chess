package marcus.okodugha.chessv1.Model;


public class Piece {
    private Color color;
    private PieceType pieceType;
    public int imageIndex;
    public boolean firstMove;

    public Piece(Color color, PieceType pieceType,int imageIndex) {
        this.color = color;
        this.pieceType = pieceType;
        this.imageIndex= imageIndex;
        firstMove=true;
    }

    public Piece() {
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public String toString() {
        return color+""+pieceType;
    }
}
