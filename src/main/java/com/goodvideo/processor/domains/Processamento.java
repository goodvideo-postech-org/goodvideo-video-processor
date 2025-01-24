package com.goodvideo.processor.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Processamento {

    private String idVideo;
    private String idUsuario;
    private String email;
    private String diretorio;
    private String diretorioZip;
    private Status status;

}
