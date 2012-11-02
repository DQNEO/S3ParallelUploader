package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.Callable;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class MyTask implements Callable<Boolean> {
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

