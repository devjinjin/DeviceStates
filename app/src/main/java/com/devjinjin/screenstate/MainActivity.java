package com.devjinjin.screenstate;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.devjinjin.screenstate.Device.DeviceBroadCastReceiver;
import com.devjinjin.screenstate.Device.DeviceManager;

public class MainActivity extends AppCompatActivity {

    DeviceBroadCastReceiver mBroadCast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int percent = DeviceManager.getInstance().getBatteryPercentage(getApplication().getBaseContext());
                boolean isOnline = DeviceManager.getInstance().isOnline(getApplication().getBaseContext());
                Snackbar.make(view, "Bettery state : "+percent+"%\n"+"Online State : "+isOnline, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        mBroadCast = new DeviceBroadCastReceiver();
        DeviceManager.getInstance().setKeepScreen(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED); //충전상태 변경
        filter.addAction(Intent.ACTION_BATTERY_LOW); //배러리 상태 낮음
        filter.addAction(Intent.ACTION_BATTERY_OKAY); // 낮음상태에서 양호한상태로 변경

        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(mBroadCast, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mBroadCast);
    }
}
