package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import com.goodvideo.processor.factories.FFmpegFactory;
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
  private final FFmpegFactory ffmpegFactory;

  @Value("${process.ffmpeg-path}")
  private String ffmpegPath;

  @Value("${process.frames-path}")
  private String framesPath;
  
  @Override
  public void executar(final Processamento processamento) {
    try {
      FFmpeg ffmpeg = ffmpegFactory.createFFmpeg(ffmpegPath);

      String downloadPath = baixarArquivo.executar(processamento);
      String outputDirectory = framesPath + processamento.getIdUsuario();

      new File(outputDirectory).mkdirs();

      String outputPattern = outputDirectory + "/frame_%04d.jpg";

      FFmpegBuilder builder = new FFmpegBuilder()
              .setInput(downloadPath)
              .overrideOutputFiles(true)
              .addOutput(outputPattern)
              .setVideoFilter("fps=1/10")
              .done();

      ffmpeg.run(builder);

      String zipPath = ziparArquivo.executar(outputDirectory, processamento);
      String filePathS3 = salvarArquivo.executar(zipPath, processamento);
      finalizarProcessamento.executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.CONCLUIDO, filePathS3));
    } catch (IOException | ProcessamentoException e) {
      finalizarProcessamento.executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.ERRO, ""));
    }
  }
}
