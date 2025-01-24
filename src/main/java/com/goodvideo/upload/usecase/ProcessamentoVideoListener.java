package com.goodvideo.upload.usecase;

import com.google.gson.Gson;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProcessamentoVideoListener {

  @KafkaListener(topics = {"com.goodvideo.upload.processamento-video.v1"})
  public void ler(String message) {

    try {
      final MineTest mine = new Gson().fromJson(message, MineTest.class);
    } catch (Exception e) {

    }

  }

}

class MineTest {

  public String id;
  public String valor;
}
