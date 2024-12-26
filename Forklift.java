//Synchronization同步

import java.util.Random;

public class Forklift implements Runnable {
    private final String name;
    private static final int MAX_BOXES_TO_LIFT = 20;
    private int boxesLifted;
    private final Random random;

    public Forklift(String name) {
        this.name = name;
        this.boxesLifted = 0;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (boxesLifted < MAX_BOXES_TO_LIFT) {
                if (random.nextInt(100) < 10) {
                    System.out.println(name + ": Forklift malfunction, pausing...");
                    FactorySection.pauseForForklift(true);
                    Thread.sleep(2000);
                    System.out.println(name + ": Forklift repair complete, resuming...");
                    FactorySection.pauseForForklift(false);
                }

                liftBox();
                Thread.sleep(500 + random.nextInt(500));
            }
        } catch (InterruptedException e) {
            System.err.println(name + " interrupted: " + e.getMessage());
        }
    }

    private synchronized void liftBox() {
        if (FactorySimulator.getTotalBoxesPacked() > boxesLifted &&
                FactorySimulator.addBoxToLoadingBay()) {
            boxesLifted++;
            System.out.println(name + ": Moving box " + boxesLifted);
            FactorySimulator.recordForklift();
        }
    }
}
