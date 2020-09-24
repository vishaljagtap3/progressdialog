package in.bitcode.progressdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MyDownloadThread extends AsyncTask<String, Integer, String[]> {

    private ProgressDialog mProgressDialog;
    private Context mContext;
    private Handler mHandler;

    public MyDownloadThread( Context context, Handler handler) {
        this.mContext = context;
        mHandler = handler;
    }

    //Executed by main thread
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("tag", "onPreExecute: " + Thread.currentThread().getName());
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("BitCode Cloud!");
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIcon( R.mipmap.ic_launcher);
        mProgressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    //Executed by worker thread
    @Override
    protected String [] doInBackground(String... urls) {

        Log.e("tag", "doInBackground: " + Thread.currentThread().getName());

        String [] localUrls = new String[ urls.length ];
        int index = 0;

        for (String url : urls) {
            //mProgressDialog.setMessage("Downloading \n" + url );
            for (int i = 0; i <= 100; i++) {
                mProgressDialog.setProgress( i );
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("tag", url + " " + i + "%");

                Integer[] iResults = {i};
                publishProgress(iResults);
            }

            String [] paths = url.split("/");
            localUrls[index++] = "/sdacard/bitcode/downloads/" + paths[ paths.length-1];
        }

        return localUrls;
    }

    //Executed by main thread
    @Override
    protected void onPostExecute(String [] localUrls) {
        super.onPostExecute( localUrls );
        mProgressDialog.dismiss();

        Message message = new Message();
        message.arg1 = Config.RES_FINAL;
        message.obj = localUrls;

        mHandler.sendMessage( message );
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        Message message = new Message();
        message.arg1 = Config.RES_INETRMEDIATE;
        message.obj = values;
        mHandler.sendMessage( message );
    }
}
