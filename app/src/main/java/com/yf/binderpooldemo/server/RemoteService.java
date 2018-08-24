package com.yf.binderpooldemo.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;

import com.yf.aidlmodule.BinderPool;
import com.yf.aidlmodule.utils.LogUtils;


/**
 * FileName :  RemoteService
 * Author   :  zhizhongbiao
 * Date     :  2018/8/23
 * Describe :
 */

public class RemoteService extends Service {

    private Binder mBinderPool;
    private int pid =Process.myPid();

    @Override
    public void onCreate() {
        LogUtils.e("RemoteService   ******   onCreate ");
        mBinderPool = new BinderPool.IBinderPoolImp();

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("RemoteService   ----   onBind     pid="+pid);
        return mBinderPool;
    }
}
