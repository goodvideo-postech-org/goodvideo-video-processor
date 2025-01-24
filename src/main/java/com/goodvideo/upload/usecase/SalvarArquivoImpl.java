package com.goodvideo.upload.usecase;

import com.goodvideo.upload.domains.Processamento;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalvarArquivoImpl implements SalvarArquivo {

  @Value("${aws.bucket}")
  private String bucketName;

  @Override
  public String executar(final String outputPath, final Processamento idUsuario) {
    return null;
  }

}
