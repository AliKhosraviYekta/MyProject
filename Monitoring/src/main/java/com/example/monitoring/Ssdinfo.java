package com.example.monitoring;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import java.text.DecimalFormat;
public class Ssdinfo implements Info {
    private long ssdReads;
    private long ssdWrites;
    private final SystemInfo systemInfo;
    public Ssdinfo() {
        this.systemInfo =new SystemInfo();
    }
    @Override
    public String showInfo() {

        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        HWDiskStore[] disks = hardware.getDiskStores().toArray(new HWDiskStore[0]);
        for (HWDiskStore disk : disks) {
            if (disk.getModel().toLowerCase().contains("ssd")) {
                String ssdName = disk.getName();
                long ssdReads = disk.getReads();
                long ssdWrites = disk.getWrites();
                this.ssdReads = (long) (ssdReads/1024/10);
                this.ssdWrites = (long) (ssdWrites/1024/10);
                double ssdReadsMB = ssdReads/1024.0/10;
                double ssdWritesMB = ssdWrites/1024.0/10;
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formattedReads = decimalFormat.format(ssdReadsMB);
                String formattedWrites = decimalFormat.format(ssdWritesMB);
                return "SSD Name: " + ssdName + "\nSSD Reads: " + formattedReads +
                        " MB/s\nSSD Writes: " + formattedWrites + " MB/s";
            }
        }
        return "No SSD found";
    }
    public long getSsdReads() {

        return (long) ssdReads;
    }

    public long getSsdWrites() {
        return (long) ssdWrites;
    }
    @Override
    public double showFrequency() {

       return 0;
    }

    @Override
    public long cpuFreqeuncyShow() {
        return 0;
    }

}

