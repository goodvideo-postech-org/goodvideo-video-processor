package com.goodvideo.processor.gateways;

import com.goodvideo.processor.domains.Processamento;

public interface ProcessamentoMensageriaGateway {
  Processamento ler(String mensagem);
}
