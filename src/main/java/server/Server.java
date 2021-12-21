package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Server {

    private ServerSocket serverSocket;
    private Data data;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(8000);
            System.out.println("/#%((#* #@@@@@@@@@* /. #@@@@@@@@@@@@@@@@@( ., (@@@@@@@@@* .&@@@@@@@@%  .    ....  ..,. .............         ... \n" +
                    "*****,,,.,,**,,.......,*../((((((#(//(/*,,**,  ,#&@@@@@@@@@@&#, ..  (@@@@@@@@@@@@@@@# ./#%*,,, #@@@@@@@@@* ,//. ,#&@@@@@@@@@&#, .*/* (@@@@@@@@@* .&@@@@@@@@%      .....  ....   .,,,,......   ...  .*///\n" +
                    "/,,,,....,,**,,,,,,. ,//*,(((/(#/*,,,,**,,,*/*,,,.          .,**,,,..,,*,.        ...,,.,.,***,,,,.   ..,....,***,,.        ..,,*/(/,,..... ...        ..     ,........  ....   ........           .    \n" +
                    "***,,,,,*****,,........****//,,,,,******,/***,,,**,,.,*,....  ,**,,**/*,//*,..   ......,*,*((#(**,  ,,**/**/*,,***************/*,,...,,,*,,..,,    .........  ..,......   ... .... ...      ...   ..... \n" +
                    "**,**,,,,,,,,,,,,/.  .,*,,******//****,*///*,..,,,,,..,,.     .,..,/**/*,,*,,......*/(#((////*,,....,*/((((/(((/,,...,*////((((((/,   .,*,,.,,.    .    ...  .,,,........      ....        ............ \n" +
                    "****,,,,,,*,,,,,,,.  *****/(//((/////(/******,,,,....,*//     *, .*/##/,,,,,*,/(//(#((((/**,,,.  ..,**/((#####(/*,,,,,**///*///((/*.  .,**,.,,       ......  ..,,....          ....                  .  \n" +
                    "****,,,,,,***,,***. .*//,,////((//**(#/*,******,.,.  */(*    .(, /#/***,,*/******(((#(((/*,,,,.  .,*//(##/**,,..,******/**//*******. .,.,,..,.  .        .......                   .          ....  *(. \n" +
                    "****,,,.,,******,,. ,//*,,*///**//*////*,***,,,,,..  ,//,    .*  ,///***,**/**//*/(((/****,,.   .**//((/**,,,,,,,,,,**************,.   .,,*//*,,....        .....   ..                       .,,,(*   ..\n" +
                    "****,,..,,***,**,*. .,**,,**/***(/****//,/**,,,,,.   ./*     ,, .//,***//**,,,,***/****...     .,*/((/,,,,,,......,,,*,**,,*******.    .,,**,.       ......       ..                  ..  .. ..,/#/  .. \n" +
                    "****,,.,,**********,,****//*/////***//*****,,,,....   ,. .,  ...,//*/***,,,,,...,*/,,,,,.   ...,*/(##((/*,,....  ..,*,,,,,........      ,,,*,. .,,..      .....,....               ..        .,**.. *%* \n" +
                    "****,,,,,,*******,,**/*/*,,*((/************,,,,...    ,.   .,.  ,*******,,,,*/**//*,,.....  .,,**/(##((/*,,,,.,,,,*//*.    .......      .,**,,,*,,,,,       ...,.......      .   ...,**.    ..,,/#/..,. \n" +
                    "***,,,.,,,,********,,****,,/*/((((/**/(****,,,,....   *,    .   ,/***/*,,*/,,*///**,..    ..,,,******///////****///(/*,,..   ...,       .,*//*,,  ..        ......,,,....    ..  .**,,..      .***. ..  \n" +
                    "****,,.,,,,******,,,,//*** .,*//**//***,,**,,,......  .        ,**,,,,,,,,**,,,,*,,,..      .,********//****,,*/###(/*,,,,.....,,        .,**,.   .... .... ....   *#/...    ... .,*,,,      .*/*,*, .  \n" +
                    "****,,.,,,****,***,.*//***..*,.,*,,,,,*/***,...,,.   ./*      .,,,,*,,,**,,,.,,,.,,,,,.     ,*********,,,...,,,*******,,*,,,,,*,       . .,,..  .....     . .......,, ...        .,,,..       ........  \n" +
                    "**,*,,,,,,*,,,****,.*/**//. **. ,,,,,***/**,,,,,..    ..     .**,*//*,,,,,,,....*//(/,.    .,********,,..,,,**,. .....,,,,,,,,,.     .  .,,,,. .,*,...  .....,,.,,,,... .        ......      ....*/,..  \n" +
                    "****,,.,****,,,,,,,*/*,,,,. ,*, ,*,*********,,,,,.    ,,      ,,.,****,..,,,...,*,,,,,.    *****//**,,,.,,,,**,..  .....,,,,,,      .,,.,,,,**,. .,.*/. ..   .,...........       ....         ..........\n" +
                    "***,,,.,,**,,,,,,.../*,,,,, .*/,,,*(/***/*//*,,,..    ..      ....,,,*,.,,,....,,...,.   //****///**,,...       ....,,,.,,,,,      ..,*,,,,,,,,,...,**,     .,*,.,.....,,,                       .,,,,.,\n" +
                    "//*,,,,.,,,,,,,,,..,(*,,***..**//*****//*,*,,,,......     .    .,**/*,..,,*((###%%%#(*, ,/*/***/*,,,,,,,,,,.       .....,,,     . ..*(*,,,,,,,,,,..,,**,,.  .**,...,,.,,,.      .,,,**,    ...... .,,...\n" +
                    "///*,,.,,***,,**//,*(******. ,*,.,*///*****,*,*,,. .,,,,.,/(#&&&&&&&&&&%%%##(//(/*,,,.  ,/(/****,,,****,,,.  ..........,,.    .,,.,*//*,,,,,..,,,...,,,...,.,,,,.  ..,,,,,,,***********///*,,......... .\n" +
                    "***,,,,**//*********(**/(/** ./*..,//****,,***,,,.   ./#%###%%%%%(((//*****,,,,,,...  ..*((/*,,...,,****//**,......,....       *////(/*,,,,,,...,,.,,,,,....,/*,.....,,,.   .... ..,,,,,.       .... .**\n" +
                    "/**,,,.,*,...,,,,,,*/*,,,,**, .,,,,/#(**,*///*,,*,/#%%########/*/((((///**,,,,,,.......*((/*,,.... ........,,,,,,,,,...,,.....,,*(#/*/*,,,,,......,,...,,.. .,*,,,,,..,...   ......... .,,.,*,.       ,,\n" +
                    "/*,,,..,.,,,..      /*   .....,,,,//**//****,,/(##%#####((((*****,,******,*,...,,......,**,,....... ...............   .****///*/(#((***,,**,,.......*,,,..   ,*,,,,.**,*,.             .,,,,,.         .\n" +
                    "/***,..,*,,*,.,,...,**,..,,,,,,,,,***,.,,***//(%%######(/(/,**,,,,.*,,,,,,,,.....,.......,.....................,,,**,//////*///(%%#/,**,,*,**");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        while (true) {
            System.out.println("waiting for new connection");
            server.listen();
            server.run();
        }
    }

    public void listen() {
        try {
            Socket socket = serverSocket.accept();
            PrintWriter push = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader take = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String path = take.readLine();
            int sockets = Integer.parseInt(take.readLine());
            data = new Data(path, sockets);

            System.out.println("Path to selected directory: \t" + path);
            System.out.println("Number of threads: \t" + sockets);

            File root = new File(data.getPath());
            ForkJoinPool forkJoinPool = new ForkJoinPool(4);
            File[] files = root.listFiles();
            if (files == null) {
                System.err.println("Forbidden directory : " + root.getAbsolutePath());
            } else {
                DirAnalyzer task = new DirAnalyzer(data, root);
                forkJoinPool.invoke(task);

            }

            forkJoinPool.shutdown();

            push.println("doneSearching");
            push.println(data.getSize());
            push.println(data.getNumberFiles());
            push.println(data.getFiles().size());


            System.out.println("number of files including empty dirs: \t" + data.getFiles().size());
            System.out.println("number of files excluding empty dirs: \t" + data.getNumberFiles());
//            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void run() {
//        COUNTDOWNLATCH Riesenie
//        data.setGate(new CountDownLatch(data.getSockets()));
        if (data.getFiles().size() > 0) {
            System.out.println("starting sending");
            ExecutorService executor = Executors.newFixedThreadPool(data.getSockets());
            CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);
            int number = data.getSockets();
            int i = 0;
            for (; i < number; i++) {
                SendFile task = new SendFile(data, serverSocket);
                completionService.submit(task);
            }
            System.out.println("spustili sme " + i + " taskov");
            for (int j = 0; j < number; j++) {
                try {
                    Boolean done = completionService.take().get();
                    System.out.println("this thread is done sending files" + done);
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                } catch (ExecutionException e) {
                    executor.shutdownNow();
                }
            }

            System.err.println("we are done now");

            executor.shutdown();
        }
    }

}
