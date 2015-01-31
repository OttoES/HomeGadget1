package za.co.eboxi.hag.conditions;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.eboxi.hag.HAGtask;

/**
 * Created by John2 on 2015/01/02.
 */
public abstract class HAGcondition {

    // this indicates id the condition evaluates to true or false.
    // In most cases this can be used to save the time of re-evaluating the condition.
    protected boolean mTriggerStatus = false;

    private enum JSON_CLASS_NAMES
    {
        COND_SUSPEND_TYPE,  COND_DAILY_TIME_WINDOW, COND_EVENT_BLE, COND_EVENT_BLE_RANGE;
    }
    private static final String JSON_TAG_CLASS_NAME = "class";

    protected HAGtask mParentTask;

    public static HAGcondition Factory(HAGtask task,JSONObject jobj)
    {
        HAGcondition ret = null;
        String className = null;
        try {
            className = jobj.getString(JSON_TAG_CLASS_NAME);
            switch (JSON_CLASS_NAMES.valueOf(className)) {
                case COND_DAILY_TIME_WINDOW:
                    ret = new HAGcondSuspendType();
                    ret.Load(jobj);
                    break;
                case COND_EVENT_BLE:
                    ret = new HAGcondEventTriggered();
                    ret.Load(jobj);
                    break;
                case COND_EVENT_BLE_RANGE:
                    ret = new HAGcondBLErange();
                    ret.Load(jobj);
                    break;

            } // switch
            if (ret != null) ret.mParentTask = task;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public abstract void Load(JSONObject jobj) throws JSONException;

    public void Save(JSONObject jobj)
    {

    }

    /**
     * This function can be called at any time by the HAGTaskService to check if the
     * condition is met. In practice the function should only be called on state
     * changes of any of the conditions linked to a   HAGtask.
     * @return true if condition is true
     */
    //
    public abstract boolean Evaluate();



} // end class
