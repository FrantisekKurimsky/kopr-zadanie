package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class SendFile implements Callable<Boolean> {

    Data data;
    File file;
    Semaphore semaphore;
    ServerSocket serverSocket;
    Socket socket = null;
    FileInputStream readFile1 = null;

    public SendFile(Data data, ServerSocket serverSocket) {
        this.data = data;
        this.serverSocket = serverSocket;
    }

    @Override
    public Boolean call() throws IOException, InterruptedException {
        socket = serverSocket.accept();
        System.out.println(Thread.currentThread().getName() + "connection : " + socket.getInetAddress());
        boolean done = true;
        while (done) {
            done = sendFile();
        }
        socket.close();
        return true;
    }

    private boolean sendFile() throws IOException, InterruptedException {

        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter push = new PrintWriter(outputStream, true);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            BufferedReader take = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//      kvoli priecinkom ktore nemaju deti
            File file = data.getFiles().peek();
            if (file == null){
                push.println("closing");
                return false;
            }
            file = data.getFiles().poll();
            if (file.isDirectory()) {
                push.println("dir");
                push.println(file.getAbsolutePath());
            } else {
                push.println("file");

//          pre zistenie ci uz subor nieje stiahnuty
                push.println(file.getAbsolutePath());
                push.println(file.length());

//          ak subor nieje stiahnuty, citame velkost aku ma(moze byt nul, moze byt viac
                String size = null;

                if ((size = take.readLine()) == null){
                    throw new IOException();
                };

                if (size.equals("zabi sa")) {
                    throw new IOException();
                }

                if (!size.equals("downloaded")) {
                    Long fileSize = file.length();
                    readFile1 = new FileInputStream(file.getAbsolutePath());
                    BufferedInputStream readFile2 = new BufferedInputStream(readFile1);


                    int numberOfBytes = 2048;
                    byte[] buffer = new byte[numberOfBytes];

                    Long downloaded = Long.parseLong(size);
                    Long startingPoint = Long.parseLong(size);
                    int count;
                    readFile2.skip(startingPoint);
                    while (downloaded.compareTo(fileSize) != 0) {
                        if (fileSize - downloaded >= numberOfBytes) {
                            downloaded += numberOfBytes;
                        } else {
                            numberOfBytes = (int) (fileSize - downloaded);
                            downloaded = fileSize;
                        }
                        buffer = new byte[numberOfBytes];
                        readFile2.read(buffer, 0, numberOfBytes);
                        dataOutputStream.write(buffer, 0, numberOfBytes);

                    }
                    dataOutputStream.flush();

                    readFile1.close();
                    readFile2.close();


                }
                if (size.equals("downloaded")) {
                    return true;
                }
                if (Thread.interrupted()){
                    throw new InterruptedException();
                }


            }
        } catch (InterruptedException | NullPointerException | IOException e) {
            if (readFile1!=null) {
                readFile1.close();
            }
            socket.close();
            throw e;
        }
        return true;
    }
}
