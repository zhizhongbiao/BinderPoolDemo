package com.yf.binderpooldemo.client;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yf.aidlmodule.IMusic;
import com.yf.aidlmodule.IPhone;
import com.yf.aidlmodule.IRadio;
import com.yf.binderpooldemo.AppConfig;
import com.yf.binderpooldemo.R;
import com.yf.binderpooldemo.server.BinderPool;
import com.yf.binderpooldemo.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private BinderPool binderPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                binderPool = BinderPool.getInstance(MainActivity.this);
            }
        }).start();

    }

    @OnClick({R.id.btnRadio, R.id.btnMusic, R.id.btnPhone})
    public void onViewClicked(View view) {
        LogUtils.e("view="+view+"    ----Thread="+Thread.currentThread().getName()+" ----Pid="+ Process.myPid());
        try {
            switch (view.getId()) {
                case R.id.btnRadio:
                    IBinder radioBinder = binderPool.queryBinder(AppConfig.BinderCode.CODE_BINDER_RADIO);
                    IRadio iRadio = IRadio.Stub.asInterface(radioBinder);
                    iRadio.playRadio();
                    break;
                case R.id.btnMusic:
                    IBinder musicBinder = binderPool.queryBinder(AppConfig.BinderCode.CODE_BINDER_MUSIC);
                    IMusic iMusic = IMusic.Stub.asInterface(musicBinder);
                    iMusic.playMusic();
                    break;
                case R.id.btnPhone:
                    IBinder phoneBinder = binderPool.queryBinder(AppConfig.BinderCode.CODE_BINDER_PHONE);
                    IPhone iPhone = IPhone.Stub.asInterface(phoneBinder);
                    iPhone.call("12345678990");
                    break;
            }
        } catch (RemoteException e) {
            LogUtils.e("onViewClicked   e="+e.getMessage());
            e.printStackTrace();
        }

    }
}
