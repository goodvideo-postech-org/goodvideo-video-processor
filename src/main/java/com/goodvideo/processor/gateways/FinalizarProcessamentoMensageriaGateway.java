package com.goodvideo.processor.gateways;

import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;

public interface FinalizarProcessamentoMensageriaGateway {
  void publicar(final FinalizarProcessamentoMensagem processamentoErro);
}
