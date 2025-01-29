package com.goodvideo.processor.usecase;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import com.goodvideo.processor.factories.FileOutputStreamFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileOutputStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BaixarArquivoImplTest {

    @Mock
    private AmazonS3 amazonS3;

    private BaixarArquivoImpl baixarArquivo;

    @Mock
    private S3Object s3Object;

    @Mock
    private S3ObjectInputStream s3ObjectInputStream;

    @Mock
    private FileOutputStreamFactory fileOutputStreamFactory;

    @Mock
    private FileOutputStream fileOutputStream;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {

        // Instantiate the class with mocks
        baixarArquivo = new BaixarArquivoImpl(amazonS3, fileOutputStreamFactory);

        // Inject the mock zip path
        Field bucketNameField = BaixarArquivoImpl.class.getDeclaredField("bucketName");
        bucketNameField.setAccessible(true); // Make the private field accessible
        bucketNameField.set(baixarArquivo, "mockBucket"); // Set the value

        // Inject the mock zip path
        Field downloadPathField = BaixarArquivoImpl.class.getDeclaredField("downloadPath");
        downloadPathField.setAccessible(true); // Make the private field accessible
        downloadPathField.set(baixarArquivo, "/downloadPath/%s/"); // Set the value
    }

    @Test
    public void testExecutarSuccess() throws Exception {
        Processamento processamento = new Processamento(
                "idVideo",
                "idProcessamento",
                "email@example.com",
                "cloud/video.mp4",
                "",
                Status.PROCESSANDO);

        String expectedPath = "/downloadPath/idVideo/video.mp4";

        when(amazonS3.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(s3ObjectInputStream);
        when(s3ObjectInputStream.read(any())).thenReturn(-1);

        when(fileOutputStreamFactory.createFileOutputStream(any())).thenReturn(fileOutputStream);

        String result = baixarArquivo.executar(processamento);

        assertEquals(expectedPath, result);
        verify(amazonS3, times(1)).getObject(any(GetObjectRequest.class));
    }

    @Test
    public void testExecutarFailure() {
        Processamento processamento = new Processamento(
                "idVideo",
                "idProcessamento",
                "email@example.com",
                "cloud/video.mp4",
                "",
                Status.PROCESSANDO);

        when(amazonS3.getObject(any(GetObjectRequest.class))).thenThrow(new RuntimeException("S3 error"));

        assertThrows(ProcessamentoException.class, () -> baixarArquivo.executar(processamento));
        verify(amazonS3, times(1)).getObject(any(GetObjectRequest.class));
    }
}
