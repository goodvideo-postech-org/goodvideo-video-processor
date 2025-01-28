package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProcessarVideoImpl implements ProcessarVideo {

  private final ZiparArquivo ziparArquivo;
  private final SalvarArquivo salvarArquivo;
  private final BaixarArquivo baixarArquivo;

  private final FinalizarProcessamento finalizarProcessamento;

  @Value("${process.ffmpeg-path}")
  private String ffmpegPath;

  @Value("${process.frames-path}")
  private String framesPath;
  
  @Override
  public void executar(final Processamento processamento) {
    try {
      System.out.println("FFMPEG path");
      System.out.println(ffmpegPath);
      FFmpeg ffmpeg = new FFmpeg(ffmpegPath);

      String downloadPath = baixarArquivo.executar(processamento);
      String outputDirectory = framesPath + processamento.getIdUsuario();

      new File(outputDirectory).mkdirs();

      String outputPattern = outputDirectory + "/frame_%04d.jpg";

      System.out.println("outputPattern" + outputPattern);
      System.out.println("outputDirectory" + outputDirectory);
      System.out.println("downloadPath" + downloadPath);
      System.out.println("DOING FFMPEG");

      FFmpegBuilder builder = new FFmpegBuilder()
              .setInput(downloadPath)
              .overrideOutputFiles(true)
              .addOutput(outputPattern)
              .setVideoFilter("fps=1/10")
              .done();

      ffmpeg.run(builder);

      System.out.println("FFMPEG DONE");

      String zipPath = ziparArquivo.executar(outputDirectory, processamento);
      System.out.println("zipPath" + zipPath);
      String filePathS3 = salvarArquivo.executar(zipPath, processamento);
      System.out.println("filePathS3" + filePathS3);

      finalizarProcessamento.executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.CONCLUIDO, filePathS3));
    } catch (IOException e) {
      System.out.println("error");
      System.out.println(e.getMessage());
      finalizarProcessamento.executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.ERRO, ""));
    } catch (ProcessamentoException e) {
      finalizarProcessamento.executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.ERRO, ""));
    }
  }
}
