package com.goodvideo.upload.gateways.messaging.kafka;

import com.goodvideo.upload.gateways.messaging.kafka.resources.ProcessamentoErroMensagem;
import com.goodvideo.upload.usecase.EnviarErroProcessamento;
import com.goodvideo.upload.usecase.ProcessarVideo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.goodvideo.upload.domains.Processamento;
import com.goodvideo.upload.gateways.ProcessamentoMensageriaGateway;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProcessamentoMensageriaGatewayImpl implements ProcessamentoMensageriaGateway {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final EnviarErroProcessamento enviarErroProcessamento;
  private final ProcessarVideo processarVideo;

  @Override
  @KafkaListener(topics = {"com.goodvideo.upload.processamento-video.v1"})
  public Processamento ler(String mensagem) {
    try {
      final Processamento processamento = new Gson().fromJson(mensagem, Processamento.class);
      processarVideo.executar(processamento);

    } catch (Exception e) {
      enviarErroProcessamento.executar(new ProcessamentoErroMensagem("IDK ID", e.getMessage()));
    }
    return null;
  }

  @Override
  public void publicar(ProcessamentoErroMensagem processamentoErro) {
    kafkaTemplate.send(Topic.ERRO_PROCESSAMENTO.getDescription(), new Gson().toJson(new ProcessamentoErroMensagem(processamentoErro)));
  }
}
