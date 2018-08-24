package com.yf.aidlmodule.utils;

/**
 * FileName :  AppConfig
 * Author   :  zhizhongbiao
 * Date     :  2018/8/23
 * Describe :
 */

public interface AppConfig {

    interface AppAction {
        String ACTION_REMOTE_SERVICE = "com.yf.binderpooldemo.action.REMOTE_SERVICE";
    }

    interface BinderCode {
        int CODE_BINDER_MUSIC = 99;

        int CODE_BINDER_RADIO = 88;

        int CODE_BINDER_PHONE = 77;
    }

}
