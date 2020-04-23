package com.dev.eda.app.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.eda.R;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;

import java.text.NumberFormat;

public class CommonProgressDialog extends AlertDialog {


    private ProgressBar mProgress;
    private TextView mProgressNumber;
    private TextView mProgressPercent;
    private TextView mProgressMessage;
    private Button mOkButton;

    private Handler mViewUpdateHandler;
    private int mMax;
    private CharSequence mMessage;
    private boolean mHasStarted;
    private int mProgressVal;

    private String TAG="CommonProgressDialog";
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;
    public CommonProgressDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initFormats();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_progress_dialog);
        mProgress=(ProgressBar) findViewById(R.id.progress);
        mProgressNumber=(TextView) findViewById(R.id.progress_number);
        mProgressPercent=(TextView) findViewById(R.id.progress_percent);
        mProgressMessage=(TextView) findViewById(R.id.progress_message);
        mOkButton = findViewById(R.id.ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("asd","asdsdasd");
            }
        });
//      LayoutInflater inflater = LayoutInflater.from(getContext());
        mViewUpdateHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int progress = mProgress.getProgress();
                int max = mProgress.getMax();
                double dProgress = (double)progress/(double)(1024 * 1024);
                double dMax = (double)max/(double)(1024 * 1024);
                if (mProgressNumberFormat != null) {
                    String format = mProgressNumberFormat;
                    mProgressNumber.setText(String.format(format, dProgress, dMax));
                } else {
                    mProgressNumber.setText("");
                }
                if (mProgressPercentFormat != null) {
                    double percent = (double) progress / (double) max;
                    SpannableString tmp = new SpannableString(mProgressPercentFormat.format(percent));
                    tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mProgressPercent.setText(tmp);
                } else {
                    mProgressPercent.setText("");
                }
                return false;
            }
        });
//      View view = inflater.inflate(R.layout.common_progress_dialog, null);
//        mProgress = (ProgressBar) view.findViewById(R.id.progress);
//        mProgressNumber = (TextView) view.findViewById(R.id.progress_number);
//        mProgressPercent = (TextView) view.findViewById(R.id.progress_percent);
//        setView(view);
        //mProgress.setMax(100);
        onProgressChanged();
        if (mMessage != null) {
            setMessage(mMessage);
        }
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal);
        }
    }
    private void initFormats() {
        mProgressNumberFormat = "%1.2fM/%2.2fM";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }
    private void onProgressChanged() {
        mViewUpdateHandler.sendEmptyMessage(0);


    }
    public void setProgressStyle(int style) {
        //mProgressStyle = style;
    }
    public int getMax() {
        if (mProgress != null) {
            return mProgress.getMax();
        }
        return mMax;
    }
    public void setMax(int max) {
        if (mProgress != null) {
            mProgress.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }
    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        }
//      else {
//            mIndeterminate = indeterminate;
//        }
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            mProgress.setProgress(value);
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        // TODO Auto-generated method stub
        //super.setMessage(message);
        if(mProgressMessage!=null){
            mProgressMessage.setText(message);
        }
        else{
            mMessage = message;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mHasStarted = true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }

    public void setButton(String text, View.OnClickListener listener){
        mOkButton.setText(text);
        mOkButton.setOnClickListener(listener);
    }

}