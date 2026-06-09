package org.ijse.network;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        for(int i = 1; i<=2; i++){
           FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
           Scene scene = new Scene(loader.load());

           ClientController controller = loader.getController();
           controller.setClientName("Client :" + i);

           Stage newStage = new Stage();
           newStage.setTitle("Client " + i);
           newStage.setScene(scene);
           newStage.show();

        }

    }
}
