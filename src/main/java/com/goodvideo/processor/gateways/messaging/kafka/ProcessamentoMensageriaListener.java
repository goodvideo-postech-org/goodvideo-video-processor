package com.goodvideo.processor.gateways.messaging.kafka;

import com.goodvideo.processor.usecase.ProcessarVideo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.goodvideo.processor.domains.Processamento;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProcessamentoMensageriaListener {

  private final ProcessarVideo processarVideo;

  @KafkaListener(topics = {"com.goodvideo.upload.processamento-video.v1"})
  public Processamento ler(String mensagem) {
    try {
      final Processamento processamento = new Gson().fromJson(mensagem, Processamento.class);
      processarVideo.executar(processamento);

    } catch (Exception e) {
      System.out.println("Erro ao fazer parse do json");
    }

    return null;
  }
}
