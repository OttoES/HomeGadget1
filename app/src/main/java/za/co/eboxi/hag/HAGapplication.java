package za.co.eboxi.hag;

import android.app.Application;
import android.content.Context;

/**
 * Created by John2 on 2015/01/06.
 */
public class HAGapplication  extends Application {

    private static Context context;
    public static HAGtask[]  mTasks;

    public void onCreate(){
        super.onCreate();
        HAGapplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return HAGapplication.context;
    }

}
