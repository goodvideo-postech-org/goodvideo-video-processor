package com.goodvideo.upload.usecase;

import com.goodvideo.upload.domains.Processamento;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProcessarVideoImpl implements ProcessarVideo {

  private final ZiparArquivo ziparArquivo;

  @Value("${process.ffmpeg-path}")
  private String ffmpegPath;

  @Value("${process.output-path}")
  private String outputPath;
  
  @Override
  public void executar(final Processamento processamento) {

    try {

      FFmpeg ffmpeg = new FFmpeg("C:/main/ffmpeg-test/ffmpeg-binaries/ffmpeg.exe");

      String videoPath = processamento.getDiretorio();

      String outputDirectory = "C:/main/videos/output/" + processamento.getIdUsuario();
      String outputPattern = outputDirectory + "/frame_%04d.jpg";

      FFmpegBuilder builder = new FFmpegBuilder()
              .setInput(videoPath)
              .overrideOutputFiles(true)
              .addOutput(outputPattern)
              .setVideoFilter("fps=1/10")
              .done();

      ffmpeg.run(builder);

      ziparArquivo.executar(outputDirectory, processamento);

      System.out.println("Frames extracted successfully!");
    } catch (IOException e) {
      System.out.println("error");
      System.out.println(e.getMessage());
    }
  }
}
