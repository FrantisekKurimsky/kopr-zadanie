package client;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.*;


public class DownloadPageController {


    private Stage stage2;
    private Data data;

    @FXML // fx:id="download"
    private Label download; // Value injected by FXMLLoader
    @FXML
    private ProgressBar loadingBarFiles; // Value injected by FXMLLoader
    @FXML // fx:id="loadingBarMB1"
    private ProgressBar loadingBarMB1; // Value injected by FXMLLoader
    @FXML // fx:id="numberFiles"
    private Label numberFiles; // Value injected by FXMLLoader
    @FXML // fx:id="numberMB"
    private Label numberMB; // Value injected by FXMLLoader

    public DownloadPageController(Data data, Stage stage2) {
        this.stage2 = stage2;
        this.data = data;
    }

    @FXML
    void initialize() {
        ExecutorService executor = Executors.newFixedThreadPool(data.getNumberSockets());
        ImportantService service = new ImportantService(data, executor);

        service.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                executor.shutdownNow();
            }
        });

        service.exceptionProperty().addListener((a,b, value) ->{
            service.cancel();
        });

        service.valueProperty().addListener((ob, oldV, value) -> {
            if (value == null) {
                return;
            }
            loadingBarMB1.progressProperty().setValue(value.getDownloadedSizeProperty());
            loadingBarFiles.progressProperty().setValue(value.getDownloadedFilesNumberProperty());
            Platform.runLater(() -> {
                numberFiles.setText(
                        value.getSize()
                        );
                    }

            );
            Platform.runLater(() -> {
                        numberMB.setText(
                                value.getDownloaded()
                        );
                    }

            );
            if (value.getDownloadedSizeProperty() == 1){
                executor.shutdownNow();
                stage2.close();
                service.cancel();
            }
            if (value.getDownloadedFilesNumberProperty() == 1){
                executor.shutdownNow();
                stage2.close();
                service.cancel();
            }


        });
        service.start();

        stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                executor.shutdownNow();
                service.cancel();
            }
        });

    }

}