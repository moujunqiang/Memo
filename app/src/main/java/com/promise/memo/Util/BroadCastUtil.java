package com.promise.memo.Util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

/**
 * Created by zsl on 2019/7/31.
 * <p>
 * 描述：
 */
public class BroadCastUtil {

    /**
     * 自定义广播发送和接收权限校验
     */
    public static final String BROADCAST_PERMISSION = "com.vcom.app.receiver.receivebroadcast";

    /**
     * 通过指定包名发送显示广播，
     * 突破8.0对静态注册、隐式广播的限制
     *
     * @param context
     * @param intent
     */
    public static void sendBroadInApp(Context context, Intent intent) {
        if (context == null) {
            return;
        }

        // 改为前台广播，后台广播有时会阻塞3分钟
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        context.sendBroadcast(intent, BroadCastUtil.BROADCAST_PERMISSION);
    }

    /**
     * 发送广播到其他应用
     */
    @SuppressLint("WrongConstant")
    public static void sendBroadToOtherApp(Context context, Intent intent) {
        if (context == null) {
            return;
        }
        //FLAG_RECEIVER_INCLUDE_BACKGROUND
        intent.addFlags(0x01000000);//突破隐式限制
        context.sendBroadcast(intent);
    }

    /**
     * 动态注册广播
     *
     * @param context
     * @param broadcastReceiver
     * @param intentFilter
     */
    public static void registerBroadCast(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        if (context == null) {
            return;
        }
        context.registerReceiver(broadcastReceiver, intentFilter, BroadCastUtil.BROADCAST_PERMISSION, null);
    }
}
