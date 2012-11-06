package jp.dqneo.amazons3;

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

    private String targetDir;

    private static final long serialVersionUID = 1L;

    public UploadableFile(AmazonS3 s3, File baseDir, File file, String bucket, String targetDir) {
        super(file.getAbsolutePath());

        this.s3 = s3;
        this.baseDir = baseDir;
        this.bucket = bucket;

        if (targetDir == null) {
            this.targetDir = "";
        } else if (! targetDir.endsWith("/")) {
            this.targetDir = targetDir + "/";
        }
    }

    public String getS3Key() {
        String basePath = baseDir.getAbsolutePath().replace("\\", "/");
        String filePath = this.getAbsolutePath().replace("\\", "/");

        return targetDir +  filePath.replace(basePath + "/", "");
    }


    /**
    *  upload to S3
    */
    public void upload() {
        this.s3.putObject(new PutObjectRequest(bucket, getS3Key(), (File)this));
    }

}
