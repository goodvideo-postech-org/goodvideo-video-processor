package com.goodvideo.processor.factories;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Component
public class FileOutputStreamFactory {

    public FileOutputStream createFileOutputStream(String file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }
}
