package com.yf.aidlmodule.binders;

import android.os.Process;
import android.os.RemoteException;

import com.yf.aidlmodule.IPhone;
import com.yf.aidlmodule.utils.LogUtils;


/**
 * FileName :  Phone
 * Author   :  zhizhongbiao
 * Date     :  2018/8/23
 * Describe :
 */

public class Phone extends IPhone.Stub {
    @Override
    public void call(String phoneNum) throws RemoteException {
        LogUtils.e("call  phoneNum="+phoneNum +" ----Thread="+Thread.currentThread().getName()+" ----Pid="+ Process.myPid());
    }
}
