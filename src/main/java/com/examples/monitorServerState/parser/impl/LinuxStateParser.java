package com.examples.monitorServerState.parser.impl;

import com.examples.monitorServerState.dao.MemInfo;
import com.examples.monitorServerState.exception.ParseException;
import com.examples.monitorServerState.parser.ServerStateParser;
import com.examples.monitorServerState.util.LinuxMemInfo;
import com.examples.monitorServerState.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class LinuxStateParser implements ServerStateParser {

    public static final String MEM_INFO_FILE = "/proc/meminfo";

    public MemInfo parse() throws ParseException {
        MemInfo.Builder memInfoBuilder = new MemInfo.Builder();
        try (Stream<String> lines = Files.lines(Paths.get(MEM_INFO_FILE))) {
            lines.forEach(s -> {
                if (s.contains(LinuxMemInfo.MEM_TOTAL.getName())) {
                    memInfoBuilder.setMemTotal(getMemoryValue(s).get());
                } else if (s.contains(LinuxMemInfo.BUFFERS.getName())) {
                    memInfoBuilder.setBuffers(getMemoryValue(s).get());
                } else if (s.contains(LinuxMemInfo.MEM_FREE.getName())) {
                    memInfoBuilder.setMemFree(getMemoryValue(s).get());
                } else if (s.contains(LinuxMemInfo.SWAP_FREE.getName())) {
                    memInfoBuilder.setSwapFree(getMemoryValue(s).get());
                } else if (s.contains(LinuxMemInfo.SWAP_TOTAL.getName())) {
                    memInfoBuilder.setSwapTotal(getMemoryValue(s).get());
                }

            });
        } catch (IOException e) {
            throw new ParseException("");
        }
        return memInfoBuilder.build();
    }

    private Optional<Integer> getMemoryValue(String line) {
        String lineParts[] = line.split(" ");
        Stream<String> stream = Arrays.stream(lineParts);
        return stream
                .filter(Util::isNumeric)
                .map(Integer::valueOf)
                .findFirst();
    }

}
