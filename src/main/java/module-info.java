module marcus.okodugha.chessv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens marcus.okodugha.chessv1 to javafx.fxml;
    exports marcus.okodugha.chessv1;
    exports marcus.okodugha.chessv1.Model;
    opens marcus.okodugha.chessv1.Model to javafx.fxml;
}