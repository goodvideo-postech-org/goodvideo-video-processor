package com.goodvideo.processor.usecase;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

@Component
@RequiredArgsConstructor
public class SalvarArquivoImpl implements SalvarArquivo {

  private final AmazonS3 amazonS3;

  @Value("${aws.bucket}")
  private String bucketName;

  @Override
  public String executar(final String outputPath, final Processamento processamento) throws ProcessamentoException {
    try {
      File file = new File(outputPath);

      ObjectMetadata data = new ObjectMetadata();
      data.setContentType("application/zip");
      data.setContentLength(file.length());

      String filePathS3 = String.format("%s/%s/%s", processamento.getIdUsuario(), processamento.getIdVideo(), file.getName());

      amazonS3.putObject(new PutObjectRequest(bucketName, filePathS3, new FileInputStream(file), data));

      return filePathS3;

    } catch (Exception e) {
      throw new ProcessamentoException(
              String.format("Falha ao salvar arquivo, %s", e.getMessage()));
    }
  }

}
