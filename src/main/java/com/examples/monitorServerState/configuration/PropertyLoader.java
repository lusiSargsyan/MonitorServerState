package com.examples.monitorServerState.configuration;

import com.examples.monitorServerState.exception.PropertyLoadException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static String configPath = null;

    static {
        configPath = System.getProperty("configPath");

    }

    private PropertyLoader() {
    }

    public static Properties loadProperties(String configPath) throws PropertyLoadException {
        final Properties properties = new Properties();
        try (InputStream stream = new FileInputStream(configPath)) {
            properties.load(stream);
        } catch (NullPointerException | IOException e) {
            throw new PropertyLoadException("Can't find property file " + configPath);
        }
        return properties;
    }

    public static Properties loadKafkaProperties() throws PropertyLoadException {
        return PropertyLoader.loadProperties(configPath + "kafka.properties");
    }

}
