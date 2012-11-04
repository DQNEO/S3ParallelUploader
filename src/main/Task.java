package main;

import java.util.concurrent.Callable;

public class Task implements Callable<Boolean> {

    private UploadableFile file = null;
    private int index;

    public Task(UploadableFile file, int index) {
        this.file = file;
        this.index = index;
    }

    @Override
    public Boolean call() {
    	file.upload();
        System.out.printf("[%d] %s <= %s\n",  index, file.getS3Key(), file);
        return true;
    }
}

