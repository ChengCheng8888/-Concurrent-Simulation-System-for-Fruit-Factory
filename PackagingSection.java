//Synchronization 同步

public class PackagingSection extends FactorySection {
    private int cansPackaged;
    private int boxesPacked;
    private static final int CANS_PER_BOX = 12;  // 每箱12罐头
    private static final int MAX_CANS_TO_PACKAGE = 600;

    public PackagingSection() {
        super("Packaging Section");
        this.cansPackaged = 0;
        this.boxesPacked = 0;
    }

    @Override
    protected void processItem() throws InterruptedException {
        if (cansPackaged % CANS_PER_BOX == 0 && cansPackaged > 0 && FactorySimulator.addBoxToLoadingBay()) {
            boxesPacked++;
            System.out.println(getSectionName() + ": Packed box " + boxesPacked + ".");
            FactorySimulator.recordBoxPacked();
        }

        if (cansPackaged < MAX_CANS_TO_PACKAGE) {
            cansPackaged++;
            Thread.sleep(350);
        }
    }

    @Override
    protected boolean isLastItem() {
        return cansPackaged >= MAX_CANS_TO_PACKAGE;
    }
}
