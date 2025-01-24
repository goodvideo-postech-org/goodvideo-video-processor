package com.goodvideo.processor.gateways.messaging.kafka;

import com.goodvideo.processor.gateways.FinalizarProcessamentoMensageriaGateway;
import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinalizarProcessamentoMensageriaGatewayImpl implements FinalizarProcessamentoMensageriaGateway {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void publicar(FinalizarProcessamentoMensagem finalizarProcessamentoMensagem) {
    kafkaTemplate.send(Topic.FINALIZAR_PROCESSAMENTO.getDescription(), new Gson().toJson(new FinalizarProcessamentoMensagem(finalizarProcessamentoMensagem)));
  }
}
