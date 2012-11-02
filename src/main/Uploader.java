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

    private static final int NUM_TASKS = 10;
    private static final int NUM_THREADS = 10;

    private static String bucketName = "my-first-s3-bucket-hogehogefoobar";


    private static final ExecutorService executorPool = Executors.newFixedThreadPool(NUM_THREADS);

    public static void main(String[] args) throws Exception {

		ArrayList<File> files = FileFinder.find(args[0]);
		System.out.println("===== files foud ========");
		for(File f :files) {
        	System.out.println(f);
        }

		uploadFiles(files);
    }

    public static void uploadFiles(ArrayList<File> files) throws Exception {
    	int count_success = 0;
        int count_failure = 0;

        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
        		Uploader.class
                        .getResourceAsStream("../sample/AwsCredentials.properties")));

        s3.setEndpoint("https://s3-ap-northeast-1.amazonaws.com");
        String bucket = bucketName;
        Collection<MyTask> collection = new ArrayList<MyTask>();

        for (int i = 0; i < NUM_TASKS; i++) {
        }

        for(File f :files) {
            MyTask myTask = new MyTask(s3, bucket, f);
            collection.add(myTask);
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

