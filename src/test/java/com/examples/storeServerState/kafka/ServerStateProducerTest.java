package com.examples.storeServerState.kafka;

import com.examples.monitorServerState.exception.KafkaConnectionException;
import com.examples.monitorServerState.kafka.ServerStateProducer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ServerStateProducerTest {
    private static final String CONFIG_PATH = "src/test/resources/properties/";
    private static final String CONFIG_KEY = "configPath";

    @BeforeClass
    public static void initProperties() {
        System.setProperty(CONFIG_KEY, CONFIG_PATH);
    }

    @Test(expected = InvocationTargetException.class)
    public void newInstanceTest() throws Exception {
        Class<ServerStateProducer> producerClass = ServerStateProducer.class;
        Constructor<ServerStateProducer> constructor = producerClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void getInstanceTest() throws Exception {
        List<ServerStateProducer> a = Collections.synchronizedList(new ArrayList<>());
        final CountDownLatch latch = new CountDownLatch(1);
        Executor e = Executors.newSingleThreadExecutor();
        e.execute(() -> {
            try {
                a.add(ServerStateProducer.getInstance());

            } catch (KafkaConnectionException ex) {
                ex.printStackTrace();
            } finally {
                latch.countDown();
            }
        });
        latch.await();
        assertEquals(a.get(0), ServerStateProducer.getInstance());
    }
}
