package main;

public class Timer {

	private long startTime;

	public Timer() {
		startTime = System.currentTimeMillis();
	}

	public long getElapsedTime() {
		return (System.currentTimeMillis() - startTime);
	}

}
