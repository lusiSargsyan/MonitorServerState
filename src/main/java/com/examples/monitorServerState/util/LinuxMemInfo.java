package com.examples.monitorServerState.util;

public enum LinuxMemInfo {
    MEM_TOTAL("MemTotal"),
    MEM_FREE("MemFree"),
    MEM_AVAILABLE("MemAvailable"),
    SWAP_TOTAL("SwapTotal"),
    SWAP_FREE("SwapFree"),
    BUFFERS("Buffers");
    private String name;

    LinuxMemInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
