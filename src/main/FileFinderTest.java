package main;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileFinderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		FileFinder finder = new FileFinder();
		ArrayList<File> files = finder.getAllFiles(".");
		assertThat(files.size()   , is(1));
	}

}
