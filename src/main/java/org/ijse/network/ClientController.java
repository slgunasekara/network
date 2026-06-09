package org.ijse.network;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientController {


    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String clientName;


    @FXML private TextArea txtArea;
    @FXML private TextField txtMessage;


    @FXML private TextField user;
    @FXML private TextField price;

    public void setClientName(String name) {
        this.clientName = name;

        new Thread(() -> {
            try {
                Socket remoteSocket = new Socket("127.0.0.1", 6000);


                dataOutputStream= new DataOutputStream(remoteSocket.getOutputStream());
                dataInputStream = new DataInputStream(remoteSocket.getInputStream());

                dataOutputStream.writeUTF(clientName);
                dataOutputStream.flush();

                while (true) {
                    String message = dataInputStream.readUTF();
                    Platform.runLater(() -> txtArea.appendText("Server" +message + "\n"));
                }

            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void manageBid() throws IOException {
        new Thread(() -> {
            while(true){
                try{
                    String username = user.getText();
                    String bid = price.getText();

                    if(bid.isEmpty()){
                        ClientController controller = new ClientController();
                        controller.txtArea.appendText(username + "Please enter your bid.."+ "\n");
                    }

                    if(!bid.isEmpty()){
                        ClientController controller = new ClientController();
                        controller.txtArea.appendText(username + " : " + bid + "\n");
                    }


                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }

        }).start();



    }



    public void sendOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(clientName + " : " + txtMessage.getText());
        dataOutputStream.flush();
        txtMessage.clear();
    }


    public void OnAction(ActionEvent event) throws IOException {
        String username = user.getText();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("client.fxml"))));
        newStage.setTitle("Client : " + username);
        newStage.show();


    }
}

