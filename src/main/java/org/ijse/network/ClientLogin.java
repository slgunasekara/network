package org.ijse.network;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientLogin {



    @FXML private TextField user;
    @FXML private TextField price;



    public void sendOnAction(ActionEvent event) throws IOException {
        String username = user.getText();
        String priceString = price.getText();

        Stage newStage = new Stage();
        newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("client.fxml"))));
        newStage.show();

    }

}
