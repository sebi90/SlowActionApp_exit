package sebi.slowactionapp_exit;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private LongRunningThread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clicked(View v) {
        // Wird einmal von main aufgerufen
        Log.i("clicked", Thread.currentThread().getName());
        EditText input = (EditText) findViewById(R.id.input);
        String s = input.getText().toString();
        TextView output = (TextView) findViewById(R.id.output);

        try {
            long total = Long.parseLong(s);
            if (total < 0) {
                throw new NumberFormatException();
            }
            String format = "SUCCESS";
            t = new LongRunningThread(total, input, output, format);
            t.start();
        }
        catch (Exception e) {
            String message = "FAILURE" + " " + e.getMessage();
            output.setText(message);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Log.d("Main", "OnDestroy");
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        Log.d("Main", "OnPause");
        if (t != null) {
            t.pauseThread();
        }
        super.onPause();

    }

    @Override
    protected void onStop() {
        Log.d("Main", "OnStop");
        super.onStop();

    }

    @Override
    protected void onResume() {
        Log.d("Main", "OnResume");
        if(t != null) {
            t.resumeThread();
        }
        super.onResume();
    }
}
