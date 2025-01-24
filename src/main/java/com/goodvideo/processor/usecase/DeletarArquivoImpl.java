package com.goodvideo.processor.usecase;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class DeletarArquivoImpl implements DeletarArquivo {

  private final AmazonS3 amazonS3;

  @Value("${aws.bucket}")
  private String bucketName;

  @Override
  public void executar(final Processamento processamento) {
    System.out.println("pretending to delete");
    //amazonS3.deleteObject(bucketName, processamento.getDiretorio());
  }

}
