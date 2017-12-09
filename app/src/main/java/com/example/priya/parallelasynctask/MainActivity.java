AppCompatpackage com.example.priya.parallelasynctask;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

public class MainActivity extends AppCompactActivity {
    
// AsyncTask
    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        ProgressBar myProgressBar;

        public MyAsyncTask(ProgressBar target) {
            myProgressBar = target;
        }

        @Override
        // Perform an operation on a background thread
        protected Void doInBackground(Void... params) {
            for(int i=0; i<100; i++){
                publishProgress(i);
                SystemClock.sleep(100);
            }
            return null;
        }

        @Override
        // Runs on UI thread after publishProgress(Progress...) is invoked from doInBackground()
        protected void onProgressUpdate(Integer... values) {
            myProgressBar.setProgress(values[0]);
        }

    }

    Button buttonStart;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    MyAsyncTask asyncTask1, asyncTask2, asyncTask3, asyncTask4, asyncTask5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar1 = (ProgressBar)findViewById(R.id.progressbar1);
        progressBar2 = (ProgressBar)findViewById(R.id.progressbar2);
        progressBar3 = (ProgressBar)findViewById(R.id.progressbar3);
        progressBar4 = (ProgressBar)findViewById(R.id.progressbar4);
        progressBar5 = (ProgressBar)findViewById(R.id.progressbar5);

        buttonStart = (Button)findViewById(R.id.start);
        buttonStart.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                asyncTask1 = new MyAsyncTask(progressBar1);
                asyncTask1.execute();
                asyncTask2 = new MyAsyncTask(progressBar2);
                asyncTask2.execute();
                asyncTask3 = new MyAsyncTask(progressBar3);
                asyncTask3.execute();
                asyncTask4 = new MyAsyncTask(progressBar4);
                StartAsyncTaskInParallel(asyncTask4);
                asyncTask5 = new MyAsyncTask(progressBar5);
                StartAsyncTaskInParallel(asyncTask5);
            }});

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(MyAsyncTask task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }

}
