package com.examples.monitorServerState;

import com.examples.monitorServerState.exception.PropertyLoadException;
import com.examples.monitorServerState.timer.ParseTimer;

import java.io.File;
import java.util.Timer;

public class AppRunner {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new PropertyLoadException("Please give a property folder");
        }
        System.setProperty("configPath", args[0] + File.separator);
        Timer timer = new Timer();
        ParseTimer parseTimer = new ParseTimer("Task1");
        timer.scheduleAtFixedRate(parseTimer, 0, 60 * 1000);

    }
}
