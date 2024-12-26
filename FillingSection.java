import java.util.Random;

public class FillingSection extends FactorySection {
    private int itemsProcessed;
    private static final int MAX_ITEMS = 600;

    public FillingSection() {
        super("Filling Section");
        this.itemsProcessed = 0;
    }

    @Override
    protected void processItem() throws InterruptedException {
        itemsProcessed++;
        int canID = getNextCanID();

        if (new Random().nextInt(100) < 20) { // 模拟缺陷检测
            System.out.println(getSectionName() + ": Defective can " + canID + " removed.");
            return;
        }

        System.out.println(getSectionName() + ": Processing item " + itemsProcessed + " (can ID " + canID + ")");
        Thread.sleep(200);
        FactorySimulator.recordCansProcessed();
    }

    @Override
    protected boolean isLastItem() {
        return itemsProcessed >= MAX_ITEMS;
    }
}
