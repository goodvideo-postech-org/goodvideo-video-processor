package com.goodvideo.processor.usecase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import com.goodvideo.processor.factories.FileOutputStreamFactory;
import com.goodvideo.processor.factories.ZipOutputStreamFactory;
import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZiparArquivoImpl implements ZiparArquivo {

  private final FinalizarProcessamento finalizarProcessamento;
  private final FileOutputStreamFactory fileOutputStreamFactory;
  private final ZipOutputStreamFactory zipOutputStreamFactory;

  @Value("${process.zip-path}")
  private String zipPath;

  @Override
  public String executar(final String directoryPath, Processamento processamento) throws ProcessamentoException {
    Path sourceFolder = Paths.get(directoryPath);

    String zipUserPath = zipPath + processamento.getIdUsuario();
    final String outputPath = zipUserPath + "/frames.zip";

    new File(zipUserPath).mkdirs();

    try (ZipOutputStream zipOutputStream = zipOutputStreamFactory.createZipOutputStream(fileOutputStreamFactory.createFileOutputStream(outputPath))) {
      Files.walk(sourceFolder)
        .filter(path -> !Files.isDirectory(path))
        .forEach(path -> {
          String zipEntryName = sourceFolder.relativize(path).toString();
          try {
            zipOutputStream.putNextEntry(new ZipEntry(zipEntryName));
            Files.copy(path, zipOutputStream);
            zipOutputStream.closeEntry();
          } catch (IOException e) {
            System.err.println("Failed to add file to zip: " + path);
            throw new ProcessamentoException("Erro ao zipar arquivo: " + e.getMessage());
          }
        });
    } catch (IOException e) {
      finalizarProcessamento.executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.ERRO, ""));
      throw new ProcessamentoException("Erro ao zipar arquivo: " + e.getMessage());
    }

    return outputPath;

  }

}
