package de.stevenschwenke.java.java8workshop;

/**
 * MissionControl is the new tool for monitoring Java applications.
 */
public class C_11_MissionControl {
    /*
        See http://www.oracle.com/technetwork/java/javaseproducts/mission-control/java-mission-control-1998576.html
        and https://www.youtube.com/watch?v=ORHVOmxnxbo
        and especially http://blog.takipi.com/oracle-java-mission-control-the-ultimate-guide/

        MissionControl "JMC" = JMX Console + FlightRecorder "JFR"

        JMX CONSOLE
        ============

        1. Start MissionControl: [JDK]/bin/jmc.exe

        2. Create launch config with JVM startup flags for flight recorder:
        -XX:+UnlockCommercialFeatures
        -XX:+FlightRecorder

        3. Right-Click in JVM Browser -> Start JMX Console

        4. Overview,
            Triggers,
            Memory -> Heap Histogram (Click on "Refresh Heap Histogram" two times)
            Threads -> Deadlock-Detection + "Lock Owner Name"

        FLIGHT RECORDING
        =================

        1. (within MissionControl) Right-Click in JVM-Browser -> Start Flight Recording
        2. Range Slider
        3. Code -> Hot Methods

        HEAP DUMP ANALYSIS
        ===================

        1. Install experimental plugin JOverflow via Help -> Install New Software -> Heap Analysis -> JOverflow Heap Analyzer
        2. Right Click on Application -> Dump Heap
     */
}
