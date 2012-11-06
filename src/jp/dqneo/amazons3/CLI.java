package jp.dqneo.amazons3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     *    targetDir "s3://yourbucketname/to/dir/"
     * @example
     *     -v -t 100  c:\path\to\dir s3://yourbucketname/to/dir/
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String str = "2009year";

        String regex = "(\\d+)(y)";
        Pattern p1 = Pattern.compile(regex);

        Matcher m = p1.matcher(str);

        if (m.find()){
            int start = m.start();
            int end = m.end();
            System.out.println("whole:" + m.group());
            for (int i = 1 ; i <= m.groupCount(); i ++){
                System.out.println("[Group" + i + "] " + m.group(i));
            }
        }else{
            System.out.println("× " + str);
        }



        System.exit(0);
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

        String localDir = args[0];
        String bucketName = args[1];
        String targetDir = "";


        System.out.println(localDir +" , "+ bucketName +" , "+ numThreads);
        System.exit(0);

        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
                Uploader.class
                        .getResourceAsStream("AwsCredentials.properties")));

        s3.setEndpoint(endpoint);

        Uploader uploader = new Uploader(s3, localDir, bucketName, targetDir, numThreads);
        uploader.upload();
    }

}
