package com.goodvideo.upload.usecase;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SampleListener {

  @KafkaListener(topics = {"com.goodvideo.upload.processamento-video.v1"})
  public void getMessage(String message) {
    System.out.println("Topic lido, mensagem: " + message);
  }
  
}
