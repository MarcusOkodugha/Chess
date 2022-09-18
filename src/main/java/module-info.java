module marcus.okodugha.chessv1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens marcus.okodugha.chessv1 to javafx.fxml;
    exports marcus.okodugha.chessv1;
}