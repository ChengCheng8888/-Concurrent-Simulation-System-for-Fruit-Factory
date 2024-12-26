//volatile关键字

import java.util.concurrent.atomic.AtomicInteger;

public abstract class FactorySection implements Runnable {
    private static AtomicInteger canIDGenerator = new AtomicInteger(1);
    private final String sectionName;
    private static volatile boolean forkliftPaused = false;
    private boolean isIdle = false;

    public FactorySection(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public void run() {
        try {
            while (!isLastItem()) {
                if (this instanceof PackagingSection && forkliftPaused) {
                    System.out.println(sectionName + ": Waiting for forklift repair...");
                    Thread.sleep(500);
                    continue;
                }

                processItem();
            }
            System.out.println(sectionName + ": All cans processed.");
            isIdle = true;
        } catch (InterruptedException e) {
            System.err.println(sectionName + " interrupted: " + e.getMessage());
        }
    }

    protected int getNextCanID() {
        return canIDGenerator.getAndIncrement();
    }

    protected abstract void processItem() throws InterruptedException;

    protected abstract boolean isLastItem();

    public String getSectionName() {
        return sectionName;
    }

    public boolean isIdle() {
        return isIdle;
    }

    public static void pauseForForklift(boolean pause) {
        forkliftPaused = pause;
    }
}

