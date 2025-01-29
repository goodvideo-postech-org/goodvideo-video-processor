package com.goodvideo.processor.usecase;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SalvarArquivoImplTest {

  @InjectMocks
  private SalvarArquivoImpl salvarArquivo;

  @Mock
  private AmazonS3 amazonS3;

  private Processamento processamento;

  private static final String MOCK_USER_ID = "user123";
  private static final String MOCK_VIDEO_ID = "video123";
  private static final String MOCK_FILE_PATH = "/mock/path/to/file.zip";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Create a mock Processamento object
    processamento = new Processamento();
    processamento.setIdUsuario(MOCK_USER_ID);
    processamento.setIdVideo(MOCK_VIDEO_ID);
  }

  @Test
  void testExecutar_Success() throws Exception {
    try (MockedConstruction<FileInputStream> mockedConstruction = Mockito.mockConstruction(FileInputStream.class)) {
      // Arrange: Prepare the mock behavior
      File mockFile = mock(File.class);
      when(mockFile.length()).thenReturn(100L);
      when(mockFile.getName()).thenReturn("file.zip");
      when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(new PutObjectResult());

      // Act: Call the method
      String filePathS3 = salvarArquivo.executar(MOCK_FILE_PATH, processamento);

      // Assert: Verify the result and interactions
      assertNotNull(filePathS3);
      assertEquals("user123/video123/file.zip", filePathS3);
      verify(amazonS3, times(1)).putObject(any(PutObjectRequest.class));
    }
  }

  @Test
  void testExecutar_Failure() throws Exception {
    try (MockedConstruction<FileInputStream> mockedConstruction = Mockito.mockConstruction(FileInputStream.class)) {
      // Arrange: Prepare the mock behavior to throw an exception
      File mockFile = mock(File.class);
      when(mockFile.length()).thenReturn(100L);
      when(mockFile.getName()).thenReturn("file.zip");
      when(amazonS3.putObject(any(PutObjectRequest.class))).thenThrow(new RuntimeException("S3 error"));

      // Act and Assert: Check that ProcessamentoException is thrown
      ProcessamentoException thrownException = assertThrows(ProcessamentoException.class, () -> {
        salvarArquivo.executar(MOCK_FILE_PATH, processamento);
      });

      assertEquals("Falha ao salvar arquivo, S3 error", thrownException.getMessage());
    }
  }
}