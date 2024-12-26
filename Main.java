//Thread 管理

public class Main {
    public static void main(String[] args) {
        // 创建工厂部分、叉车和货车
        FactorySection[] sections = {
                new SterilizationSection(), new FillingSection(),
                new SealingSection(), new LabellingSection(), new PackagingSection()
        };

        Forklift[] forklifts = {
                new Forklift("Forklift 1"),
                new Forklift("Forklift 2"),
                new Forklift("Forklift 3")
        };

        Van[] vans = {
                new Van("Van 1"),
                new Van("Van 2"),
                new Van("Van 3")
        };
        Thread[] threads = new Thread[sections.length + forklifts.length + vans.length];
        for (int i = 0; i < sections.length; i++) {
            threads[i] = new Thread(sections[i]);
        }
        for (int i = 0; i < forklifts.length; i++) {
            threads[sections.length + i] = new Thread(forklifts[i]);
        }
        for (int i = 0; i < vans.length; i++) {
            threads[sections.length + forklifts.length + i] = new Thread(vans[i]);
        }
        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        FactorySimulator.printStatistics();
    }
}

