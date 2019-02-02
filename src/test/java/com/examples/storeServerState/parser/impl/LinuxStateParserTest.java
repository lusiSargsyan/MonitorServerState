package com.examples.storeServerState.parser.impl;

import com.examples.monitorServerState.dao.MemInfo;
import com.examples.monitorServerState.parser.ServerStateParser;
import com.examples.monitorServerState.parser.impl.LinuxStateParser;
import com.examples.monitorServerState.parser.impl.ServerStateParserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@PrepareForTest(LinuxStateParser.class)
@RunWith(PowerMockRunner.class)
public class LinuxStateParserTest {

    public static ServerStateParser parser;

    @BeforeClass
    public static void init() {
        parser = ServerStateParserFactory.getParser("Linux");
    }


    @Test
    public void getMemoryValue_test() throws Exception {
        Optional<Integer> actual = Whitebox.invokeMethod(parser, "getMemoryValue", "MemTotal:       32463712 kB");
        assertEquals((Integer) 32463712, actual.get());

        actual = Whitebox.invokeMethod(parser, "getMemoryValue", "MemFree:        10651228 kB");
        assertEquals((Integer) 10651228, actual.get());

        actual = Whitebox.invokeMethod(parser, "getMemoryValue", "MemAvailable:   23562404 kB");
        assertEquals((Integer) 23562404, actual.get());

        actual = Whitebox.invokeMethod(parser, "getMemoryValue", "Buffers:          561116 kB");
        assertEquals((Integer) 561116, actual.get());

        actual = Whitebox.invokeMethod(parser, "getMemoryValue", "SwapTotal:        999420 kB");
        assertEquals((Integer) 999420, actual.get());

        actual = Whitebox.invokeMethod(parser, "getMemoryValue", "SwapFree:        999420 kB");
        assertEquals((Integer) 999420, actual.get());
    }

    // mock paths.get
    @Test
    public void parse_test() throws Exception {
        PowerMockito.mockStatic(Paths.class);
        File resourcesDirectory = new File("src/test/resources/meminfo");
        PowerMockito.when(Paths.get(LinuxStateParser.MEM_INFO_FILE))
                .thenReturn(resourcesDirectory.toPath());
        MemInfo info = parser.parse();
        assertEquals(999420, info.getSwapFree());
        assertEquals(999420, info.getSwapTotal());
        assertEquals(10651228, info.getMemFree());
        assertEquals(32463712, info.getMemTotal());
        assertEquals(561116, info.getBuffers());
    }

}
