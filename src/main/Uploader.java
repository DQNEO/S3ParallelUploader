package main;

import java.io.File;
import java.util.ArrayList;

public class Uploader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		ArrayList<File> files = FileFinder.find(args[0]);

		for(File f :files) {
			System.out.println(f);
		}
	}

}
