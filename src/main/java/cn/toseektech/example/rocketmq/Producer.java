package cn.toseektech.example.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class Producer {

	public static final String GROUP_NAME = "demo_producer";

	public static final String TOPIC_KEY = "demo_topic";
	
	public static final String TAG = "demo_tag_1";
	
	public static void main(String[] args) throws Exception {
		
		DefaultMQProducer producer = new DefaultMQProducer(GROUP_NAME);
		
		// 设置NameServer的地址
		producer.setNamesrvAddr("47.114.41.74:9876");
		//producer.setCreateTopicKey(TOPIC_KEY);
		producer.setSendMsgTimeout(6000);
		producer.setRetryTimesWhenSendFailed(1);
		producer.setRetryTimesWhenSendAsyncFailed(0);
		producer.setClientCallbackExecutorThreads(8);
		producer.setMaxMessageSize(1024*1024*4);
		producer.setNamespace(null);
		producer.setRetryAnotherBrokerWhenNotStoreOK(false);
		producer.setDefaultTopicQueueNums(4);
		producer.setHeartbeatBrokerInterval(30*1000);
		producer.start();
		// 发送消息
		for(int i=0;i<3;i++) {
			Message msg = new Message();
			
			if(i==0) {
				msg.setTopic("a");
				msg.setTags("1");
			}else {
				msg.setTopic("b");
				msg.setTags("2");
			}
			
			msg.setBody(("Hello world "+i).getBytes()); 		
			SendResult result=producer.send(msg);
			System.out.println(result);
		}
		
	}

}
