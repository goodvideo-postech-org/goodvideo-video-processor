package com.goodvideo.processor.usecase;

import com.goodvideo.processor.gateways.FinalizarProcessamentoMensageriaGateway;
import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinalizarProcessamentoImpl implements FinalizarProcessamento {
  
  private final FinalizarProcessamentoMensageriaGateway processamentoMensageriaErro;
  
  @Override
  public void executar(FinalizarProcessamentoMensagem finalizarProcessamentoMensagem) {
    processamentoMensageriaErro.publicar(finalizarProcessamentoMensagem);
  }

}
