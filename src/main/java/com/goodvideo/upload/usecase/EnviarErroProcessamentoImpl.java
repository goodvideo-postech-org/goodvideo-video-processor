package com.goodvideo.upload.usecase;

import com.goodvideo.upload.gateways.ProcessamentoMensageriaGateway;
import com.goodvideo.upload.gateways.messaging.kafka.resources.ProcessamentoErroMensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnviarErroProcessamentoImpl implements EnviarErroProcessamento {
  
  private final ProcessamentoMensageriaGateway processamentoMensageria;
  
  @Override
  public void executar(ProcessamentoErroMensagem processamentoErroMensagem) {
    processamentoMensageria.publicar(processamentoErroMensagem);
  }

}
