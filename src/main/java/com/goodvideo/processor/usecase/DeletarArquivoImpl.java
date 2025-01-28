package com.goodvideo.processor.usecase;

import com.amazonaws.services.s3.AmazonS3;
import com.goodvideo.processor.domains.Processamento;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarArquivoImpl implements DeletarArquivo {

  private final AmazonS3 amazonS3;

  @Value("${aws.bucket}")
  private String bucketName;

  @Override
  public void executar(final Processamento processamento) {
    amazonS3.deleteObject(bucketName, processamento.getDiretorio());
  }

}
