package za.co.eboxi.hag;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import za.co.eboxi.hag.actions.HAGaction;
import za.co.eboxi.hag.conditions.HAGcondition;

/**
 * Created by John2 on 2015/01/02.
 */
public class HAGtask {
    private static final String JSON_CLASS_TAG = "class";
    private static final String JSON_TASKS_ARR_TAG = "tasks";
    private static final String JSON_CONDS_ARR_TAG = "conditions";
    private static final String JSON_ACTIONS_ARR_TAG = "actions";
    private static final String JSON_NAME_TAG = "name";
    private static final String JSON_CLASS_Name = "HAGtask";

    private String name;
    HAGcondition mConditions[];
    HAGaction mActions[];


    private static String readStringFromAsset(AssetManager mngr,String fname) {
        String json = null;
        try {
            InputStream is = mngr.open(fname);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    } // end

    public static HAGtask[] ReadTasksFromJSONfile(AssetManager mngr,String fname) {

        String jsonstr = readStringFromAsset(mngr, fname);
        if (jsonstr == null) return null;
        try {
            JSONObject obj = new JSONObject(jsonstr);
            JSONArray jTaskArray = obj.getJSONArray(JSON_TASKS_ARR_TAG);
            HAGtask[] arr = new HAGtask[jTaskArray.length()];
            for (int i = 0; i < jTaskArray.length(); i++)    {
                    JSONObject jsonTaskObj = jTaskArray.getJSONObject(i);
                    //Log.d("Details-->",jo_inside.getString("formule"));
                    arr[i] = new HAGtask();
                    arr[i].ReadTaskFromJSON(jsonTaskObj);
            } // for i
            return arr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    } // ReadTasksFromJSONfile()

    private void ReadTaskFromJSON(JSONObject jsonTaskObj) {

        try {
            //JSONObject obj = new JSONObject(jsonTaskObj);
            String className = jsonTaskObj.getString(JSON_CLASS_TAG);
//            if (className.equalsIgnoreCase(JSON_CLASS_Name) == false ) {
//                Log.e("", "Failed to load JSON task");
//                return;  // should not happen !!
//            }
            // read the conditions
            JSONArray jCondArray = jsonTaskObj.getJSONArray(JSON_CONDS_ARR_TAG);
            mConditions = new HAGcondition[jCondArray.length()];
            for (int i = 0; i < jCondArray.length(); i++)    {
                JSONObject jsonCondObj = jCondArray.getJSONObject(i);
                mConditions[i] = HAGcondition.Factory(this,jsonCondObj);
            } // for i

            // read the actions
            JSONArray jActArray = jsonTaskObj.getJSONArray(JSON_ACTIONS_ARR_TAG);
            mActions = new HAGaction[jActArray.length()];
            for (int i = 0; i < jActArray.length(); i++)    {
                JSONObject jsonActObj = jActArray.getJSONObject(i);
                mActions[i] = HAGaction.Factory(jsonActObj);
            } // for i


            //String name = obj.getString(JSON_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    } // ReadTaskFromJSON()

    public void Load(JSONObject jobj)
    {

    }

    public void ReevaluateConditions()
    {
        for (int i = 0; i < mConditions.length; i++)    {
            if (mConditions[i].Evaluate() == false ) return;
        } // for i
        // all conditions must be true so execute all tasks
        // by default a task-action should return true, if it returns false all other
        // task-actions should not be executed.
        for (int i = 0; i < mActions.length; i++)    {
            if (mActions[i].Execute() == false) return;
        } // for i
    } // ReevaluateConditions()

} // end class
