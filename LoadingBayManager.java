//Semaphore

import java.util.concurrent.Semaphore;

public class LoadingBayManager {
    private static final Semaphore loadingBays = new Semaphore(2);  // 2 loading bay
    public static boolean tryAcquireWithTimeout(long timeoutMillis) throws InterruptedException {
        return loadingBays.tryAcquire(timeoutMillis, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
    public static void release() {
        loadingBays.release();
    }
}
