package com.goodvideo.processor.usecase;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaixarArquivoImplTest {

    private BaixarArquivoImpl baixarArquivo;
    private AmazonS3 amazonS3;

    private static final String MOCK_BUCKET_NAME = "mock-bucket";
    private static final String MOCK_DOWNLOAD_PATH = "/mock/download/%s/";

    @BeforeEach
    void setUp() throws Exception {
        // Mock the AmazonS3 client
        amazonS3 = mock(AmazonS3.class);

        // Create the class under test
        baixarArquivo = new BaixarArquivoImpl(amazonS3);

        // Use reflection to set private fields
        var bucketField = BaixarArquivoImpl.class.getDeclaredField("bucketName");
        bucketField.setAccessible(true);
        bucketField.set(baixarArquivo, MOCK_BUCKET_NAME);

        var downloadPathField = BaixarArquivoImpl.class.getDeclaredField("downloadPath");
        downloadPathField.setAccessible(true);
        downloadPathField.set(baixarArquivo, MOCK_DOWNLOAD_PATH);
    }

    @Test
    void testExecutar_Success() throws Exception {
        // Arrange: Create a dummy Processamento object
        Processamento processamento = new Processamento();
        processamento.setDiretorio("test/file.txt");
        processamento.setIdVideo("12345");

        String mockFileContent = "This is a test file content.";
        S3ObjectInputStream mockInputStream = new S3ObjectInputStream(new ByteArrayInputStream(mockFileContent.getBytes()), null);

        // Mock the behavior of S3Object
        S3Object mockS3Object = mock(S3Object.class);
        when(mockS3Object.getObjectContent()).thenReturn(mockInputStream);
        doNothing().when(mockS3Object).close();

        // Mock the behavior of AmazonS3
        when(amazonS3.getObject(any(GetObjectRequest.class))).thenReturn(mockS3Object);

        String expectedDownloadPath = String.format(MOCK_DOWNLOAD_PATH, "12345") + "file.txt";

        // Act
        String result = baixarArquivo.executar(processamento);

        // Assert
        assertEquals(expectedDownloadPath, result);
        assertTrue(Files.exists(new File(expectedDownloadPath).toPath()));

        // Verify AmazonS3 interaction
        verify(amazonS3, times(1)).getObject(new GetObjectRequest(MOCK_BUCKET_NAME, processamento.getDiretorio()));

        // Clean up
        Files.deleteIfExists(new File(expectedDownloadPath).toPath());
        Files.deleteIfExists(new File(String.format(MOCK_DOWNLOAD_PATH, "12345")).toPath());
    }

    @Test
    void testExecutar_Failure() {
        // Arrange: Create a dummy Processamento object
        Processamento processamento = new Processamento();
        processamento.setDiretorio("test/file.txt");
        processamento.setIdVideo("12345");

        // Mock the behavior of AmazonS3 to throw an exception
        when(amazonS3.getObject(any(GetObjectRequest.class))).thenThrow(new RuntimeException("S3 error"));

        // Act & Assert
        ProcessamentoException exception = assertThrows(
                ProcessamentoException.class,
                () -> baixarArquivo.executar(processamento)
        );

        assertTrue(exception.getMessage().contains("Falha ao salvar arquivo"));

        // Verify that AmazonS3 was called
        verify(amazonS3, times(1)).getObject(new GetObjectRequest(MOCK_BUCKET_NAME, processamento.getDiretorio()));
    }
}