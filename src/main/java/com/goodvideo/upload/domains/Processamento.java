package com.goodvideo.upload.domains;

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

    private String id;
    private String idUsuario;
    private String email;
    private String diretorio;
    private Status status;
    private LocalDateTime dataCriacao;

}
