package com.goodvideo.processor.gateways.api;

import java.io.IOException;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.usecase.ProcessarVideo;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("processamento")
@RequiredArgsConstructor
@Api(value = "/processamento")
public class ProcessarVideoController {

  private final ProcessarVideo processarVideo;

  @PostMapping(path = "/process",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<String> process(@RequestHeader(name = "authorization") String auth, String caminhoArquivo) throws IOException {
    processarVideo.executar(Processamento.builder().diretorio(caminhoArquivo).idVideo("asdqwe").idUsuario("usuarioTeste").build());
    return ResponseEntity.ok("OK");
  }

  @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
          @ApiResponse(code = 201, message = "Created")})
  @GetMapping(path = "/test")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "idPedido", value = "Identificador do pedido", required = true,
                  dataType = "string", paramType = "body"),
          @ApiImplicitParam(name = "valorPedido", value = "Valor do pedido", required = true,
                  dataType = "bigDecimal", paramType = "body")})
  public String hello() {
    return "Hello, World!";
  }
}
