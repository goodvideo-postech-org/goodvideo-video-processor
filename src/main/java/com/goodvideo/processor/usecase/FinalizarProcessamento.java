package com.goodvideo.processor.usecase;

import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;

public interface FinalizarProcessamento {

  void executar(final FinalizarProcessamentoMensagem finalizarProcessamentoMensagem);
  
}
