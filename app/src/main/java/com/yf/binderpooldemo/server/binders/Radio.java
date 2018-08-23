package com.yf.binderpooldemo.server.binders;

import android.os.Process;
import android.os.RemoteException;

import com.yf.aidlmodule.IPhone;
import com.yf.aidlmodule.IRadio;
import com.yf.binderpooldemo.utils.LogUtils;

/**
 * FileName :  Phone
 * Author   :  zhizhongbiao
 * Date     :  2018/8/23
 * Describe :
 */

public class Radio extends IRadio.Stub {


    @Override
    public void playRadio() throws RemoteException {
        LogUtils.e("playRadio   ----Thread="+Thread.currentThread().getName()+" ----Pid="+ Process.myPid());
    }
}
