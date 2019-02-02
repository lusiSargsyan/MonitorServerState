package com.examples.storeServerState.configuration;

import com.examples.monitorServerState.configuration.PropertyLoader;
import com.examples.monitorServerState.exception.PropertyLoadException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest(PropertyLoader.class)
@RunWith(PowerMockRunner.class)
public class PropertyLoaderTest {

    private static final String CONFIG_PATH = "src/test/resources/properties/";
    private static final String CONFIG_KEY = "configPath";

    @BeforeClass
    public static void initProperties() {
        System.setProperty(CONFIG_KEY, CONFIG_PATH);
    }

    @Test
    public void initPropertiesTest() throws PropertyLoadException {
        mockStatic(System.class);
        when(System.getProperty(Mockito.anyString())).thenReturn(CONFIG_PATH);
        Properties p = PropertyLoader.loadKafkaProperties();
        assertEquals("-1", p.getProperty("acks"));
        assertEquals("localhost:9092", p.getProperty("bootstrap.servers"));
        assertEquals("org.apache.kafka.common.serialization.StringSerializer", p.getProperty("value.serializer"));

    }

    @Test(expected = PropertyLoadException.class)
    public void initPropertiesExceptionTest() throws PropertyLoadException {
        PropertyLoader.loadProperties(null);
    }
}
