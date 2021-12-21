package server;

import java.io.File;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Data {

    private String path;
    private int sockets;
    private AtomicLong size = new AtomicLong(0);
    private AtomicInteger numberFiles = new AtomicInteger(0);
    private BlockingQueue<File> files = new LinkedBlockingQueue<File>();
    private CountDownLatch gate;


    public Data(String path, int sockets) {
        this.path = path;
        this.sockets = sockets;

    }

    public CountDownLatch getGate() {
        return gate;
    }

    public void setGate(CountDownLatch gate) {
        this.gate = gate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSockets() {
        return sockets;
    }

    public void setSockets(int sockets) {
        this.sockets = sockets;
    }

    public BlockingQueue<File> getFiles() {
        return files;
    }

    public void setFiles(BlockingQueue<File> files) {
        this.files = files;
    }


    public AtomicLong getSize() {
        return size;
    }

    public void setSize(AtomicLong size) {
        this.size = size;
    }

    public AtomicInteger getNumberFiles() {
        return numberFiles;
    }

    public void setNumberFiles(AtomicInteger numberFiles) {
        this.numberFiles = numberFiles;
    }
}
