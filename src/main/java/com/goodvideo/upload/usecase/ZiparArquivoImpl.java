package com.goodvideo.upload.usecase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.goodvideo.upload.domains.Processamento;
import com.goodvideo.upload.gateways.messaging.kafka.resources.ProcessamentoErroMensagem;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZiparArquivoImpl implements ZiparArquivo {

  private final SalvarArquivo salvarArquivo;
  private final EnviarErroProcessamento enviarErroProcessamento;
  
  @Override
  public void executar(final String directoryPath, Processamento processamento) {
    Path sourceFolder = Paths.get(directoryPath);
    final String outputPath = "C:/main/goodvideo-process/output/" + processamento.getIdUsuario() + "zipTest.zip";

    try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputPath))) {
      Files.walk(sourceFolder)
        .filter(path -> !Files.isDirectory(path)) // Only include files
        .forEach(path -> {
          String zipEntryName = sourceFolder.relativize(path).toString();
          try {
            zipOutputStream.putNextEntry(new ZipEntry(zipEntryName));
            Files.copy(path, zipOutputStream);
            zipOutputStream.closeEntry();
          } catch (IOException e) {
            System.err.println("Failed to add file to zip: " + path);
            enviarErroProcessamento.executar(new ProcessamentoErroMensagem(processamento.getId(), "Erro ao adicionar arquivo a zip: " + path + "\n Stacktrace: " + Arrays.toString(e.getStackTrace())));
          }
        });
    } catch (IOException e) {
      enviarErroProcessamento.executar(new ProcessamentoErroMensagem(processamento.getId(), "Erro ao criar zip.\n Stacktrace: " + Arrays.toString(e.getStackTrace())));
      return;
    }

    salvarArquivo.executar(outputPath, processamento);
  }

}
