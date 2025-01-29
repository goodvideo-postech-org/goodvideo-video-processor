

# GoodVideo Processor Application

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=goodvideo-postech-org_goodvideo-video-processor&metric=coverage)](https://sonarcloud.io/summary/new_code?id=goodvideo-postech-org_goodvideo-video-processor)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=goodvideo-postech-org_goodvideo-video-processor&metric=alert_status)](https://sonarcloud.io/dashboard?id=goodvideo-postech-org_goodvideo-video-processor)
![Bugs](https://sonarcloud.io/api/project_badges/measure?project=goodvideo-postech-org_goodvideo-video-processor&metric=bugs)


Esse projeto é responsável por processar vídeos enviados para o GoodVideo, pegando frames do vídeo a cada 10 segundos e salvando no Amazon S3 em formato zip.

## Features

### Fluxo geral da aplicação

- Recebe-se a mensagem por Kafka com o ID do vídeo a ser processado;
- Faz-se o download do vídeo do Amazon S3;
- Se utiliza o FFMPEG para um frame do vídeo a cada 10 segundos de vídeo;
- Salva-se os frames no Amazon S3 em formato zip;
- Envia-se uma mensagem por Kafka com o ID do vídeo e o link do zip no Amazon S3.
- Caso algum erro ocorra no meio do processo, envia-se uma mensagem por Kafka com o ID do vídeo e o erro ocorrido.

## Tecnologias Utilizadas
- Java
- Maven
- Spring Framework
- Kafka
- Amazon S3
- FFmpeg
- JUnit 5
- Mockito



