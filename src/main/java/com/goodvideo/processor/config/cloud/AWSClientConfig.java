package com.goodvideo.processor.config.cloud;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AWSClientConfig {

  @Value("${aws.access:123}")
  private String accessKey;

  @Value("${aws.secret:456}")
  private String secretAccessKey;

  @Value("${aws.bucket:}")
  private String bucketName;

  @Bean
  public AmazonS3 amazonS3() {
    final BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .withRegion("us-east-1")
        .build();
  }


}
