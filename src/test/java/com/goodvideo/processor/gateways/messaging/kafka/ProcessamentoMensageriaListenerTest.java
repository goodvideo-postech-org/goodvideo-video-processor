package com.goodvideo.processor.gateways.messaging.kafka;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.usecase.ProcessarVideo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProcessamentoMensageriaListenerTest {

  @InjectMocks
  private ProcessamentoMensageriaListener provider;
  
  @Mock
  private ProcessarVideo processarVideo;
  
  @Test
  public void deveAtualizarStatus() {

    Processamento processamento = new Processamento(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "email@email.com",
            "cloud/video.mp4",
            "",
            Status.PROCESSANDO);
    
    String json = new Gson().toJson(processamento);
    
    provider.ler(json);
    
    verify(processarVideo).executar(any(Processamento.class));
  }
  
}
