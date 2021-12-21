package client;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.File;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Data {
    private String pathTo;
    private String pathFrom;
    private int numberSockets;
    private AtomicLong totalSize = new AtomicLong(0);
    private AtomicInteger totalNumberFiles = new AtomicInteger(0);
    private AtomicLong downloadedSize = new AtomicLong(0);
    private AtomicInteger downloadedFilesNumber = new AtomicInteger(0);
    private BlockingQueue<Socket> WorkingSockets = new LinkedBlockingQueue<>();
    private CountDownLatch gate;


    public CountDownLatch getGate() {
        return gate;
    }

    public void setGate(CountDownLatch gate) {
        this.gate = gate;
    }

    public int getNumberSockets() {
        return numberSockets;
    }

    public void setNumberSockets(int numberSockets) {
        this.numberSockets = numberSockets;
    }

    public String getPathTo() {
        return pathTo;
    }

    public void setPathTo(String pathTo) {
        this.pathTo = pathTo;
    }

    public String getPathFrom() {
        return pathFrom;
    }

    public void setPathFrom(String pathFrom) {
        this.pathFrom = pathFrom;
    }

    public AtomicLong getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(AtomicLong totalSize) {
        this.totalSize = totalSize;
    }

    public AtomicInteger getTotalNumberFiles() {
        return totalNumberFiles;
    }

    public void setTotalNumberFiles(AtomicInteger totalNumberFiles) {
        this.totalNumberFiles = totalNumberFiles;
    }

    public AtomicLong getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(AtomicLong downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public AtomicInteger getDownloadedFilesNumber() {
        return downloadedFilesNumber;
    }

    public void setDownloadedFilesNumber(AtomicInteger downloadedFilesNumber) {
        this.downloadedFilesNumber = downloadedFilesNumber;
    }

    public BlockingQueue<Socket> getWorkingSockets() {
        return WorkingSockets;
    }

    public void setWorkingSockets(BlockingQueue<Socket> workingSockets) {
        WorkingSockets = workingSockets;
    }
}
