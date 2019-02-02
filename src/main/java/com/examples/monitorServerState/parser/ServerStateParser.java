package com.examples.monitorServerState.parser;

import com.examples.monitorServerState.dao.MemInfo;
import com.examples.monitorServerState.exception.ParseException;

public interface ServerStateParser {

    MemInfo parse() throws ParseException;
}

