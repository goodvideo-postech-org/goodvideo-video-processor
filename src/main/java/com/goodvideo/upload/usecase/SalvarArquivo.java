package com.goodvideo.upload.usecase;

import com.goodvideo.upload.domains.Processamento;

public interface SalvarArquivo {

  String executar(String arquivo, Processamento idUsuario);
  
}
