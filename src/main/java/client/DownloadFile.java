package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class DownloadFile implements Callable<Boolean> {

    Socket socket;
    Data data;
    File file;
    FileOutputStream writeFile1 = null;
    InputStream stream = null;
    Boolean exit = false;

    public DownloadFile(Data data) {
        this.data = data;
    }

    @Override
    public Boolean call() throws IOException, InterruptedException {
        socket = new Socket(InetAddress.getLocalHost(), 8000);
        boolean done = true;
        while (done) {
            done = downloadFile();
        }
        socket.close();
        return true;

    }

    private boolean downloadFile() throws IOException, InterruptedException {

        try {
            String type;
            OutputStream outputStream = socket.getOutputStream();
            stream = socket.getInputStream();
            InputStreamReader inpuStreamReader = new InputStreamReader(stream);
            DataInputStream dataInputStream = new DataInputStream(stream);
            PrintWriter push = new PrintWriter(outputStream, true);
            BufferedReader take = new BufferedReader(inpuStreamReader);

            type = take.readLine();

            if (type.equals("closing")) {
                socket.close();
                return false;
            }

            if (type.equals("file")) {
                String path = take.readLine();
                String tempSize = take.readLine();
                Long fileSize = Long.parseLong(tempSize);
                Long fileSizeDownloaded = null;

                path = path.substring(data.getPathFrom().length());
                path = data.getPathTo() + path;
                System.out.println(path);

                file = new File(path);
                file.getParentFile().mkdirs();
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                    push.println(0);
                    fileSizeDownloaded = 0L;
                } else {
                    fileSizeDownloaded = file.length();
                    if (fileSize.compareTo(fileSizeDownloaded) == 0) {
                        System.out.println("File already exists and is downloaded.");
                        synchronized (this) {
                            data.getDownloadedFilesNumber().incrementAndGet();
                            data.getDownloadedSize().addAndGet(fileSizeDownloaded);
//                            Props.downloadedFilesNumberProperty.setValue((double) data.getDownloadedFilesNumber().intValue() / (double) data.getTotalNumberFiles().intValue());
//                            Props.downloadedSizeProperty.setValue((double) data.getDownloadedSize().longValue() / data.getTotalSize().longValue());
                        }

                        push.println("downloaded");

                        return true;
                    } else {
                        System.out.println("File already exists but is not downloaded.");
                        push.println(file.length());

                        synchronized (this) {
                            data.getDownloadedSize().addAndGet(fileSizeDownloaded);
//                            Props.downloadedSizeProperty.setValue((double) data.getDownloadedSize().longValue() / data.getTotalSize().longValue());
                        }
                    }
                }

                writeFile1 = new FileOutputStream(file.getAbsolutePath());

                BufferedOutputStream writeFile2 = new BufferedOutputStream(writeFile1);
                int numberOfBytes = 2048;
                byte[] buffer;
                while ( !Thread.currentThread().isInterrupted() && fileSizeDownloaded.compareTo(fileSize) != 0) {
                    if (fileSize - fileSizeDownloaded >= numberOfBytes) {
                        fileSizeDownloaded += numberOfBytes;
                    } else {
                        numberOfBytes = (int) (fileSize - fileSizeDownloaded);
                        fileSizeDownloaded = fileSize;
                    }
                    buffer = new byte[numberOfBytes];


                    int read = dataInputStream.read(buffer, 0, numberOfBytes);

                    writeFile2.write(buffer, 0, numberOfBytes);


                    synchronized (this) {
                        data.getDownloadedSize().addAndGet(numberOfBytes);
//                        Props.downloadedSizeProperty.setValue((double) data.getDownloadedSize().longValue() / data.getTotalSize().longValue());
                    }


                }
                writeFile2.flush();


                synchronized (this) {
                    data.getDownloadedFilesNumber().incrementAndGet();
//                    Props.downloadedFilesNumberProperty.setValue((double) data.getDownloadedFilesNumber().intValue() / (double) data.getTotalNumberFiles().intValue());
                }
                writeFile2.close();
                if (Thread.interrupted()){
                    throw new InterruptedException();
                }
                return true;

            }
            if (type.equals("dir")) {
                String path = take.readLine();
                File dir = new File(path);
                dir.mkdirs();
                dir.createNewFile();
                if (Thread.interrupted()){
                    throw new InterruptedException();
                }
                return true;

            }

        } catch (InterruptedException  | IOException  e) {
            socket.close();
            writeFile1.close();
            throw e;
        }
        return true;
    }


}
