package com.example.sharangirdhani.inclass04;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    //ExecutorService threadPool;
    SeekBar sb_count;
    SeekBar sb_length;

    Handler handler;
    ProgressDialog progressDialog;
    TextView password_value;
    TextView password_count;
    TextView password_length;

    ArrayList<String> data;

    int current_password_count;
    int current_password_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //threadPool = Executors.newFixedThreadPool(2);

        sb_count = (SeekBar) findViewById(R.id.seekBar_count);
        sb_length = (SeekBar) findViewById(R.id.seekBar_length);

        password_count = (TextView) findViewById(R.id.password_count);
        password_length = (TextView) findViewById(R.id.password_length);
        password_value = (TextView) findViewById(R.id.password_value);

        current_password_count = 1;
        current_password_length = 8;

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Generating Passwords ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch(msg.what) {
                    case DoWork.STATUS_START:
                        progressDialog.show();
                        progressDialog.setProgress(0);
                        break;
                    case DoWork.STATUS_STEP:
                        progressDialog.setProgress((Integer) msg.obj);
                        break;
                    case DoWork.STATUS_DONE:
                        data = msg.getData().getStringArrayList("PASSWORDS");
                        progressDialog.dismiss();
                        showPasswords();
                        break;
                }
                return false;
            }
        });

        sb_count.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current_password_count = progress+1;
                password_count.setText(String.valueOf(current_password_count));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current_password_length = progress+8;
                password_length.setText(String.valueOf(current_password_length));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.btn_password_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService threadPool = Executors.newFixedThreadPool(2);
                threadPool.execute(new DoWork());
            }
        });

        findViewById(R.id.btn_async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoWorkAsync().execute(current_password_length, current_password_count);
            }
        });

    }

    public void showPasswords(){
        // Create new alert dialog to pick password
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Passwords");
        alertDialog.setCancelable(false);

        CharSequence[] psw = data.toArray(new CharSequence[data.size()]);
        alertDialog.setItems(psw, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                password_value.setText(data.get(which));
            }
        });
        alertDialog.show();
    }

    class DoWork implements Runnable {

        ArrayList<String> data = new ArrayList<>();
        Message msg = new Message();
        Bundle msgData = new Bundle();

        static final int STATUS_START = 0x00;
        static final int STATUS_STEP = 0x01;
        static final int STATUS_DONE = 0x02;


        @Override
        public void run() {

            msg = new Message();
            msg.what = STATUS_START;
            handler.sendMessage(msg);

            for (int i = 0; i < current_password_count; i++) {
                String pass = Util.getPassword(current_password_length);
                data.add(pass);

                msg = new Message();
                msg.what = STATUS_STEP;
                int progress = 100/current_password_count;
                msg.obj = (i + 1) * progress;
                handler.sendMessage(msg);
            }
            msg = new Message();
            msgData.putStringArrayList("PASSWORDS",data);
            msg.what = STATUS_DONE;
            msg.setData(msgData);
            handler.sendMessage(msg);
        }
    }

    class DoWorkAsync extends AsyncTask<Integer, Integer, ArrayList<String> > {
        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            ArrayList<String> data = new ArrayList<>();

            int length = params[0];
            int count = params[1];

            for (int index = 1; index <= count; index++){
                data.add(Util.getPassword(length));
                int progressStep = 100 / count;
                publishProgress(index * progressStep);
            }
            return data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            progressDialog.setProgress(0);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            progressDialog.dismiss();
            data = strings;
            showPasswords();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }
}
