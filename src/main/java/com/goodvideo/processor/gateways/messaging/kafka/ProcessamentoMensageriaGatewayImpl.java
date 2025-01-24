package com.goodvideo.processor.gateways.messaging.kafka;

import com.goodvideo.processor.usecase.ProcessarVideo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.gateways.ProcessamentoMensageriaGateway;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProcessamentoMensageriaGatewayImpl implements ProcessamentoMensageriaGateway {

  private final ProcessarVideo processarVideo;

  @Override
  @KafkaListener(topics = {"com.goodvideo.upload.processamento-video.v1"})
  public Processamento ler(String mensagem) {
    try {
      System.out.println("MENSAGEM ABAIXO");
      System.out.println(mensagem);
      final Processamento processamento = new Gson().fromJson(mensagem, Processamento.class);
      processarVideo.executar(processamento);

    } catch (Exception e) {
      System.out.println("Erro ao fazer parse do json");
    }

    return null;
  }
}
