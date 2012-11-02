package main;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class Uploader {

    private static final int NUM_THREADS = 100;

    private static String bucketName = "my-first-s3-bucket-hogehogefoobar";
    private static String endpoint = "https://s3-ap-northeast-1.amazonaws.com";

    private static final ExecutorService executorPool = Executors.newFixedThreadPool(NUM_THREADS);

    public static void main(String[] args) throws Exception {

    	File baseDir = checkBaseDir(args[0]);

		ArrayList<File> files = FileFinder.find(baseDir.getPath());
		System.out.println("===== files foud ========");
		for(File file :files) {
        	System.out.println(file);
        }

		uploadFiles(files);
    }

    private static File checkBaseDir(String path) throws Exception {
    	File dir  =new File(path);
    	if (!  dir.isDirectory()) {
    		throw new Exception("directory not found:" + path);
    	}
    	return dir;
    }

    public static void uploadFiles(ArrayList<File> files) throws Exception {
    	int count_success = 0;
        int count_failure = 0;

        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
        		Uploader.class
                        .getResourceAsStream("../sample/AwsCredentials.properties")));

        s3.setEndpoint(endpoint);
        Collection<MyTask> collection = new ArrayList<MyTask>();


        int index = 1;
        for(File file :files) {
            collection.add( new MyTask(s3, bucketName, file, index++));
		}


		System.out.println("===== Start uploading ========");
        long startTime = System.currentTimeMillis();

        try {
            List<Future<Boolean>> list = executorPool.invokeAll(collection);
            for (Future<Boolean> fut : list) {
                if(fut.get()) {
                	count_success++;
                } else {
                	count_failure++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        	long elapsedTime = (System.currentTimeMillis() - startTime);

        	System.out.println("TOTAL SUCCESS - " + count_success);
            System.out.println("TOTAL FAILURE - " + count_failure);
            System.out.println("Total time - "    + elapsedTime + " ms");

            executorPool.shutdown();
        }
    }

}

