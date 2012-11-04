package main;

import java.io.File;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;


/**
 * @author DQNEO
 *
 */
public class UploadableFile extends File {

    private AmazonS3 s3;

    /**
     *  target bucket
     */
    private String bucket = null;

    private File baseDir;

    private static final long serialVersionUID = 1L;

    public UploadableFile(AmazonS3 s3, String bucket, File baseDir, File file) {
        super(file.getAbsolutePath());

        this.s3 = s3;
        this.bucket = bucket;
        this.baseDir = baseDir;
    }

    public String getS3Key() {
        String basePath = baseDir.getAbsolutePath().replace("\\", "/");
        String filePath = this.getAbsolutePath().replace("\\", "/");

        return filePath.replace(basePath + "/", "");
    }


    /**
    *  upload to S3
    */
    public void upload() {
        this.s3.putObject(new PutObjectRequest(bucket, getS3Key(), (File)this));
    }

}
