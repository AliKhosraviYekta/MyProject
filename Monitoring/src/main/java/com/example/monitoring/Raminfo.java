package com.example.monitoring;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;

import java.text.DecimalFormat;

public class Raminfo implements Info{
    private final SystemInfo systemInfo;

    public Raminfo() {
        this.systemInfo = new SystemInfo();
    }

    @Override
    public String showInfo() {
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalRamSize = memory.getTotal();
        double totalRamSizeGB = totalRamSize / 1024.0 / 1024.0 / 1024.0;
        long availableRamSize = memory.getAvailable();
        double availableRamSizeGB = availableRamSize / 1024.0 / 1024.0 / 1024.0;
        String ramName = memory.getPhysicalMemory().toString();
        String ramname = ramName.substring(1, 61);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedTotalRamSize = decimalFormat.format(totalRamSizeGB);
        String formattedAvailableRamSize = decimalFormat.format(availableRamSizeGB);
        return "RAM Name:\n" + ramname + "\nTotal RAM Size: "
                + formattedTotalRamSize + " GB" + "\nAvailable RAM Size: " + formattedAvailableRamSize + " GB";
    }

    @Override
    public double showFrequency() {
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        PhysicalMemory[] physicalMemoryArray = memory.getPhysicalMemory().toArray(new PhysicalMemory[0]);

        if (physicalMemoryArray.length > 0) {
            // در اینجا یک مقدار معین از آبجکت PhysicalMemory انتخاب شده و از آن فرکانس خوانده شده است
            return physicalMemoryArray[0].getClockSpeed()/1024/1024;
        } else {
            // در صورتی که اطلاعاتی وجود نداشته باشد، می‌توانید یک مقدار پیشفرض یا null بازگردانید
            return 0; // یا مقدار دلخواه دیگر
        }
    }


    @Override
    public long cpuFreqeuncyShow() {

        return 0;
    }

}