package za.co.eboxi.hag.conditions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John2 on 2015/01/02.
 */
public class HAGcondEventTriggered extends HAGcondition {
    private static final String JSON_SIGNAL_MIN_TAG = "signalMin";
    private static final String JSON_SIGNAL_MAX_TAG = "signalMax";
    private static final String JSON_BLE_ID_TAG = "ID";

    String bleID;
    int    sigMax;
    int    sigMin;

    @Override
    public void Load(JSONObject jobj) throws JSONException {
        bleID  = jobj.getString(JSON_BLE_ID_TAG);
        sigMax = jobj.optInt(JSON_SIGNAL_MAX_TAG, -10);
        sigMin = jobj.optInt(JSON_SIGNAL_MIN_TAG, -90);

    }

    @Override
    public boolean Evaluate() {
        return mTriggerStatus;
    }

}
