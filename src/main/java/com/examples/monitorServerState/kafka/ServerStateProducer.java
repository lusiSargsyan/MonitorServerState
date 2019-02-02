package com.examples.monitorServerState.kafka;

import com.examples.monitorServerState.configuration.PropertyLoader;
import com.examples.monitorServerState.exception.ErrorCodes;
import com.examples.monitorServerState.exception.KafkaConnectionException;
import com.examples.monitorServerState.exception.PropertyLoadException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ServerStateProducer {

    private static ServerStateProducer producerInstance;
    private static KafkaProducer<String, String> producer;
    private static Properties kafkaProperties;


    private ServerStateProducer() throws KafkaConnectionException {
        if (producerInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        init();
    }

    /**
     * @return single instance or null
     */
    public static ServerStateProducer getInstance() throws KafkaConnectionException {
        //add check for null to avoid threads to wait for synchronized block
        if (producerInstance == null) {
            //THREAD1 is executing sync block and thread2 is waiting
            synchronized (ServerStateProducer.class) {
                if (producerInstance == null) { //when thread 1 is leaving thread 2 is checking for null
                    producerInstance = new ServerStateProducer();
                }
            }
        }
        return producerInstance;
    }

    public void send(String key, String value) {
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>(kafkaProperties.getProperty("write.topic"), key, value);
        producer.send(record);
    }

    private void init() throws KafkaConnectionException {
        try {
            kafkaProperties = PropertyLoader.loadKafkaProperties();
            producer = new KafkaProducer<String, String>(kafkaProperties);
        } catch (PropertyLoadException e) {
            throw new KafkaConnectionException(ErrorCodes.NOT_CONNECTED, e.getMessage());
        }
    }

}
