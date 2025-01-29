package com.goodvideo.processor.factories;

import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamFactory {

    public ZipOutputStream createZipOutputStream(FileOutputStream fos) {
        return new ZipOutputStream(fos);
    }
}
