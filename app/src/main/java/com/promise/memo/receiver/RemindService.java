package com.promise.memo.receiver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;


import com.promise.memo.Util.BroadCastUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 描述：定时提醒休息，计时服务
 */
public class RemindService extends Service {
    private Timer timer;
    private TimerTask timerTask;
    MyHandler myHandler;
    int time_runned = -1;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int time = intent.getIntExtra("timeLenth", -1);
            if (time != -1) {
                setTimeLenthAndStart(time);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 设置计时时长
     *
     * @param timeLenth
     */
    public void setTimeLenthAndStart(int timeLenth) {
        this.time_runned = timeLenth;
        startCount();
    }

    void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (myHandler == null) {
            myHandler = new MyHandler();
        }

    }

    /**
     * 开始计时
     */
    private void startCount() {
        //防止多次点击开启计时器
        stopTimer();

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                myHandler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                time_runned--;
//                LogUtils.e("RemindAction", time_runned + "");

                if (time_runned == 0) {
                    //时间到
                    Intent intent = new Intent();
                    intent.setAction("com.uxin.RemindAction");
                    BroadCastUtil.sendBroadInApp(getBaseContext(), intent);
                    stopTimer();
                }
            }
        }
    }

}
