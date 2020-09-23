package in.bitcode.progressdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDownload = findViewById(R.id.btnDownload);
        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tag", Thread.currentThread().getName());

                String[] urls = {
                        "http://bitcode.in/android/file1",
                        "http://bitcode.in/android/file2",
                        "http://bitcode.in/android/file3",
                        "http://bitcode.in/android/file4"
                };
                new MyDownloadThread()
                        .execute(urls);

            }
        });
    }

    class MyDownloadThread extends AsyncTask<String, Integer, Float> {

        //private String mUrl;
        /*public MyDownloadThread(String url) {
            mUrl = url;
        }*/

        //Executed by main thread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("tag", "onPreExecute: " + Thread.currentThread().getName());
            mBtnDownload.setEnabled(false);
        }

        //Executed by worker thread
        @Override
        protected Float doInBackground(String... urls) {

            Log.e("tag", "doInBackground: " + Thread.currentThread().getName());

            for (String url : urls) {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("tag", url + " " + i + "%");

                    Integer[] iResults = {i};
                    publishProgress(iResults);
                }
            }
            return 80.80f;
        }

        //Executed by main thread
        @Override
        protected void onPostExecute(Float res) {
            super.onPostExecute(res);

            Log.e("tag", "onPostExecute " + res + " " + Thread.currentThread().getName());

            mBtnDownload.setText(res + "");
            if (!mBtnDownload.isEnabled()) {
                mBtnDownload.setEnabled(true);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e("tag", "onProgressUpdate " + Thread.currentThread().getName() );
            mBtnDownload.setText( values[0] +  "%");
        }
    }


    class DownloadThread extends Thread {

        private String mUrl;

        public DownloadThread(String url) {
            mUrl = url;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 100; i++) {
                //mBtnDownload.setText(i + "%");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("tag", mUrl + " " + i + "%");
            }
        }
    }
}










