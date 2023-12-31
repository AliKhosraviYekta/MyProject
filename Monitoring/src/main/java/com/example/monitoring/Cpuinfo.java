package com.example.monitoring;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public  class Cpuinfo implements Info {
    private final SystemInfo systemInfo;

    public Cpuinfo() {
        this.systemInfo = new SystemInfo();
    }

    @Override
    public String showInfo() {

        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        return processor.getProcessorIdentifier().getName();
    }

    @Override
    public double showFrequency() {

        return 0;
    }

    @Override
    public long cpuFreqeuncyShow() {
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        var current = processor.getProcessorIdentifier().getVendorFreq()/1024/1024;
        return current;
    }

}


