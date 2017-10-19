package com.wuma.mq;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * Created by user on 17/10/19.
 */
public class MProducer {
    public static void main(String[] args) throws UnsupportedEncodingException {

        try {
            DefaultMQProducer producer = new DefaultMQProducer("Order-apply-service");

            Message message = new Message("Ordercancel-queue", "2017101911002341".getBytes("utf-8"));
            message.setDelayTimeLevel(3);
            producer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
