package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;

import java.io.*;

public class ClientPageController {

    private Stage stage;
    private Stage stage2;
    private int sckts;

    public ClientPageController(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private AnchorPane numberFiles;

    @FXML
    private TextField pathFrom;

    @FXML
    private TextField pathTo;

    @FXML
    private TextField sockets;

    @FXML
    private Button start;

    @FXML
    void initialize() {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                try {
                    stage2.close();
                    Platform.exit();
                }catch (NullPointerException e){
                    System.out.println("stage2 is closed");
                    Platform.exit();
                }


            }
        });
        pathFrom.textProperty().setValue("C:\\Users\\Dell\\Desktop\\witcher");
        pathTo.textProperty().setValue("D:\\KOPR\\data\\prazdny");
        sockets.textProperty().setValue("4");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    FXMLLoader loader = new FXMLLoader(Client.class.getResource("download-view.fxml"));
                    Data data = new Data();
                    data.setPathFrom(pathFrom.textProperty().getValue());
                    data.setPathTo(pathTo.textProperty().getValue());
                    data.setNumberSockets(Integer.parseInt(sockets.textProperty().getValue()));
                    stage2 = new Stage();
                    loader.setController(new DownloadPageController(data, stage2));

                    Parent parent = loader.load();
                    Scene scene = new Scene(parent);

                    stage2.setScene(scene);
                    stage2.initModality(Modality.APPLICATION_MODAL); // nikto iny
                    stage2.setTitle("Downloading");
                    stage2.show();

                }catch (IOException e) {
                    e.printStackTrace();
                }catch (NumberFormatException e){
                    System.err.println("Sockets must be int");
                }

            }
        });


    }

}