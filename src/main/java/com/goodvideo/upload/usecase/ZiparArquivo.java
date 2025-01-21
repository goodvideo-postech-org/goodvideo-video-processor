package com.goodvideo.upload.usecase;

import com.goodvideo.upload.domains.Processamento;

public interface ZiparArquivo {

  void executar(String directoryPath, Processamento idUsuario);
  
}
