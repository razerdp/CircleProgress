package razerdp.com.circleprogress;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 大灯泡 on 2016/5/6.
 *
 * 线程池封装
 */
public class ThreadPoolManager {

    private static ExecutorService threadPool = null;

    static {
        threadPool = Executors.newCachedThreadPool();
    }

    public static void execute(Runnable runnable) {
        try {
            threadPool.execute(runnable);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", e.toString());
        }
    }

    /**
     * 针对不同api的 asynctask处理
     * 3.0以后的asynctask被改为默认串行，使用自己的线程池实现并行
     */
    public static <Params, Progress, Result> void executeOnExecutor(AsyncTask<Params, Progress, Result> task, Params...
        params) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(threadPool, params);
        } else {
            task.execute(params);
        }
    }
}
