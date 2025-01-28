package com.goodvideo.processor.domains;

import lombok.Getter;

@Getter
public enum Status {

    PROCESSANDO,
    CONCLUIDO,
    ERRO;

    public Status getByString(final String status){
        return Status.valueOf(status);
    }

}
