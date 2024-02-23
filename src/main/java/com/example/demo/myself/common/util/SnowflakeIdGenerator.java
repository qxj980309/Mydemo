package com.example.demo.myself.common.util;

/*
* 可以使用像Twitter的Snowflake算法生成唯一的ID
* Snowflake算法可以生成一个64位的长整数，其中包含时间戳、数据中心ID、机器ID和序列号，以确保生成的ID既唯一又有序。
* */
public class SnowflakeIdGenerator {
    private long datacenterId; // 数据中心ID
    private long machineId;    // 机器ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上一次时间戳

    private final long twepoch = 1288834974657L; //系统的起始时间戳
    private final long datacenterIdBits = 5L; //数据中心ID所占的位数
    private final long machineIdBits = 5L; //机器ID所占的位数
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); //数据中心ID的最大值，这里通过位运算计算得出
    private final long maxMachineId = -1L ^ (-1L << machineIdBits); //机器ID的最大值，同样通过位运算得出。
    private final long sequenceBits = 12L; //机器ID的最大值，同样通过位运算得出

    private final long machineIdShift = sequenceBits; //机器ID的偏移位
    private final long datacenterIdShift = sequenceBits + machineIdBits; //数据中心ID的偏移位数
    private final long timestampLeftShift = sequenceBits + machineIdBits + datacenterIdBits; //数据中心ID的偏移位数。
    private final long sequenceMask = -1L ^ (-1L << sequenceBits); //用于保证序列号在指定范围内循环

    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than %d or less than 0");
        }
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than %d or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /*
    * public synchronized long nextId()是生成ID的核心方法，使用synchronized保证线程安全。
    * */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (machineId << machineIdShift) |
                sequence;
    }

    /*
    * private long tilNextMillis(long lastTimestamp)是一个辅助方法，用于在序列号溢出时等待直到下一个毫秒。
    * */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
