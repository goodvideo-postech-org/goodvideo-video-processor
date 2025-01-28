package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProcessarVideoImplTest {

//    @InjectMocks
//    private ProcessarVideoImpl processarVideo;
//
//    @Mock
//    private ZiparArquivo ziparArquivo;
//
//    @Mock
//    private SalvarArquivo salvarArquivo;
//
//    @Mock
//    private BaixarArquivo baixarArquivo;
//
//    @Mock
//    private FinalizarProcessamento finalizarProcessamento;
//
//    @Mock
//    private FFmpeg ffmpeg;
//
//    private Processamento processamento;
//
//    private static final String MOCK_FFMPEG_PATH = "/mock/ffmpeg";
//    private static final String MOCK_FRAMES_PATH = "/mock/frames/";
//    private static final String MOCK_DOWNLOAD_PATH = "/mock/download/file.mp4";
//    private static final String MOCK_ZIP_PATH = "/mock/zip/output.zip";
//    private static final String MOCK_FILE_PATH_S3 = "/mock/s3/file.zip";
//    private static final String MOCK_USER_ID = "user123";
//    private static final String MOCK_VIDEO_ID = "video123";
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Create mock Processamento
//        processamento = new Processamento();
//        processamento.setIdUsuario(MOCK_USER_ID);
//        processamento.setIdVideo(MOCK_VIDEO_ID);
//        processamento.setDiretorio(MOCK_DOWNLOAD_PATH);
//    }
//
//    @Test
//    void testExecutar_Success() throws Exception {
//        try (MockedConstruction<FFmpeg> mockedConstruction = Mockito.mockConstruction(FFmpeg.class)) {
//            // Arrange: Mock the behavior of the dependencies
//            when(baixarArquivo.executar(processamento)).thenReturn(MOCK_DOWNLOAD_PATH);
//            when(ziparArquivo.executar(any(String.class), eq(processamento))).thenReturn(MOCK_ZIP_PATH);
//            when(salvarArquivo.executar(MOCK_ZIP_PATH, processamento)).thenReturn(MOCK_FILE_PATH_S3);
//            doNothing().when(finalizarProcessamento).executar(any(FinalizarProcessamentoMensagem.class));
//
//            // Mock FFmpeg behavior
//            FFmpegBuilder ffmpegBuilder = mock(FFmpegBuilder.class);
//            when(new FFmpeg(MOCK_FFMPEG_PATH)).thenReturn(ffmpeg);
//            doNothing().when(ffmpeg).run(any(FFmpegBuilder.class));
//
//            // Act: Call the method
//            processarVideo.executar(processamento);
//
//            // Assert: Verify that all the methods were called as expected
//            verify(baixarArquivo, times(1)).executar(processamento);
//            verify(ziparArquivo, times(1)).executar(any(String.class), eq(processamento));
//            verify(salvarArquivo, times(1)).executar(MOCK_ZIP_PATH, processamento);
//            verify(finalizarProcessamento, times(1)).executar(any(FinalizarProcessamentoMensagem.class));
//        }
//    }
//
//    @Test
//    void testExecutar_FFmpegFailure() throws Exception {
//        try (MockedConstruction<FFmpeg> mockedConstruction = Mockito.mockConstruction(FFmpeg.class)) {
//            // Arrange: Mock the behavior of the dependencies
//            when(baixarArquivo.executar(processamento)).thenReturn(MOCK_DOWNLOAD_PATH);
//            when(ziparArquivo.executar(any(String.class), eq(processamento))).thenReturn(MOCK_ZIP_PATH);
//            when(salvarArquivo.executar(MOCK_ZIP_PATH, processamento)).thenReturn(MOCK_FILE_PATH_S3);
//            doNothing().when(finalizarProcessamento).executar(any(FinalizarProcessamentoMensagem.class));
//
//            // Mock FFmpeg behavior to throw an exception
//            FFmpegBuilder ffmpegBuilder = mock(FFmpegBuilder.class);
//            when(new FFmpeg(MOCK_FFMPEG_PATH)).thenReturn(ffmpeg);
//            doThrow(new IOException("FFmpeg error")).when(ffmpeg).run(any(FFmpegBuilder.class));
//
//            // Act: Call the method
//            processarVideo.executar(processamento);
//
//            // Assert: Verify that the finalize method was called with an error status
//            verify(finalizarProcessamento, times(1)).executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.ERRO, ""));
//        }
//    }
//
//    @Test
//    void testExecutar_ProcessamentoException() throws Exception {
//        try (MockedConstruction<FFmpeg> mockedConstruction = Mockito.mockConstruction(FFmpeg.class)) {
//            // Arrange: Mock the behavior of the dependencies
//            when(baixarArquivo.executar(processamento)).thenReturn(MOCK_DOWNLOAD_PATH);
//            when(ziparArquivo.executar(any(String.class), eq(processamento))).thenReturn(MOCK_ZIP_PATH);
//            when(salvarArquivo.executar(MOCK_ZIP_PATH, processamento)).thenReturn(MOCK_FILE_PATH_S3);
//            doNothing().when(finalizarProcessamento).executar(any(FinalizarProcessamentoMensagem.class));
//
//            // Mock FFmpeg behavior
//            FFmpegBuilder ffmpegBuilder = mock(FFmpegBuilder.class);
//            when(new FFmpeg(MOCK_FFMPEG_PATH)).thenReturn(ffmpeg);
//            doNothing().when(ffmpeg).run(any(FFmpegBuilder.class));
//
//            // Simulate an exception from the FinalizarProcessamento service
//            doThrow(new ProcessamentoException("Processing error")).when(finalizarProcessamento).executar(any(FinalizarProcessamentoMensagem.class));
//
//            // Act: Call the method
//            processarVideo.executar(processamento);
//
//            // Assert: Verify that the finalize method was called with an error status
//            verify(finalizarProcessamento, times(1)).executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.ERRO, ""));
//        }
//    }
}