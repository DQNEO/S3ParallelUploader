package main;

import java.io.File;
import java.util.concurrent.Callable;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class MyTask implements Callable<Boolean> {

	private AmazonS3 s3 = null;
    private String bucket = null;
    private MyFile file = null;
    private int index;

    public MyTask(AmazonS3 s3, String bucket, MyFile file, int index) {
    	this.s3 = s3;
        this.bucket = bucket;
        this.file = file;
        this.index = index;
    }

    @Override
    public Boolean call() {
    	String key = file.getS3Key();
        s3.putObject(new PutObjectRequest(bucket, key, file));

        System.out.printf("[%d] %s <= %s\n",  index, key, file);
        return true;
    }
}

