package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Processamento;

public interface ProcessarVideo {

  void executar(final Processamento processamento);
  
}
