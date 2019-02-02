package com.examples.monitorServerState.timer;

import com.examples.monitorServerState.dao.MemInfo;
import com.examples.monitorServerState.exception.KafkaConnectionException;
import com.examples.monitorServerState.exception.ParseException;
import com.examples.monitorServerState.kafka.ServerStateProducer;
import com.examples.monitorServerState.parser.ServerStateParser;
import com.examples.monitorServerState.parser.impl.ServerStateParserFactory;
import com.examples.monitorServerState.util.Util;

import java.util.TimerTask;

public class ParseTimer extends TimerTask {

    private String name;

    public ParseTimer(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        if (name.equals("Task1")) {
            try {
                ServerStateParser parser = ServerStateParserFactory.getParser(Util.getOs());
                MemInfo info = parser.parse();
                ServerStateProducer producer = ServerStateProducer.getInstance();
                producer.send(null, info.toString());
            } catch (ParseException | KafkaConnectionException e) {
                System.out.println("Error saving record : skipping this time " + e.getMessage());
            }

        }
    }
}
