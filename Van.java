import java.util.Random;

public class Van implements Runnable {
    private final String name;
    private static final int MAX_BOXES = 18;
    private int loadedBoxes;
    private final Random random;

    public Van(String name) {
        this.name = name;
        this.loadedBoxes = 0;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (loadedBoxes < MAX_BOXES) {
                long waitStart = System.currentTimeMillis();

                if (random.nextInt(100) < 10) {
                    System.out.println(name + ": Delayed arrival...");
                    Thread.sleep(500 + random.nextInt(1000));
                }

                if (LoadingBayManager.tryAcquireWithTimeout(2000)) {
                    long waitTime = System.currentTimeMillis() - waitStart;
                    FactorySimulator.recordVanLoaded(name, waitTime);

                    loadBox();
                    FactorySimulator.removeBoxFromLoadingBay();
                    LoadingBayManager.release();
                } else {
                    System.out.println(name + ": Timeout waiting for loading bay...");
                    Thread.sleep(500 + random.nextInt(1000));
                }
            }
            leaveFactory();
        } catch (InterruptedException e) {
            System.err.println(name + " interrupted: " + e.getMessage());
        }
    }

    private void loadBox() throws InterruptedException {
        if (FactorySimulator.getTotalBoxesPacked() > loadedBoxes) {
            loadedBoxes++;
            System.out.println(name + ": Loading box " + loadedBoxes);
            Thread.sleep(100);
        }
    }

    private void leaveFactory() {
        System.out.println(name + ": Loaded with " + loadedBoxes + " boxes, leaving factory.");
    }
}