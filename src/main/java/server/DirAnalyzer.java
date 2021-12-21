package server;


import java.io.File;
import java.util.concurrent.RecursiveAction;


public class DirAnalyzer extends RecursiveAction {

    private Data data;
    private File dir;

    public DirAnalyzer(Data data, File dir) {
        this.data = data;
        this.dir = dir;
    }

    @Override
    protected void compute() {
        File[] files = dir.listFiles();
//        zakazany pristup
        if (files == null) {
            System.err.println("Forbidden directory : " + dir.getAbsolutePath());
        }
//        ak je to koncovy priecinok
        else{
            if (files.length == 0){
                data.getFiles().offer(dir);
            }else{
                for (File file: files) {
                    if (file.isDirectory()){
                       invokeAll(new DirAnalyzer(data, file));

                    }
                    else{
                        synchronized (this) {
                            data.getFiles().offer(file);
                            data.getNumberFiles().incrementAndGet();
                            data.getSize().addAndGet(file.length());
                        }
                    }

                }

            }


        }

    }
}
