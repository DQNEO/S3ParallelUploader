package sample;

public class Chdir {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		String dir;

		dir = System.getProperty("user.dir");
		System.out.println(dir);

		System.setProperty("user.dir", "c:\\tmp");

		dir = System.getProperty("user.dir");
		System.out.println(dir);

		System.setProperty("user.dir", "c:\\tmp");

		dir = System.getProperty("user.dir");
		System.out.println(dir);
	}

}
