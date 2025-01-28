package com.goodvideo.processor.gateways.messaging.kafka.resources;

import com.goodvideo.processor.domains.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalizarProcessamentoMensagem {

  public String idVideo;
  public Status status;
  public String diretorioZip;

  public FinalizarProcessamentoMensagem(FinalizarProcessamentoMensagem finalizarProcessamentoMensagem) {
    this.idVideo = finalizarProcessamentoMensagem.getIdVideo();
    this.status = finalizarProcessamentoMensagem.getStatus();
    this.diretorioZip = finalizarProcessamentoMensagem.getDiretorioZip();
  }
}
