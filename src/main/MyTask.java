package main;

import java.io.File;
import java.util.concurrent.Callable;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class MyTask implements Callable<Boolean> {

	private AmazonS3 s3 = null;
    private String bucket = null;
    private File file = null;

    public MyTask(AmazonS3 s3, String bucket, File file) {
    	this.s3 = s3;
        this.bucket = bucket;
        this.file = file;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            s3.putObject(new PutObjectRequest(bucket, file.getAbsolutePath(), file));
            System.out.println(file);
            return true;
        } catch (Exception e) {
            //return false;
        	throw e;
        }
    }
}

