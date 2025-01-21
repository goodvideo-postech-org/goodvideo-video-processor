package com.goodvideo.upload.usecase;

import com.goodvideo.upload.gateways.messaging.kafka.resources.ProcessamentoErroMensagem;

public interface EnviarErroProcessamento {

  void executar(final ProcessamentoErroMensagem processamentoErroMensagem);
  
}
