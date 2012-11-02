package main;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyFileTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		MyFile myFile1 = new MyFile(new File("c:\\tmp\basedir"), new File("c:\\tmp\basedir\\foo\\file.txt"));
		assertThat(myFile1.getS3Key(), is("foo/file.txt"));

		MyFile myFile2 = new MyFile(new File("c:\\tmp\basedir"), new File("c:\\tmp\basedir\\bar\\file.txt"));
		assertThat(myFile2.getS3Key(), is("bar/file.txt"));

		MyFile myFile3 = new MyFile(new File("c:\\tmp\basedir"), new File("c:\\tmp\basedir\\file.txt"));
		assertThat(myFile3.getS3Key(), is("file.txt"));
}

}
