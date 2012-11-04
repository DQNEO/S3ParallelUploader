package main;

import java.util.concurrent.Callable;

public class MyTask implements Callable<Boolean> {

    private UploadableFile file = null;
    private int index;

    public MyTask(UploadableFile file, int index) {
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

