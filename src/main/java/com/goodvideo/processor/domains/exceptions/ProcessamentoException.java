package com.goodvideo.processor.domains.exceptions;

import java.io.Serial;

public class ProcessamentoException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -7650800379446359145L;

  public ProcessamentoException(String message) {
    super(message);
  }

  public ProcessamentoException(String message, Throwable e) {
    super(message, e);
  }
  
}
