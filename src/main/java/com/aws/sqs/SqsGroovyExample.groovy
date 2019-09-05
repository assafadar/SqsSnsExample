package com.aws.sqs

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder

class SqsGroovyExample {
	private AmazonSQS sqs;
	
	SqsGroovyExample(){
		this.sqs = AmazonSQSClientBuilder.defaultClient();
	}
	
	def createMessage() {
	
	}
	
	
	def getAllQueues() {
		return this.sqs.listQueues();
	}	
		
}
