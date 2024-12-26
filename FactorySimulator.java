// Atomic 变量

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;
import java.util.HashMap;

public class FactorySimulator {
    private static final int MAX_LOADING_CAPACITY = 20; // 装载区最多20个箱子
    private static final AtomicInteger totalCansProcessed = new AtomicInteger(0);
    private static final AtomicInteger totalBoxesPacked = new AtomicInteger(0);
    private static final AtomicInteger totalForklifts = new AtomicInteger(0);
    private static final AtomicInteger totalVansLoaded = new AtomicInteger(0);
    private static final AtomicLong totalWaitTimeForVans = new AtomicLong(0);
    private static final AtomicInteger currentBoxesInLoadingBay = new AtomicInteger(0); // 当前装载区的箱子数量

    private static final Map<String, Long> vanWaitTimes = new HashMap<>();
    private static long minWaitTime = Long.MAX_VALUE;
    private static long maxWaitTime = Long.MIN_VALUE;

    // Record all the attribute
    public static void recordCansProcessed() {
        totalCansProcessed.incrementAndGet();
    }
    public static void recordBoxPacked() {
        totalBoxesPacked.incrementAndGet();
    }
    public static void recordForklift() {
        totalForklifts.incrementAndGet();
    }
    public static void recordVanLoaded(String vanName, long waitTime) {
        totalVansLoaded.incrementAndGet();
        totalWaitTimeForVans.addAndGet(waitTime);
        vanWaitTimes.merge(vanName, waitTime, Long::sum);
        minWaitTime = Math.min(minWaitTime, waitTime);
        maxWaitTime = Math.max(maxWaitTime, waitTime);
    }
    public static int getTotalBoxesPacked() {
        return totalBoxesPacked.get();
    }
    public static synchronized boolean addBoxToLoadingBay() {
        if (currentBoxesInLoadingBay.get() < MAX_LOADING_CAPACITY) {
            currentBoxesInLoadingBay.incrementAndGet();
            return true;
        }
        return false;
    }

    // 从装载区移除一个箱子
    public static synchronized void removeBoxFromLoadingBay() {
        if (currentBoxesInLoadingBay.get() > 0) {
            currentBoxesInLoadingBay.decrementAndGet();
        }
    }

    // 打印统计数据和检查工厂状态
    public static void printStatistics() {
        int expectedBoxes = totalCansProcessed.get() / 12;



        System.out.println("\n=== Factory Simulation Statistics ===");
        System.out.println("Total cans processed : " + totalCansProcessed.get());
        System.out.println("Boxes packed : " + expectedBoxes);

        long avgWaitTime = totalVansLoaded.get() > 0 ? totalWaitTimeForVans.get() / totalVansLoaded.get() : 0;
        System.out.println("Average truck waiting time : " + avgWaitTime + " ms");
        System.out.println("Minimum truck waiting time : " + (minWaitTime == Long.MAX_VALUE ? 0 : minWaitTime) + " ms");
        System.out.println("Maximum truck waiting time : " + (maxWaitTime == Long.MIN_VALUE ? 0 : maxWaitTime) + " ms");

        System.out.println("Truck waiting time details ：");
        vanWaitTimes.forEach((van, time) ->
                System.out.println(van + " Total waiting time : " + time + " ms")

        );

        System.out.println("Current boxes in loading bay : " + currentBoxesInLoadingBay.get());
        if (currentBoxesInLoadingBay.get() == 0) {
            System.out.println("Sanity Check Passed: Loading bay is empty.");
        } else {
            System.out.println("Sanity Check Failed: Loading bay is not empty.");
        }
    }
}
