package com.goodvideo.upload.gateways.messaging.kafka.resources;

import com.goodvideo.upload.domains.Processamento;
import lombok.Data;

@Data
public class ProcessamentoMensagem {

  public String idVideo;
  public String diretorio;
  public String idUsuario;
  public String email;
   
  public ProcessamentoMensagem(final Processamento processamento) {
    this.idVideo = processamento.getId();
    this.diretorio = processamento.getDiretorio();
    this.email = processamento.getEmail();
    this.idUsuario = processamento.getIdUsuario();
  }
  
}
