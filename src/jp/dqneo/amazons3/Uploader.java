package jp.dqneo.amazons3;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonaws.services.s3.AmazonS3;

/**
 * main class of Uploader
 *
 */
public class Uploader {

    private int numThreads;

    private AmazonS3 s3;

    private String bucketName;

    private File localDir;

    private String targetDir;

    private static int count_success = 0;
    private static int count_failure = 0;

    private static Timer timer;


    public Uploader(AmazonS3 s3, String localDir,  String bucketName,  String targetDir, int numThreads) throws Exception {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.localDir = checkBaseDir(localDir);
        this.targetDir = targetDir;
        this.numThreads = numThreads;
    }

    private File checkBaseDir(String path) throws Exception {
        File dir  =new File(path);
        if (!  dir.isDirectory()) {
            throw new Exception("directory not found:" + path);
        }
        return dir;
    }

    public void upload() throws Exception {

        ArrayList<File> files = FileFinder.find(localDir);
        Logger.printf("===== [%d] files foud ========\n", files.size());

        Collection<Task> tasks = new ArrayList<Task>();

        int index = 1;
        for(File file :files) {
            tasks.add(new Task(new UploadableFile(s3, localDir, file,  bucketName, targetDir), index++));
        }

        Logger.log("===== Start uploading ========");
        timer = new Timer();

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);

        //maybe we'd better try-catch below?
        List<Future<Boolean>> list = threadPool.invokeAll(tasks);

        for (Future<Boolean> fut : list) {
            if(fut.get()) {
                count_success++;
            } else {
                count_failure++;
            }
        }
        threadPool.shutdown();

        end(files.size());
    }

    /**
    * @param countFiles
    */
    public void end(int countFiles) {
        long elapsedTime = timer.getElapsedTime();

        Logger.log("Number of threas - "    + numThreads);
        Logger.log("TOTAL SUCCESS - " + count_success);
        Logger.log("TOTAL FAILURE - " + count_failure);
        Logger.log("Total time - "    + elapsedTime + " ms");
        Logger.log("Millsec per file - " + ( elapsedTime / countFiles));
    }

}

