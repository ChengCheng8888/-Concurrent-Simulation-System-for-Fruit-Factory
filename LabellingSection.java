public class LabellingSection extends FactorySection {
    private int cansLabelled;
    private static final int MAX_CANS_TO_LABEL = 600;

    public LabellingSection() {
        super("Labelling Section");
        this.cansLabelled = 0;
    }

    @Override
    protected void processItem() throws InterruptedException {
        cansLabelled++;
        System.out.println(getSectionName() + ": cans are being " + cansLabelled + " labelled ...");
        Thread.sleep(300);
    }

    @Override
    protected boolean isLastItem() {
        return cansLabelled >= MAX_CANS_TO_LABEL;
    }
}
