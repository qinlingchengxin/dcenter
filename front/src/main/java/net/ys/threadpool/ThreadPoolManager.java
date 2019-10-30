/**
 *
 */
package net.ys.threadpool;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author sujg
 */
public enum ThreadPoolManager {

    INSTANCE;

    /**
     * 综合线程池
     */
    public TPool complexPool = null;

    ThreadPoolManager() {
        /**
         * @param corePoolSize ： 线程池维护线程的最少数量
         * @param maximumPoolSize ：线程池维护线程的最大数量
         * @param keepAliveTime ： 线程池维护线程所允许的空闲时间
         * @param unit ： 线程池维护线程所允许的空闲时间的单位
         * @param workQueue ：线程池所使用的缓冲队列
         * @param  handler ： 线程池对拒绝任务的处理策略
         * */
        complexPool = new TPool(20, 200, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), null);
    }

    /**
     * 线程池销毁
     */
    public void destroy() {
        try {
            if (complexPool != null) {
                complexPool.shutdown();
                Thread.sleep(2000);
                boolean shutdown = complexPool.isShutdown();
                if (!shutdown) {
                    complexPool.shutdownNow();
                }
            }
        } catch (InterruptedException e) {
        }
    }
}
