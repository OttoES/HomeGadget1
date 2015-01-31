package za.co.eboxi.hag.actions;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John2 on 2015/01/24.
 */
public class HAGactionBeep extends HAGaction {
    private static final String JSON_TAG_FREQ_Hz = "freq";
    private static final String JSON_TAG_TIME_ms = "time_ms";
    private int mBeepFreq_Hz = 200;
    private int mBeepTime_ms = 700;

    @Override
    public void Load(JSONObject jobj) throws JSONException {
        mBeepFreq_Hz = jobj.optInt(JSON_TAG_FREQ_Hz, 1000);
        mBeepTime_ms  = jobj.optInt(JSON_TAG_TIME_ms, 600);
    }
    @Override
    public boolean Execute(Context context)
    {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        return true;
    }

}
