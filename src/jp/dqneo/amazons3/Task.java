package jp.dqneo.amazons3;

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
        Logger.printf("[%d] %s <= %s\n",  index, file.getS3Key(), file.getAbsolutePath());
        return true;
    }
}

