package com.yf.aidlmodule;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;


import com.yf.aidlmodule.binders.Music;
import com.yf.aidlmodule.binders.Phone;
import com.yf.aidlmodule.binders.Radio;
import com.yf.aidlmodule.utils.AppConfig;
import com.yf.aidlmodule.utils.LogUtils;

import java.util.concurrent.CountDownLatch;

/**
 * FileName :  BinderPool
 * Author   :  zhizhongbiao
 * Date     :  2018/8/23
 * Describe :
 */

public class BinderPool
        implements ServiceConnection
        , IBinder.DeathRecipient {

    private Context context;
    private static BinderPool instance;
    private CountDownLatch countDownLatch;
    private IBinderPool binderPool;

    private BinderPool(Context context) {
        this.context = context.getApplicationContext();
        connectToRemoteService(this.context);
    }

    public static BinderPool getInstance(Context context) {
        if (instance == null) {
            synchronized (BinderPool.class) {
                if (instance == null) {
                    instance = new BinderPool(context);
                }
            }
        }
        return instance;
    }

    public IBinder queryBinder(int binderCode) throws RemoteException {
        LogUtils.e("queryBinder ----Thread=" + Thread.currentThread().getName() + " ----Pid=" + Process.myPid());
        return binderPool.queryBinder(binderCode);
    }

    private synchronized void connectToRemoteService(Context context) {
        LogUtils.e("connectToRemoteService ----Thread=" + Thread.currentThread().getName() + " ----Pid=" + Process.myPid());
        Intent intent = new Intent(AppConfig.AppAction.ACTION_REMOTE_SERVICE);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);

        //此类是用来线程等待的，等待绑定远程的服务返回Binder才释放
        countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            LogUtils.e("线程同步出错  exception=" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binderPool = IBinderPool.Stub.asInterface(service);

        try {
            //监听远程服务连接异常断开死亡
            binderPool.asBinder().linkToDeath(this, 0);
        } catch (RemoteException e) {
            LogUtils.e("远程出错  exception=" + e.getMessage());
            e.printStackTrace();
        }

        //释放线程等待
        countDownLatch.countDown();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    /**
     * 连接异常死亡后，重连
     */
    @Override
    public void binderDied() {
        binderPool.asBinder().unlinkToDeath(this, 0);
        binderPool = null;
        connectToRemoteService(context);
    }


    /**
     * 返回给client端BinderPool池这个Binder的实现类
     */
    public static class IBinderPoolImp extends IBinderPool.Stub {

        /**
         * 返回真正对应需要的Binder
         *
         * @param binderCode
         * @return
         * @throws RemoteException
         */
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            LogUtils.e("IBinderPoolImp   queryBinder ----Thread=" + Thread.currentThread().getName() + " ----Pid=" + Process.myPid());
            IBinder binder = null;
            switch (binderCode) {
                case AppConfig.BinderCode.CODE_BINDER_MUSIC:
                    binder = new Music();
                    break;
                case AppConfig.BinderCode.CODE_BINDER_RADIO:
                    binder = new Radio();
                    break;
                case AppConfig.BinderCode.CODE_BINDER_PHONE:
                    binder = new Phone();
                    break;
            }
            return binder;
        }
    }

}
