package com.goodvideo.processor.usecase;

import com.amazonaws.services.s3.AmazonS3;
import com.goodvideo.processor.domains.Processamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarArquivoImplTest {

    private DeletarArquivoImpl deletarArquivo;
    private AmazonS3 amazonS3;

    private static final String MOCK_BUCKET_NAME = "mock-bucket";

    @BeforeEach
    void setUp() throws Exception {
        // Mock the AmazonS3 client
        amazonS3 = mock(AmazonS3.class);

        // Create the class under test
        deletarArquivo = new DeletarArquivoImpl(amazonS3);

        // Use reflection to set the private field for bucketName
        var bucketField = DeletarArquivoImpl.class.getDeclaredField("bucketName");
        bucketField.setAccessible(true);
        bucketField.set(deletarArquivo, MOCK_BUCKET_NAME);
    }

    @Test
    void testExecutar_DeletesObjectFromS3() {
        // Arrange: Create a dummy Processamento object
        Processamento processamento = new Processamento();
        processamento.setDiretorio("test/file.txt");

        // Act: Call the method under test
        deletarArquivo.executar(processamento);

        // Assert: Verify that the deleteObject method was called with the correct parameters
        verify(amazonS3, times(1)).deleteObject(MOCK_BUCKET_NAME, processamento.getDiretorio());
    }
}