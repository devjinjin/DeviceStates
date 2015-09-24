package com.devjinjin.screenstate.Device;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.view.WindowManager;

/**
 * Created by JYLEE on 2015-09-24.
 */
public class DeviceManager {

    static DeviceManager instance = null;

    public static DeviceManager getInstance() {

        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }

    private DeviceManager() {

    }

    //화면 켜짐 유지
    public void setKeepScreen(Activity pActivity) {
        if (pActivity != null) {
            pActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    // 배터리 양 체크
    public int getBatteryPercentage(Context context) {
        Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float) scale;
        return (int) (batteryPct * 100);
    }


    //현재 네트워크 상태 체크
    public boolean isOnline(Context context) {

        NetworkInfo info;
        try {

            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Network[] networks = manager.getAllNetworks();

                for (Network network : networks) {

                    info = manager.getNetworkInfo(network);

                    if (info != null) {
                        if (info.getType() == ConnectivityManager.TYPE_MOBILE && info.isConnected()) {
                            return true;
                        }
                        if (info.getType() == ConnectivityManager.TYPE_WIFI && info.isConnected()) {
                            return true;
                        }
                    }
                }

            } else {

                NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (wifiInfo != null) {
                    if (wifiInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
                NetworkInfo mobileNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileNetwork != null) {
                    if (mobileNetwork.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

            }
        } catch (Exception e) {

        }

        return false;
    }


}
