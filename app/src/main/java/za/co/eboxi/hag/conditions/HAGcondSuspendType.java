package za.co.eboxi.hag.conditions;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import de.greenrobot.event.EventBus;
import za.co.eboxi.hag.HAGapplication;

/**
 * Created by John2 on 2015/01/02.
 */
public class HAGcondSuspendType extends HAGcondition {
    //public static final String JSON_CLASS_NAME = "HAGcondSus pendType";
    private static final String JSON_START_HOUR_TAG = "startHour";
    private static final String JSON_START_MIN_TAG = "startMinute";
    private static final String JSON_END_HOUR_TAG = "endHour";
    private static final String JSON_END_MIN_TAG = "endMinute";

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    //private boolean isInTimeLimits = false;


    @Override
    public void Load(JSONObject jobj) throws JSONException {
        startHour = jobj.optInt(JSON_START_HOUR_TAG, 0);
        endHour = jobj.optInt(JSON_END_HOUR_TAG, 0);
        startMinute = jobj.optInt(JSON_START_MIN_TAG, 0);
        endMinute = jobj.optInt(JSON_END_MIN_TAG, 0);
        scheduleAlarm();

    }

    @Override
    public boolean Evaluate()
    {
        // check if the current time is within the limits
        Calendar cal = Calendar.getInstance();
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        if ((h<startHour) ) return false;
        if ((h==startHour) && (m<startMinute)) return false;
        if ((h==endHour) && (m>endMinute)) return false;
        if ((h>endHour) ) return false;
        return true;
    }

    private PendingIntent alarmIntent;


    public void scheduleAlarm()
    {

        Context context = HAGapplication.getAppContext();
        //Create an offset from the current time in which the alarm will go off.
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 5);
//
//        //Create a new PendingIntent and add it to the AlarmManager
//        Intent intent = new Intent(this, AlarmReceiverActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager am =
//                (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
//                pendingIntent);

// Set the alarm to start at approximately 2:00 p.m.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 14);


        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
        //Long time = new GregorianCalendar().getTimeInMillis()+24*60*60*1000;
        // 20 sec from now
        //Long time = new GregorianCalendar().getTimeInMillis()+20*1000;

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(context, AlarmReceiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
       // alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

        Toast.makeText(context, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
        alarmIntent = PendingIntent.getBroadcast(context, 0, intentAlarm, 0);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +   5 * 1000, alarmIntent);

    }

    public static  class AlarmReceiver extends BroadcastReceiver
    {
        //public AlarmReceiver()     {  }

        @Override
        public void onReceive(Context context, Intent intent)
        {

            // here you can start an activity or service depending on your need
            // for ex you can start an activity to vibrate phone or to ring the phone

//            String phoneNumberReciver="9718202185";// phone number to which SMS to be send
//            String message="Hi I will be there later, See You soon";// message to send
//            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
            // Show the toast  like in above screen shot
            //Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
            EventBus.getDefault().post(new HAGeventMsg());
            //mParentTask.ReevaluateConditions();

        }

    }

}
