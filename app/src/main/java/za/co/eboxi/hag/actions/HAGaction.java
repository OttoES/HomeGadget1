package za.co.eboxi.hag.actions;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.eboxi.hag.HAGapplication;

/**
 * Created by John2 on 2015/01/02.
 */
public class HAGaction {
    private enum JSON_ACT_CLASS_NAMES
    {
        ACT_NONE,
        ACT_DEFAULT,
        ACT_BEEP,
        ACT_TOAST_MSG,
        ACT_PLAY_AUDIO,
        ACT_BLE_TOGGLE_RELAY
    }
    private static final String JSON_TAG_CLASS_NAME = "class";
    private static final String JSON_TAG_DESCRIPTION_NAME = "name";
    private static final String JSON_TAG_TOAST_MSG = "msg";

    String name;
    String msg;

    public static HAGaction Factory(JSONObject jobj)
    {
        HAGaction ret = null;
        try {
            String className = jobj.optString(JSON_TAG_CLASS_NAME);
            switch (JSON_ACT_CLASS_NAMES.valueOf(className)) {
                case ACT_DEFAULT:
                case ACT_TOAST_MSG:
                case ACT_PLAY_AUDIO:
                    ret = new HAGaction();
                    ret.Load(jobj);
                    break;


            } // switch

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // will in mot cases be over ridden to load the class details
    public void Load(JSONObject jobj) throws JSONException {
        name = jobj.optString(JSON_TAG_DESCRIPTION_NAME, "none");
        msg  = jobj.optString(JSON_TAG_TOAST_MSG,"No message defined");
    }



    //         playSound(this, getAlarmUri());


//    private MediaPlayer mMediaPlayer;
//    private void playSound(Context context, Uri alert) {
//        mMediaPlayer = new MediaPlayer();
//        try {
//            mMediaPlayer.setDataSource(context, alert);
//            final AudioManager audioManager = (AudioManager) context
//                    .getSystemService(Context.AUDIO_SERVICE);
//            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
//                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
//                mMediaPlayer.prepare();
//                mMediaPlayer.start();
//            }
//        } catch (IOException e) {
//            System.out.println("OOPS");
//        }
//    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
//    private Uri getAlarmUri() {
//        Uri alert = RingtoneManager
//                .getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alert == null) {
//            alert = RingtoneManager
//                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            if (alert == null) {
//                alert = RingtoneManager
//                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//            }
//        }
//        return alert;
//    }


    public boolean Execute() {
        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone

//            String phoneNumberReciver="9718202185";// phone number to which SMS to be send
//            String message="Hi I will be there later, See You soon";// message to send
//            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
        // Show the toast  like in above screen shot
        Context context = HAGapplication.getAppContext();
        Toast.makeText(context, "Event Triggered", Toast.LENGTH_LONG).show();
        return true;
    }

} // end class
