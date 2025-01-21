package com.goodvideo.upload.gateways;

import com.goodvideo.upload.domains.Processamento;
import com.goodvideo.upload.gateways.messaging.kafka.resources.ProcessamentoErroMensagem;

public interface ProcessamentoMensageriaGateway {
  Processamento ler(String mensagem);

  void publicar(final ProcessamentoErroMensagem processamentoErro);
}
