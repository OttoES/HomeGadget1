package za.co.eboxi.hag.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John2 on 2015/01/18.
 */
public class HAGactionMsgDialog extends HAGaction
{
    private static final String JSON_TAG_DIALOG_MESSAGE = "msg";

    String msg = "Message";
    @Override
    public void Load(JSONObject jobj) throws JSONException {
        msg = jobj.optString(JSON_TAG_DIALOG_MESSAGE, ".. message ..");
    }

    @Override
    public boolean Execute(Context context)
    {
       return true;
    }

}
