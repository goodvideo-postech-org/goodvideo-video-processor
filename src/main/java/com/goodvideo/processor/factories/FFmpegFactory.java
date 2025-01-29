package com.goodvideo.processor.factories;

import net.bramp.ffmpeg.FFmpeg;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FFmpegFactory {

    public FFmpeg createFFmpeg(String ffmpegPath) throws IOException {
       return new FFmpeg(ffmpegPath);
    }
}
