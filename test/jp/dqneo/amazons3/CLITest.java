package jp.dqneo.amazons3;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CLITest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parseS3path() throws Exception {
        String[] strs = CLI.parseS3path( "s3://yourbucketname/to/dir/");

        assertThat(strs[0], is("yourbucketname"));
        assertThat(strs[1], is("to/dir/"));

        strs = CLI.parseS3path("s3://yourbucketname/to/dir");

        assertThat(strs[0], is("yourbucketname"));
        assertThat(strs[1], is("to/dir/"));

    }

}
