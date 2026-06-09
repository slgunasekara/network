module org.ijse.network {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.ijse.network to javafx.fxml;
    exports org.ijse.network;
}