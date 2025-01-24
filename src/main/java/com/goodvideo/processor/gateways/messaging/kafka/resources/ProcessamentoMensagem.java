package com.goodvideo.processor.gateways.messaging.kafka.resources;

import com.goodvideo.processor.domains.Processamento;
import lombok.Data;

@Data
public class ProcessamentoMensagem {

  public String idVideo;
  public String diretorio;
  public String idUsuario;
  public String email;
   
  public ProcessamentoMensagem(final Processamento processamento) {
    this.idVideo = processamento.getIdVideo();
    this.diretorio = processamento.getDiretorio();
    this.email = processamento.getEmail();
    this.idUsuario = processamento.getIdUsuario();
  }
  
}
