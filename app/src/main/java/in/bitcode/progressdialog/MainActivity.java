package in.bitcode.progressdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
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
                };
                new MyDownloadThread( MainActivity.this, new MyHandler() )
                        .execute(urls);

            }
        });
    }

    public class MyHandler extends android.os.Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if( msg == null ) {
                return;
            }

            if( msg.arg1 == Config.RES_FINAL ) {
                String[] localUrls = (String[]) msg.obj;
                for (String localUrl : localUrls) {
                    Log.e("tag", localUrl);
                }
            }
            if( msg.arg1 == Config.RES_INETRMEDIATE ) {
                Integer [] iResults = (Integer[]) msg.obj;
                mBtnDownload.setText( iResults[0] + "%");
            }
        }
    }

}










