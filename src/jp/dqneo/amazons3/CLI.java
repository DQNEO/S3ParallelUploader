package jp.dqneo.amazons3;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/**
 * Command Line Interface
 *
 */
public class CLI {

    static private String endpoint = "https://s3-ap-northeast-1.amazonaws.com";

    /**
     * @param args
     *    baseDir : "c:\path\to\dir" ,
     *    targetDir "s3://path/to/dir/"
     * @example
     *     -v -t 100  c:\path\to\dir s3://path/to/dir/
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        int numThreads = 0;
        try {
            Options opt = new Options();

            opt.addOption("h", false, "Print help");
            opt.addOption("t", true, "number of threads");
            //opt.addOption("v", true, "The username to use");

            BasicParser parser = new BasicParser();
            CommandLine cl = parser.parse(opt, args);

            if ( cl.hasOption('h') ) {
                HelpFormatter f = new HelpFormatter();
                f.printHelp("OptionsTip", opt);
                System.exit(1);
            }

            if (cl.hasOption('t')) {
                numThreads = Integer.parseInt(cl.getOptionValue('t'));
                if (numThreads < 1) {
                    numThreads = 1;
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (args.length < 1) {
            throw new IllegalArgumentException("source directory is not given");
        }

        if (args.length < 2) {
            throw new IllegalArgumentException("target directory is not given");
        }

        String baseDir = args[0];
        String bucketName = args[1];


        System.out.println(baseDir +" , "+ bucketName +" , "+ numThreads);
        System.exit(0);

        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
                Uploader.class
                        .getResourceAsStream("AwsCredentials.properties")));

        s3.setEndpoint(endpoint);

        Uploader uploader = new Uploader(s3, bucketName, baseDir, numThreads);
        uploader.uploadFiles();
    }

}
