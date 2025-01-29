package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.factories.FFmpegFactory;
import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessarVideoImplTest {

    private ProcessarVideoImpl processarVideo;

    @Mock
    private ZiparArquivo ziparArquivo;

    @Mock
    private SalvarArquivo salvarArquivo;

    @Mock
    private BaixarArquivo baixarArquivo;

    @Mock
    private FinalizarProcessamento finalizarProcessamento;

    @Mock
    private FFmpegFactory fFmpegFactory;

    @Mock
    private FFmpeg ffmpeg;

    private Processamento processamento;

    private static final String MOCK_FFMPEG_PATH = "/mock/ffmpeg";
    private static final String MOCK_FRAMES_PATH = "/mock/frames/";
    private static final String MOCK_DOWNLOAD_PATH = "/mock/download/file.mp4";
    private static final String MOCK_ZIP_PATH = "/mock/zip/output.zip";
    private static final String MOCK_FILE_PATH_S3 = "/mock/s3/file.zip";
    private static final String MOCK_USER_ID = "user123";
    private static final String MOCK_VIDEO_ID = "video123";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        // Instantiate the class with mocks
        processarVideo = new ProcessarVideoImpl(ziparArquivo, salvarArquivo, baixarArquivo, finalizarProcessamento, fFmpegFactory);

        // Create mock Processamento
        processamento = new Processamento();
        processamento.setIdUsuario(MOCK_USER_ID);
        processamento.setIdVideo(MOCK_VIDEO_ID);
        processamento.setDiretorio(MOCK_DOWNLOAD_PATH);

        Field ffmpegPathField = ProcessarVideoImpl.class.getDeclaredField("ffmpegPath");
        ffmpegPathField.setAccessible(true);
        ffmpegPathField.set(processarVideo, MOCK_ZIP_PATH);

        Field framesPathField = ProcessarVideoImpl.class.getDeclaredField("framesPath");
        framesPathField.setAccessible(true);
        framesPathField.set(processarVideo, MOCK_FRAMES_PATH);
    }

    @Test
    void testExecutar_Success() throws Exception {
        // Arrange: Mock the behavior of the dependencies
        when(baixarArquivo.executar(processamento)).thenReturn(MOCK_DOWNLOAD_PATH);
        when(ziparArquivo.executar(any(String.class), eq(processamento))).thenReturn(MOCK_ZIP_PATH);
        when(salvarArquivo.executar(MOCK_ZIP_PATH, processamento)).thenReturn(MOCK_FILE_PATH_S3);
        doNothing().when(finalizarProcessamento).executar(any(FinalizarProcessamentoMensagem.class));

        when(fFmpegFactory.createFFmpeg(MOCK_ZIP_PATH)).thenReturn(ffmpeg);
        doNothing().when(ffmpeg).run(any(FFmpegBuilder.class));

        // Act: Call the method
        processarVideo.executar(processamento);

        // Assert: Verify that all the methods were called as expected
        verify(baixarArquivo, times(1)).executar(processamento);
        verify(ziparArquivo, times(1)).executar(any(String.class), eq(processamento));
        verify(salvarArquivo, times(1)).executar(MOCK_ZIP_PATH, processamento);
        verify(finalizarProcessamento, times(1)).executar(any(FinalizarProcessamentoMensagem.class));
    }

    @Test
    void testExecutar_FFmpegFailure() throws Exception {
        // Arrange: Mock the behavior of the dependencies
        when(baixarArquivo.executar(processamento)).thenReturn(MOCK_DOWNLOAD_PATH);
        doNothing().when(finalizarProcessamento).executar(any(FinalizarProcessamentoMensagem.class));

        when(fFmpegFactory.createFFmpeg(MOCK_ZIP_PATH)).thenReturn(ffmpeg);
        doThrow(new IOException("FFmpeg error")).when(ffmpeg).run(any(FFmpegBuilder.class));

        // Act: Call the method
        processarVideo.executar(processamento);

        // Assert: Verify that the finalize method was called with an error status
        verify(finalizarProcessamento, times(1)).executar(new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.ERRO, ""));
    }
}
