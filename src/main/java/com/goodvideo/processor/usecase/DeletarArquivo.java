package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;

public interface DeletarArquivo {

  void executar(Processamento idUsuario);
  
}
