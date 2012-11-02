package main;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileFinderTest {

	static String baseDir = "C:/z/pleiades/workspace/S3ParallelUploader/testdirs";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ArrayList<File> files = FileFinder.find(baseDir + "/a");
		assertThat(files.size()   , is(4));
		assertThat(files.get(0), is(new File(baseDir + "/a/b/c/1.txt")));
		assertThat(files.get(1), is(new File(baseDir + "/a/b/c/2.txt")));
		assertThat(files.get(2), is(new File(baseDir + "/a/b/1.txt")));
		assertThat(files.get(3), is(new File(baseDir + "/a/b/2.txt")));
	}

}
