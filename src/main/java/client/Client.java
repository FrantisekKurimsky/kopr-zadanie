package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("client-view.fxml"));
        ClientPageController controller = new ClientPageController(stage);
        fxmlLoader.setController(controller);
        Parent rootPane = fxmlLoader.load();
        Scene scene = new Scene(rootPane);
        stage.setTitle("CLIENT");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }


}
