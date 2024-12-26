public class SterilizationSection extends FactorySection {
    private int cansSterilized;
    private static final int CANS_PER_BATCH = 4;  // 每次消毒的罐头数量
    private static final int MAX_CANS_TO_STERILIZE = 600; // 需要消毒的最大罐头数量

    public SterilizationSection() {
        super("Sterilization Section");
        this.cansSterilized = 0;
    }

    @Override
    protected void processItem() throws InterruptedException {
        cansSterilized++;
        System.out.println(getSectionName() + ": Can being disinfected " + cansSterilized + "..."); //
        if (cansSterilized % CANS_PER_BATCH == 0) {
            System.out.println(getSectionName() + ": Batch disinfection completed " + CANS_PER_BATCH + " can。");
            Thread.sleep(300);
        } else {
            Thread.sleep(350);
        }
    }

    @Override
    protected boolean isLastItem() {
        // 判断是否为最后一个罐头
        return cansSterilized >= MAX_CANS_TO_STERILIZE;
    }
}
