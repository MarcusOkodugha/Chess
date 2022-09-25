package marcus.okodugha.chessv1.Model;

public class Piece {
    private final Color color;
    private final PieceType pieceType;
    public final int imageIndex;
    public boolean firstMove=true;
    int xp;
    int yp;
    static int srcX;
    static int srcY;

    public Piece(Color color, PieceType pieceType,int imageIndex) {
        this.color = color;
        this.pieceType = pieceType;
        this.imageIndex= imageIndex;

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

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setYp(int yp) {
        this.yp = yp;
    }

    @Override
    public String toString() {
        return color+""+pieceType;
    }
}
