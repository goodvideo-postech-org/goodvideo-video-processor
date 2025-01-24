package com.goodvideo.upload.usecase;

import com.goodvideo.upload.domains.Processamento;

public interface ProcessarVideo {

  void executar(final Processamento processamento);
  
}
