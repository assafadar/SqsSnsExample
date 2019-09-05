package com.aws.sqs;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SqsExample {
	private AmazonSQS sqs;
	
	public SqsExample() {
		this.sqs = AmazonSQSClientBuilder.defaultClient();
	}
	
	public boolean createQueue(String name) {
		CreateQueueRequest createRequest = 
				new CreateQueueRequest(name)
				.addAttributesEntry("DelaySeconds", "60")
				.addAttributesEntry("MessageRetentionPeriod", "86400");
		
		try {
			this.sqs.createQueue(createRequest);
		}catch (AmazonSQSException e) {
			if(!e.getErrorCode().equals("QueueAlreadyExists"))
				return false;
			else
				return true;
		}
		return true;		
		
	}
	
	public ListQueuesResult getAllQueues() {
		ListQueuesResult queueList = this.sqs.listQueues();
		return queueList;
	}
	
	public void createMessage(String queueUrl) {
		SendMessageRequest request = new SendMessageRequest(queueUrl, "This is a test message");
		request.setDelaySeconds(5);
		this.sqs.sendMessage(request);
		
	}
	
	public void deleteMessageFromQueue(String queueUrl, String handler) {
		this.sqs.deleteMessage(queueUrl,handler);
	}
	
	public List<Message> getAllMessagesForQueue(String queueURL){
		return this.sqs.receiveMessage(queueURL).getMessages();
	}
	
	public static void main(String[] args) {
		Timer timer = new Timer();
		SqsExample e = new SqsExample();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				e.getAllQueues().getQueueUrls().forEach(url -> {
					System.out.println(url);
//					e.createMessage(url);
					e.getAllMessagesForQueue(url).forEach(m -> {
						System.out.println("Message in quque: "+e);
						System.out.println(m.getBody());
						e.deleteMessageFromQueue(url, m.getReceiptHandle());
					});
				});
			}
		}, 0, 60 * 1000);
		
//		SqsGroovyExample e = new SqsGroovyExample();
		
	}
}
