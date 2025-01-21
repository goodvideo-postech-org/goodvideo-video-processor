package com.goodvideo.upload.gateways.messaging.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Topic {

  ERRO_PROCESSAMENTO("com.goodvideo.upload.erro_processamento.v1");

  public String description;
}
