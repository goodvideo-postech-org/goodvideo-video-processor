package com.goodvideo.processor.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileInputStreamFactory {

    public static FileInputStream createFileInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
