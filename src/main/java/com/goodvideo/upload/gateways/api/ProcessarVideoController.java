package com.goodvideo.upload.gateways.api;

import java.io.IOException;

import com.goodvideo.upload.domains.Processamento;
import com.goodvideo.upload.usecase.ProcessarVideo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("processamento")
@RequiredArgsConstructor
public class ProcessarVideoController {

  private final ProcessarVideo processarVideo;

  @PostMapping(path = "/process",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<String> process(@RequestHeader(name = "authorization") String auth, String caminhoArquivo) throws IOException {
    processarVideo.executar(Processamento.builder().diretorio(caminhoArquivo).id("asdqwe").idUsuario("usuarioTeste").build());
    return ResponseEntity.ok("OK");
  }
}
