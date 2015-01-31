package za.co.eboxi.ble;

/**
 * Created by Otto on 2015/01/13.
 * Copyright OttoS (2015)
 * All rights reserved.
 */


import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class BluetoothLeScanService extends Service implements LeScanCallback
{


    private static String mBluetoothDeviceAddress = null;
    private ArrayList<BluetoothDevice> mDevices= new ArrayList<BluetoothDevice>();
    private BluetoothGatt mConnectedGatt;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;


    private static int mConnectionState;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;
    private static final int REQUEST_ENABLE_BT = 0;
    private Handler mHandler = new Handler();


    public void startscanning ()
    {
        mHandler.post(mStartRunnable);
        mHandler.postDelayed(mStopRunnable, 3000);
    }


    Handler mhandler = new Handler();
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
//        mservice getService() {
//            // Return this instance of LocalService so clients can call public
//            // methods
//            return mservice.this;
//        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    @Override
    public void onCreate() {


//        // Use this check to determine whether BLE is supported on the device.  Then you can
//        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Device does not support Bluetooth LE", Toast.LENGTH_SHORT).show();
            return;
        }
//
//        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
//        // BluetoothAdapter through BluetoothManager.
//        final BluetoothManager bluetoothManager =
//                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//        mBluetoothAdapter = bluetoothManager.getAdapter();
//
//        // Checks if Bluetooth is supported on the device.
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//


        mBluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter= mBluetoothManager.getAdapter();
//        if (mBluetoothAdapter == null) {
        super.onCreate();
    }


    @Override
    public void onRebind(Intent intent) {
        // TODO Auto-generated method stub
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        return super.onUnbind(intent);
    }


    final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            // TODO Auto-generated method stub
            super.onConnectionStateChange(gatt, status, newState);

            if (newState == BluetoothProfile.STATE_CONNECTED) {

                Toast.makeText(getApplicationContext(), "BLE conneced", Toast.LENGTH_SHORT).show();

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Toast.makeText(getApplicationContext(), "BLE disconnected", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            // TODO Auto-generated method stub
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            // TODO Auto-generated method stub
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
            // TODO Auto-generated method stub
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            // TODO Auto-generated method stub
            super.onCharacteristicChanged(gatt, characteristic);
            Toast.makeText(getApplicationContext(), "char changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt,
                                     BluetoothGattDescriptor descriptor, int status) {
            // TODO Auto-generated method stub
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt,
                                      BluetoothGattDescriptor descriptor, int status) {
            // TODO Auto-generated method stub
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            // TODO Auto-generated method stub
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            // TODO Auto-generated method stub
            super.onReadRemoteRssi(gatt, rssi, status);
        }


    };
    private Runnable mStopRunnable = new Runnable() {
        @Override
        public void run() {
            stopScan();
        }
    };
    private Runnable mStartRunnable = new Runnable() {
        @Override
        public void run() {
            startScan();
        }
    };

    public void startScan() {
        mBluetoothAdapter.startLeScan(this);

    }

    public void stopScan() {
        mBluetoothAdapter.stopLeScan(this);

    }

    public static class xxBLEscanEvent {
        public String name;
        xxBLEscanEvent(String name) { this.name = name; }
    }

    // results from scan received callback
    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        String name = device.getName();
        if (name.startsWith("LQ-")) {
            Log.w("BLE name",name);
            EventBus.getDefault().post(new xxBLEscanEvent(name));
            //stopScan();
            //connect(device.getName());
        }

    }

//    public boolean connect(final String address) {
//        String TAG="connecting situation";
//        if (mBluetoothAdapter == null || address == null) {
//            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
//            return false;
//        }
//
//
//        // Previously connected device.  Try to reconnect.
//        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
//                && mBluetoothGatt != null) {
//            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
//            if (mBluetoothGatt.connect()) {
//                mConnectionState = STATE_CONNECTING;
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//        if (device == null) {
//            Log.w(TAG, "Device not found.  Unable to connect.");
//            return false;
//        }
//        // We want to directly connect to the device, so we are setting the autoConnect
//        // parameter to false.
//        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
//        Log.d(TAG, "Trying to create a new connection.");
//        mBluetoothDeviceAddress = address;
//        mConnectionState = STATE_CONNECTING;
//        return true;
//    }


}