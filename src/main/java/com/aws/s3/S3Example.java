package com.aws.s3;

import java.util.EnumSet;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketNotificationConfiguration;
import com.amazonaws.services.s3.model.S3Event;
import com.amazonaws.services.s3.model.SetBucketNotificationConfigurationRequest;
import com.amazonaws.services.s3.model.TopicConfiguration;

public class S3Example {
	private AmazonS3 s3;
	
	public S3Example() {
		this.s3 = AmazonS3ClientBuilder.defaultClient();
	}
	
	public void configS3SNS(String bucketName) throws Exception{
		BucketNotificationConfiguration config = 
				new BucketNotificationConfiguration();
		config.addConfiguration("snsTopicConfig", 
				new TopicConfiguration("arn:aws:sns:eu-west-1:565490044280:test_topic",
						EnumSet.of(S3Event.ObjectCreated)));
		SetBucketNotificationConfigurationRequest request = 
				new SetBucketNotificationConfigurationRequest(bucketName,config);
		s3.setBucketNotificationConfiguration(request);
	}
	
	public static void main(String[] args) {
		try {
			S3Example e = new S3Example();
			e.configS3SNS("sqs-sns-example");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
