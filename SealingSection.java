//密封
public class SealingSection extends FactorySection {
    private int cansSealed;
    private static final int CANS_PER_BATCH = 10;  // 每次密封的罐头数量
    private static final int MAX_CANS_TO_SEAL = 600; // 需要密封的最大罐头数量

    public SealingSection() {
        super("Sealing Section");
        this.cansSealed = 0;
    }

    @Override
    protected void processItem() throws InterruptedException {
        cansSealed++;
        System.out.println(getSectionName() + ": Can being sealed  " + cansSealed + "...");
        if (cansSealed % CANS_PER_BATCH == 0) {
            System.out.println(getSectionName() + ": Batch seal completed " + CANS_PER_BATCH + " can。");
            Thread.sleep(300);
        } else {
            Thread.sleep(350);
        }
    }

    @Override
    protected boolean isLastItem() {
        // 判断是否为最后一个罐头
        return cansSealed >= MAX_CANS_TO_SEAL;
    }
}
