package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class FileFinder {


	private static ArrayList<File> files = new ArrayList<File>();

	public static  ArrayList<File> find(String path) {
		_find(path);
		return files;
	}

	/**
	 * 引数で渡されたディレクトリ以下を再帰的に検索します。
	 *
	 * @param path ディレクトリパス
	 * @return bool isFile
	 */
	private static boolean _find(String path) {
		File file = new File(path);

		File[] listFiles = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// ドットで始まるファイルは対象外
				if (name.startsWith(".")) {
					return false;
				}
				// クラスファイルは対象外
				if (name.endsWith(".class")) {
					return false;
				}
				// 対象要素の絶対パスを取得
				String absolutePath = dir.getAbsolutePath()
										+ File.separator + name;
				if (new File(absolutePath).isFile()) {
					return true;
				}
				else {
					// ディレクトリの場合、再び同一メソッドを呼出す。
					return _find(absolutePath);
				}
			}
		});

		for (File f : listFiles) {
			if (f.isFile()) {
				files.add(f);
			}
		}
		return true;
	}
}