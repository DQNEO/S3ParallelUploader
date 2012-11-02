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

    private static final int CLIENTS = 100;
    private static final int THREADS = 100;

    private static final ExecutorService executorPool = Executors
            .newFixedThreadPool(THREADS);

    public static void main(String[] args) throws IOException {
        int success = 0;
        int failure = 0;

        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
        		ConcurrentSample.class
                        .getResourceAsStream("AwsCredentials.properties")));

        s3.setEndpoint("https://s3-ap-northeast-1.amazonaws.com");

        String bucket = "my-first-s3-bucket-" + UUID.randomUUID();
        String key = "MyObjectKey";

        s3.createBucket(bucket);

        Collection<S3UploadTask> collection = new ArrayList<S3UploadTask>();
        for (int i = 0; i < CLIENTS; i++) {
            S3UploadTask task = new S3UploadTask(bucket, key
                    + UUID.randomUUID());
            collection.add(task);
        }

        long startTime = System.currentTimeMillis();
        try {
            List<Future<Boolean>> list = executorPool.invokeAll(collection);
            for (Future<Boolean> fut : list) {
                int ignore = fut.get() ? success++ : failure++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("TOTAL SUCCESS - " + success);
            System.out.println("TOTAL FAILURE - " + failure);
            System.out.println("Total time - "
                    + (System.currentTimeMillis() - startTime) + " ms");
            executorPool.shutdown();
        }
    }
}


class S3UploadTask implements Callable<Boolean> {
    private String bucket = null;
    private String key = null;

    public S3UploadTask(String bucket, String key) {
        this.bucket = bucket;
        this.key = key;
    }

    @Override
    public Boolean call() {
        try {
            AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
                    S3UploadTask.class
                            .getResourceAsStream("AwsCredentials.properties")));

            s3.putObject(new PutObjectRequest(bucket, key, createSampleFile()));

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

