package cn.toseektech.example.rocketmq;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer {
	
	
	public static final String GROUP="demo_consumer";
	
	public static void main(String[] args) throws Exception {
		
		consumer1();
		
		//consumer2();
		
	}
	
	public static void consumer1() throws Exception {
		// 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

    	// 设置NameServer的地址
        consumer.setNamesrvAddr("47.114.41.74:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeThreadMax(5);
        consumer.setConsumeThreadMin(5);
        consumer.setMaxReconsumeTimes(5);
        consumer.setConsumerGroup(GROUP);
        consumer.setConsumeTimeout(15);
        consumer.setNamespace(null);
        consumer.setConsumeMessageBatchMaxSize(1);


    	// 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
       // consumer.subscribe(Producer.TOPIC_KEY,Producer.TAG);
        
        consumer.subscribe("a","*");
        
        consumer.subscribe("b","*");
        
       // consumer.subscribe(Producer.TOPIC_KEY,"2");
    	// 注册回调实现类来处理从broker拉取回来的消息
        
        consumer.registerMessageListener(new MessageListenerConcurrently() {
        	
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            
                System.out.printf("%s Receive1 New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer1 Started.%n");
		
		
	}
	
	public static void consumer2() throws Exception {
		// 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Producer.GROUP_NAME);

    	// 设置NameServer的地址
        consumer.setNamesrvAddr("47.114.41.74:9876");

    	// 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(Producer.TOPIC_KEY, "*");
    	// 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive2 New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer2 Started.%n");
		
		
	}

}
