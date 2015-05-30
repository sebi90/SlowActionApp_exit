package sebi.slowactionapp_exit;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Sebi on 30.05.15.
 */
class Request implements Runnable {
    private EditText input;
    private TextView output;
    private String message;

    public Request(EditText input, String message) {
        this.input = input;
        this.message = message;
    }

    public Request(TextView output, String message) {
        this.output = output;
        this.message = message;
    }

    public void run() {
        // Wird von main Sekunden+1 mal aufgerufen
        // Sekunden-mal innherhalb der while Schleife
        // und einmal hinter der while Schleife
        Log.d("run_Request", Thread.currentThread().getName());
        if (input != null) {
            input.setText(message);
        } else if (output != null) {
            output.setText(message);
        }
    }
}
