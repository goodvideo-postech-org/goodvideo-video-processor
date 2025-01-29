package com.goodvideo.processor.factories;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

@Component
public class ZipOutputStreamFactory {

    public ZipOutputStream createZipOutputStream(FileOutputStream fos) {
        return new ZipOutputStream(fos);
    }
}
