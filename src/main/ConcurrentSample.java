package main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class ConcurrentSample {

    private static final int NUM_TASKS = 10;
    private static final int NUM_THREADS = 10;

    private static String bucketName = "my-first-s3-bucket-hogehogefoobar";


    private static final ExecutorService executorPool = Executors.newFixedThreadPool(NUM_THREADS);

    public static void main(String[] args) throws IOException {
    	upload();
    }

    public static void upload() throws IOException {
    	int count_success = 0;
        int count_failure = 0;

        String bucket = getBucket();
        Collection<MyTask> collection = new ArrayList<MyTask>();

        for (int i = 0; i < NUM_TASKS; i++) {
            String uniqueKey = "file-" + UUID.randomUUID();
            MyTask myTask = new MyTask(bucket, uniqueKey);
            collection.add(myTask);
        }

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
        } finally {
        	long elapsedTime = (System.currentTimeMillis() - startTime);

        	System.out.println("TOTAL SUCCESS - " + count_success);
            System.out.println("TOTAL FAILURE - " + count_failure);
            System.out.println("Total time - "    + elapsedTime + " ms");

            executorPool.shutdown();
        }
    }

    private static String getBucket() throws IOException {
        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
        		ConcurrentSample.class
                        .getResourceAsStream("../sample/AwsCredentials.properties")));

        s3.setEndpoint("https://s3-ap-northeast-1.amazonaws.com");

        s3.createBucket(bucketName);
    	return bucketName;
    }

}

