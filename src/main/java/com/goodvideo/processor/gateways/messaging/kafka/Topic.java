package com.goodvideo.processor.gateways.messaging.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Topic {

  FINALIZAR_PROCESSAMENTO("com.goodvideo.upload.finalizar_processamento.v1");

  public String description;
}
