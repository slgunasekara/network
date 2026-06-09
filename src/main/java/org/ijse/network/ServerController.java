package org.ijse.network;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {


    List<DataOutputStream> allClients = new ArrayList<>();

    @FXML private TextArea txtArea;
    @FXML private TextField txtMessage;

    public  void initialize(){
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(6000);
                txtArea.appendText("Server started - Port 6000" +"\n");
                System.out.println("Server started");

                while (true){
                    Socket localSocket = serverSocket.accept();
                    txtArea.appendText("Client connected\n");
                    System.out.println("Client connected");


                    handleClient(localSocket);

                }

            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void handleClient(Socket localSocket){
        new Thread(()->{
            try {
                DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(localSocket.getOutputStream());
                allClients.add(dataOutputStream);

                String clientName = dataInputStream.readUTF();
                txtArea.appendText(clientName + " joined\n");
                System.out.println(clientName + "Client joined");

                while (true){
                    String message = dataInputStream.readUTF();
                    Platform.runLater(() -> txtArea.appendText(message + "\n"));
                    broadcast(message);
                }


            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void broadcast(String message){
        for (DataOutputStream dataOutputStream : allClients){
            try{
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();

            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }



    public void sendOnAction(ActionEvent event) {
        broadcast(txtMessage.getText());
        txtArea.appendText(txtMessage.getText() + "\n");
        txtMessage.clear();
    }
}
