package main;

import java.io.File;

public class MyFile extends File {

	private File baseDir;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MyFile(File baseDir, File file) {
		super(file.getAbsolutePath());
		this.baseDir = baseDir;
	}

	public String getS3Key() {
		String basePath = baseDir.getAbsolutePath().replace("\\", "/");
		String filePath = this.getAbsolutePath().replace("\\", "/");

		return filePath.replace(basePath + "/", "");
	}

}
