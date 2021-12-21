package client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Modality;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ImportantService extends Service<Props> {

    Data data;
    ExecutorService executor;
    Semaphore semaphore;

    public ImportantService(Data data, ExecutorService executor) {
        this.executor = executor;
        this.data = data;
    }

    @Override
    protected Task<Props> createTask() {
        return new Task<>() {

            @Override
            protected Props call() throws InterruptedException, ExecutionException {
                Long numberFilesWithEmptyDirs = null;
                if (this.isCancelled()){
                    executor.shutdownNow();

                }
                try {
                    Socket socket = new Socket(InetAddress.getLocalHost(), 8000);
                    PrintWriter push = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader take = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    push.println(data.getPathFrom());
                    push.println(data.getNumberSockets());
                    String doneSearching = take.readLine();
                    String allsize = take.readLine();
                    String numberFilse = take.readLine();
                    String numberFilesWithEmptyDirsTemp = take.readLine();
                    numberFilesWithEmptyDirs = Long.parseLong(numberFilesWithEmptyDirsTemp);
                    data.setTotalSize(new AtomicLong(Long.parseLong(allsize)));
                    data.setTotalNumberFiles(new AtomicInteger(Integer.parseInt(numberFilse)));
                    System.out.println("====" + doneSearching + "====");
                    System.out.println("Total size of files to download: " + data.getTotalSize());
                    System.out.println("Total number of files to be downloaded: " + data.getTotalNumberFiles());
                    System.out.println("Total number of sockets/threads: " + data.getNumberSockets());


                } catch (IOException e) {
                    System.out.println("Server is not running");
                }
                //        COUNTDOWNLATCH Riesenie
//                data.setGate(new CountDownLatch(data.getNumberSockets()));
                Props props = new Props("","");
                var propsExecutor = Executors.newScheduledThreadPool(1);
                propsExecutor.scheduleAtFixedRate(
                        ()->{
                            props.setSize(data.getDownloadedFilesNumber() + " / " + data.getTotalNumberFiles() + " files");
                            props.setDownloaded(data.getDownloadedSize() + " / " + data.getTotalSize() + " By");
                            props.setDownloadedSizeProperty((double)data.getDownloadedSize().longValue()/data.getTotalSize().longValue());
                            props.setDownloadedFilesNumberProperty((double)data.getDownloadedFilesNumber().intValue()/data.getTotalNumberFiles().intValue());
                            updateValue(new Props(props.getDownloadedSizeProperty(), props.getDownloadedFilesNumberProperty(), props.getSize(),props.getDownloaded()));
                        },
                        0,
                        100,
                        TimeUnit.MILLISECONDS
                );

                CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);
                for (int i = 0; i < data.getNumberSockets(); i++) {
                    DownloadFile task = new DownloadFile(data);
                    completionService.submit(task);
                }
                for (int i = 0; i < data.getNumberSockets(); i++) {
                    try {
                        Boolean done = completionService.take().get();
                        System.out.println("this thread is done sending files" + done);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        ImportantService.this.cancel();
                        executor.shutdownNow();
                        System.err.println("ended task");
                        throw e;
                    } catch (ExecutionException e) {
//                        e.printStackTrace();
                        executor.shutdownNow();
                        ImportantService.this.cancel();
                        System.err.println("ended task");
                        throw  e;
                    }
                }
                executor.shutdownNow();
                System.out.println("Done This Job Waiting For Other");
                throw new InterruptedException();
            }

        };

    }
}
