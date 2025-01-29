package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import com.goodvideo.processor.factories.FileOutputStreamFactory;
import com.goodvideo.processor.factories.ZipOutputStreamFactory;
import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZiparArquivoImplTest {

  private ZiparArquivoImpl ziparArquivo;
  private FinalizarProcessamento finalizarProcessamento;

  @Mock
  private FileOutputStreamFactory fileOutputStreamFactory;

  @Mock
  private ZipOutputStreamFactory zipOutputStreamFactory;

  @Mock
  private ZipOutputStream zipOutputStream;

  @Mock
  private FileOutputStream fileOutputStream;

  private static final String MOCK_ZIP_PATH = "/mock/zip/path/";

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException, FileNotFoundException {
    // Mock dependencies
    finalizarProcessamento = mock(FinalizarProcessamento.class);

    // Instantiate the class with mocks
    ziparArquivo = new ZiparArquivoImpl(finalizarProcessamento, fileOutputStreamFactory, zipOutputStreamFactory);

    // Inject the mock zip path
    Field zipPathField = ZiparArquivoImpl.class.getDeclaredField("zipPath");
    zipPathField.setAccessible(true); // Make the private field accessible
    zipPathField.set(ziparArquivo, MOCK_ZIP_PATH); // Set the value
  }

  @Test
  void testExecutar_Success() throws IOException, ProcessamentoException {
    // Arrange
    Processamento processamento = new Processamento();
    processamento.setIdUsuario("123");
    processamento.setIdVideo("video123");

    when(zipOutputStreamFactory.createZipOutputStream(any())).thenReturn(zipOutputStream);
    when(fileOutputStreamFactory.createFileOutputStream(any())).thenReturn(fileOutputStream);

    // Create a temporary directory and file to simulate input
    Path tempDir = Files.createTempDirectory("testDir");
    Path tempFile = Files.createTempFile(tempDir, "testFile", ".txt");
    Files.writeString(tempFile, "Test content");

    String expectedOutputPath = MOCK_ZIP_PATH + "123/frames.zip";

    String result = ziparArquivo.executar(tempDir.toString(), processamento);

    // Assert
    assertEquals(expectedOutputPath, result);
    assertTrue(Files.exists(Path.of(expectedOutputPath)));

    // Clean up
    Files.deleteIfExists(Path.of(expectedOutputPath));
    Files.deleteIfExists(tempFile);
    Files.deleteIfExists(tempDir);
  }

  @Test
  void testExecutar_FailureOnZipping() throws IOException {
    // Arrange
    Processamento processamento = new Processamento();
    processamento.setIdUsuario("123");
    processamento.setIdVideo("video123");

    // Create a temporary directory but simulate an error by passing a non-existent directory
    Path tempDir = Files.createTempDirectory("testDir");
    String nonExistentDir = tempDir.resolve("nonExistent").toString();

    // Act & Assert
    ProcessamentoException exception = assertThrows(
            ProcessamentoException.class,
            () -> ziparArquivo.executar(nonExistentDir, processamento)
    );

    assertTrue(exception.getMessage().contains("Erro ao zipar arquivo"));

    // Verify the FinalizarProcessamento is called with the correct parameters
    verify(finalizarProcessamento, times(1)).executar(
            new FinalizarProcessamentoMensagem(processamento.getIdVideo(), Status.ERRO, "")
    );

    // Clean up
    Files.deleteIfExists(tempDir);
  }

}
