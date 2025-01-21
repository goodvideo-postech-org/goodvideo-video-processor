package com.goodvideo.upload.gateways.messaging.kafka.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessamentoErroMensagem {

  public String idVideo;
  public String descricao;

  public ProcessamentoErroMensagem(ProcessamentoErroMensagem processamentoErroMensagem) {
    this.idVideo = processamentoErroMensagem.getIdVideo();
    this.descricao = processamentoErroMensagem.getDescricao();
  }
}
