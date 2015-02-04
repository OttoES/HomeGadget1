package za.co.eboxi.hag.conditions;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import za.co.eboxi.ble.BluetoothLeScanService2;

/**
 * Created by OttoES on 2015/01/13.
 */
public class HAGcondBLErange extends HAGcondition {


    private static final String JSON_SIGNAL_MIN_TAG = "signalMin";
    private static final String JSON_SIGNAL_MAX_TAG = "signalMax";
    private static final String JSON_TAG_TIMEOUT = "timeOut";
    private static final String JSON_BLE_ID_TAG = "ID";

    private final static int HIST_LEN = 3;
    private int[] mRssis = new int[HIST_LEN];
    private long[] mTimeStamp = new long[HIST_LEN];

    String bleID;
    int    sigMax;
    int    sigMin;
    int    mTimeout;
    //boolean condition = false;   // is the condition met or not
    long   lastDetectedTime =0;

    @Override
    public void Load(JSONObject jobj) throws JSONException {
        bleID  = jobj.getString(JSON_BLE_ID_TAG);
        sigMax = jobj.optInt(JSON_SIGNAL_MAX_TAG, -10);
        sigMin = jobj.optInt(JSON_SIGNAL_MIN_TAG, -90);
        mTimeout  = jobj.optInt(JSON_TAG_TIMEOUT,10)*1000;
        EventBus.getDefault().register(this);

    }

    private boolean isActive(long t)
    {
        // too long ago detected - 6 sec window
        if (t > mTimeStamp[HIST_LEN-1]+6000) return false;
        for (int i=0; i<HIST_LEN ; i++) {
           if (mRssis[i] < sigMin ) return false;
           if (mRssis[i] > sigMax ) return false;
       }
        return true;
    }

    private void addRssi(int rssi, long t)
    {
        for (int i=1; i<HIST_LEN ; i++) {
            mRssis[i]     = mRssis[i-1];
            mTimeStamp[i] = mTimeStamp[i-1];
        }
        mRssis[0]     = rssi;
        mTimeStamp[0] = t;

    }

    @Override
    public boolean Evaluate()
    {
        long t = System.currentTimeMillis();
        return (t-lastDetectedTime)<mTimeout;
    }

    // this function will be called by the EventBus on a BLE scan match
    public void onEvent(BluetoothLeScanService2.BLEscanDeviceFoundEvent evt)
    {
        int sigStrength = evt.rssi;
        // check address and signal strength
//        if (bleID.equalsIgnoreCase(evt.id) && (sigStrength < sigMax) && (sigStrength > sigMin)) {
         if (bleID.equalsIgnoreCase(evt.id) ) {
             addRssi(sigStrength,evt.time);
             if (isActive(evt.time)) {
                 //Context context = HAGapplication.getAppContext();
                 //        Toast.makeText(context, "BLE found "+evt.name, Toast.LENGTH_LONG).show();
                 lastDetectedTime = evt.time;
                 Log.i("HAGcondBLErange", "BLE event received " + evt.id);
                 //ReevaluateConditions();
                 EventBus.getDefault().post(new HAGeventMsg());
             }
        }
    }


}
