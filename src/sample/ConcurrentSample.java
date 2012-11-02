package sample;
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
import com.amazonaws.services.s3.model.PutObjectRequest;

public class ConcurrentSample {

    private static final int NUM_TASKS = 100;
    private static final int NUM_THREADS = 100;

    private static final ExecutorService executorPool = Executors
            .newFixedThreadPool(NUM_THREADS);

    public static void main(String[] args) throws IOException {
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
                        .getResourceAsStream("AwsCredentials.properties")));

        s3.setEndpoint("https://s3-ap-northeast-1.amazonaws.com");

        String bucket = "my-first-s3-bucket-" + UUID.randomUUID();
        s3.createBucket(bucket);
    	return bucket;
    }

}




class MyTask implements Callable<Boolean> {
    private String bucket = null;
    private String key = null;

    public MyTask(String bucket, String key) {
        this.bucket = bucket;
        this.key = key;
    }

    @Override
    public Boolean call() {
        try {
            AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
                    MyTask.class
                            .getResourceAsStream("AwsCredentials.properties")));

            s3.putObject(new PutObjectRequest(bucket, key, createSampleFile()));
            System.out.println(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
}

