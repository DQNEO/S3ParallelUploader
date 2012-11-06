package jp.dqneo.amazons3;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Command Line Interface
 *
 */
public class CLI {

    static private String endpoint = "https://s3-ap-northeast-1.amazonaws.com";

    /**
     * @param args baseDir , targetDir
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            throw new IllegalArgumentException("bucketName is not given");
        }

        if (args.length < 3) {
            throw new IllegalArgumentException("number of threads is not given");
        }

        String baseDir = args[0];
        String bucketName = args[1];

        int numThreads = Integer.parseInt(args[2]);
        if (numThreads < 1) {
            numThreads = 1;
        }

        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
                Uploader.class
                        .getResourceAsStream("AwsCredentials.properties")));

        s3.setEndpoint(endpoint);

        Uploader uploader = new Uploader(s3, bucketName, baseDir, numThreads);
        uploader.uploadFiles();
    }

}
