package com.goodvideo.processor.usecase;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.goodvideo.processor.domains.Processamento;
import com.goodvideo.processor.domains.exceptions.ProcessamentoException;
import com.goodvideo.processor.factories.FileOutputStreamFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class BaixarArquivoImpl implements BaixarArquivo {

  private final AmazonS3 amazonS3;
  private final FileOutputStreamFactory fileOutputStreamFactory;

  @Value("${aws.bucket}")
  private String bucketName;

  @Value("${process.download-path}")
  private String downloadPath;

  @Override
  public String executar(final Processamento processamento) throws ProcessamentoException {
    try {
      String fileName =  processamento.getDiretorio().substring(processamento.getDiretorio().lastIndexOf("/") + 1);
      System.out.println("fileName: " + fileName);

      String downloadFolder = String.format(downloadPath, processamento.getIdVideo());
      new File(downloadFolder).mkdirs();

      String completeDownloadPath = downloadFolder + fileName;

      System.out.println("completeDownloadPath: " + completeDownloadPath);


      System.out.println("bucketName: " + bucketName);
      System.out.println("processamento.getDiretorio(): " + processamento.getDiretorio());

      S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, processamento.getDiretorio()));

      System.out.println("s3Object" + s3Object);

      try (InputStream inputStream = s3Object.getObjectContent();
           FileOutputStream outputStream = fileOutputStreamFactory.createFileOutputStream(completeDownloadPath)) {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead);
        }
      }

      s3Object.close();

      System.out.println("File downloaded successfully to: " + completeDownloadPath);

      return completeDownloadPath;

    } catch (Exception e) {
      System.out.println("error download probably");
      System.out.println(e.getMessage());
      throw new ProcessamentoException(
              String.format("Falha ao salvar arquivo, %s", e.getMessage()));
    }
  }

}
