package com.devjinjin.screenstate.Device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.widget.Toast;

public class DeviceBroadCastReceiver extends BroadcastReceiver {

    public DeviceBroadCastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            onBatteryChanged(context, intent);
        }else if (action.equals(Intent.ACTION_BATTERY_LOW)) {
            Toast.makeText(context, "ACTION_BATTERY_LOW", Toast.LENGTH_LONG).show();
        }else if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
            Toast.makeText(context, "ACTION_BATTERY_OKAY", Toast.LENGTH_LONG).show();
        }else if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){

            boolean isOnline = DeviceManager.getInstance().isOnline(context);
            Toast.makeText(context, "CONNECTIVITY_ACTION : "+isOnline, Toast.LENGTH_LONG).show();
        }
    }

    public void onBatteryChanged(Context context, Intent intent){
        int plug, status, scale, level, ratio;
        String sPlug = "";
        String sStatus = "";

        //배터리가 존재하는 지
        if (intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false) == false){
            Toast.makeText(context, "no battery", Toast.LENGTH_LONG).show();
            return;
        }

        plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0); // 외부전원이 연결되어 있는지 확인
        status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN); //배터리 현재상태
        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100); //배터리 최대량
        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0); //현재 충전 상태 확인
        ratio = level * 100 / scale;

        switch (plug){
            case BatteryManager.BATTERY_PLUGGED_AC:
                sPlug = "AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                sPlug = "USB";
                break;
            default:
                sPlug = "BATTERY";
                break;
        }

        switch (status){
            case BatteryManager.BATTERY_STATUS_CHARGING:
                sStatus = "Charging";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                sStatus = "not charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                sStatus = "discharging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                sStatus = "fully charged";
                break;
            default:
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                sStatus = "Unknwon status";
                break;
        }

//        Toast.makeText(context, "Connected: "+ sPlug +"\ncurrent state : "+sStatus, Toast.LENGTH_LONG).show();
    }
}
