package com.examples.monitorServerState.parser.impl;

import com.examples.monitorServerState.parser.ServerStateParser;
import com.examples.monitorServerState.util.Os;

public class ServerStateParserFactory {


    public static ServerStateParser getParser(String osName) {
        if (osName.toUpperCase().contains(Os.LINUX.name())) {
            return new LinuxStateParser();
        } else {
            return new WindowsStateParser();
        }
    }

}
