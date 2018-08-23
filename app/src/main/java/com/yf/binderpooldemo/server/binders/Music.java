package com.yf.binderpooldemo.server.binders;

import android.os.Process;
import android.os.RemoteException;

import com.yf.aidlmodule.IMusic;
import com.yf.aidlmodule.IRadio;
import com.yf.binderpooldemo.utils.LogUtils;

/**
 * FileName :  Phone
 * Author   :  zhizhongbiao
 * Date     :  2018/8/23
 * Describe :
 */

public class Music extends IMusic.Stub {

    @Override
    public void playMusic() throws RemoteException {
        LogUtils.e("playMusic ----Thread="+Thread.currentThread().getName()+" ----Pid="+ Process.myPid());
    }
}
