package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;

public interface SalvarArquivo {

  String executar(String arquivo, Processamento idUsuario) throws ProcessamentoException;
  
}
