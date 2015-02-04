package za.co.eboxi.ble;

/**
 * Created by Otto on 2015/01/13.
 * Copyright OttoS (2015)
 * All rights reserved.
 */


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import de.greenrobot.event.EventBus;

///import com.thedamfr.android.BleEventAdapter.BleEventBus;
///import com.thedamfr.android.BleEventAdapter.events.DiscoveredDevicesEvent;
///import com.thedamfr.android.BleEventAdapter.events.ScanningEvent;
///import com.squareup.otto.Produce;


public class BluetoothLeScanService2 extends Service {
    private static final long SCAN_PERIOD = 150000;
    //private BleEventBus mBleEventBus;
    private EventBus mBleEventBus;
    private Set<BluetoothDevice> mBluetoothDevices = new HashSet<BluetoothDevice>();
    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private boolean mScanning = false;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ///mBleEventBus = BleEventBus.getInstance();
        ///mBleEventBus.register(this);
        mBleEventBus = EventBus.getDefault();
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(this.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
// Toast.makeText(this, "Pas de Ble, Blaireau", Toast.LENGTH_SHORT).show();
            stopSelf();
        } else {
            scanLeDevice(true);
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
           Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();
        mBleEventBus = EventBus.getDefault();
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(this.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            stopSelf();
        } else {
            scanLeDevice(true);
        }
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            startScanning();
        } else {
            stopScanning();
        }
    }
    private void startScanning() {
        mHandler = new Handler();
// Stops scanning after a
//... pre-defined scan period.
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopScanning();
            }
        }, SCAN_PERIOD);
        mScanning = true;
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        ///mBleEventBus.post(new ScanningEvent(mScanning));
    }
    private void stopScanning() {
        mScanning = false;
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        ///mBleEventBus.post(new ScanningEvent(false));
        ///DeviceDiscoveryService.this.stopSelf();

        // I don't want this service to stay in memory, so I stop it
        // immediately after doing what I wanted it to do.
        //stopSelf();
    }


    public static class BLEscanDeviceFoundEvent {
        public String name;
        public String id;
        public long   time =0;
        public int    rssi;
        public  BLEscanDeviceFoundEvent(String name,String id, int _rssi, long _time)
        {
            this.name = name;
            this.id   = id;
            time =_time; rssi = _rssi;
        }
    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    mBluetoothDevices.add(device);
                    ///mBleEventBus.post(new DiscoveredDevicesEvent(device, mBluetoothDevices));
                    String name = device.getName();
                    if (name.startsWith("LQ") || name.startsWith("esti")) {
                        Log.w("BLE name", name);
                        long t = System.currentTimeMillis();
                        mBleEventBus.post(new BLEscanDeviceFoundEvent(name,device.getAddress(),rssi,t));
                        //stopScan();
                        //connect(device.getName());
                        // kick of re-evaluation of conditions
                        //EventBus.getDefault().post(new HAGeventMsg());
                    }
                    else {
                        Log.w("BLE= ", name + " " + device.getAddress());

                    }

                }
            };

//    @Produce
//    public DiscoveredDevicesEvent produceAnswer() {
//        return new DiscoveredDevicesEvent(null, mBluetoothDevices);
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopScanning();
        mBleEventBus.unregister(this);

        // I want to restart this service again in 10 sec?
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.set(
                alarm.RTC_WAKEUP,
                System.currentTimeMillis() + (1000 * 10),
                PendingIntent.getService(this, 0, new Intent(this, BluetoothLeScanService2.class), 0)
        );
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
}