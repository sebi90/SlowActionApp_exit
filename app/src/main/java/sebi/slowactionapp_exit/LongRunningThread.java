package sebi.slowactionapp_exit;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Sebi on 30.05.15.
 */
public class LongRunningThread extends Thread {

    private long total;
    private EditText input;
    private TextView output;
    private String format;
    private boolean onPauseThread = false;

    public LongRunningThread(long total, EditText input, TextView output, String format) {
        this.total = total;
        this.input = input;
        this.output = output;
        this.format = format;
    }

    public void run() {
        // Wird einmal von Thread-xxx aufgerufen
        Log.d("run_LongRunningThread", Thread.currentThread().getName());
        long rest = total;
        while (rest > 0) {
            long thisTime = Math.min(rest, 1000L);

            try {
                Thread.sleep(thisTime);
            }
            catch (Exception e) {}

            rest -= thisTime;
            Request req = new Request(input, "" + rest);
            input.post(req);
            synchronized (this) {
                while (onPauseThread) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        String message = String.format(format, total);
        Request req = new Request(output, message);
        input.post(req);
    }

    public void pauseThread()
    {
        onPauseThread = true;
    }

    public void resumeThread()
    {
        onPauseThread = false;
        synchronized (this) {
            notify();
        }
    }
}

