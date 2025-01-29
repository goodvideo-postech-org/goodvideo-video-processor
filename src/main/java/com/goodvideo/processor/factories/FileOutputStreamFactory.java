package com.goodvideo.processor.factories;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileOutputStreamFactory {

    public FileOutputStream createFileOutputStream(String file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }
}
