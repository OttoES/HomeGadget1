package za.co.eboxi.hag.actions;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;

import za.co.eboxi.hag.HAGapplication;

/**
 * Created by John2 on 2015/01/18.
 */
public class HAGactionPlayAudio extends HAGaction {
    private static final String JSON_TAG_FILE_NAME = "file";
    private MediaPlayer mediaPlayer;

    String mUriName ;
    @Override
    public void Load(JSONObject jobj) throws JSONException {

        mUriName = jobj.optString(JSON_TAG_FILE_NAME, "none");
    }

    @Override
    public boolean Execute(Context context)
    {
        AudioManager manager = (AudioManager) HAGapplication.getAppContext().getSystemService(Context.AUDIO_SERVICE);
        if (manager.isMusicActive()) return false;
        //if (mediaPlayer == null)
        mediaPlayer = new MediaPlayer();
        //if (mediaPlayer.isPlaying()) return true;
        //mediaPlayer.setDataSource(this, track.asUri());
        Uri myUri =Uri.parse(mUriName);
        try {
//            mediaPlayer.setDataSource(context, myUri);
//            mediaPlayer.setDataSource("/storage/emulated/0/VoiceRecorder/my_sounds/beacon2.3gp");
//            mediaPlayer.setDataSource("/storage/emulated/0/Music/Ace of Base/Happy Nation/11-Dimension.mp3");

//            FileInputStream fis = new FileInputStream("/storage/emulated/0/Music/Ace of Base/Happy Nation/11-Dimension.mp3");
//            FileDescriptor fd = null;
//            fd = fis.getFD();
//            mediaPlayer.setDataSource(fd);
//            mediaPlayer.prepare();

//            String name = "file:///storage/emulated/0/Music/Ace of Base/Happy Nation/11-Dimension.mp3";
//            Uri myUri1 = Uri.parse(name);
//                       //Uri.parse("file:///storage/emulated/0/Music/Ace of Base/Happy Nation/11-Dimension.mp3");
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setDataSource(HAGapplication.getAppContext(), myUri1);
//            mediaPlayer.prepare();

//            String name = "storage/emulated/0/Music/Ace of Base/Happy Nation/11-Dimension.mp3";
            String name = "/sdcard/Music/Ace of Base/Happy Nation/11-Dimension.mp3";
            FileInputStream fis = null;
            fis = new FileInputStream(mUriName);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.set
//        intent.setDataAndType(myUri, "audio/mp3");
//        context.startActivity(intent);
        return true;
    }

    public boolean oldExecute()
    {

 //       mUriName = "file:///storage/emulated/0/Music/Ace of Base/Happy Nation/11-Dimension.mp3";
        Uri myUri =Uri.parse(mUriName);
//        Uri myUri =Uri.parse("file:/"+selectedFile);
        //    String introURI;
//    introURI = Uri.parse("file:///sdcard/intro.3gp");

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(myUri, "audio/mp3");
        HAGapplication.getAppContext().startActivity(intent);


//        Uri intentUri = Uri.parse(URL);
//
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(intentUri, "audio/*");
//        startActivity(intent);


//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//            Uri data = Uri.parse("file:///sdcard/sample.mp3");
//            intent.setDataAndType(data,"audio/mp3");
//
//            try {
//                startActivity(intent);
//            } catch (ActivityNotFoundException e) {
//                e.printStackTrace();
//            }

        return  true;
    } // end Execute

//    public static MediaPlayer player;
//    public static void SoundPlayer(Context ctx,int raw_id){
//        player = MediaPlayer.create(ctx, raw_id);
//        player.setLooping(false); // Set looping
//        player.setVolume(100, 100);
//
//        //player.release();
//        player.start();
//    }

//
//    if (item_ext.equalsIgnoreCase(".mp3") ||
//            item_ext.equalsIgnoreCase(".m4a")||
//            item_ext.equalsIgnoreCase(".mp4")) {
//
//        if(mReturnIntent) {
//            returnIntentResults(file);
//        } else {
//            Intent i = new Intent();
//            i.setAction(android.content.Intent.ACTION_VIEW);
//            i.setDataAndType(Uri.fromFile(file), "audio/*");
//            startActivity(i);
//        }
//    }
//
//        /*photo file selected*/
//    else if(item_ext.equalsIgnoreCase(".jpeg") ||
//            item_ext.equalsIgnoreCase(".jpg")  ||
//            item_ext.equalsIgnoreCase(".png")  ||
//            item_ext.equalsIgnoreCase(".gif")  ||
//            item_ext.equalsIgnoreCase(".tiff")) {
//
//        if (file.exists()) {
//            if(mReturnIntent) {
//                returnIntentResults(file);
//
//            } else {
//                Intent picIntent = new Intent();
//                picIntent.setAction(android.content.Intent.ACTION_VIEW);
//                picIntent.setDataAndType(Uri.fromFile(file), "image/*");
//                startActivity(picIntent);
//            }
//        }
//    }
//
//        /*video file selected--add more video formats*/
//    else if(item_ext.equalsIgnoreCase(".m4v") ||
//            item_ext.equalsIgnoreCase(".3gp") ||
//            item_ext.equalsIgnoreCase(".wmv") ||
//            item_ext.equalsIgnoreCase(".mp4") ||
//            item_ext.equalsIgnoreCase(".ogg") ||
//            item_ext.equalsIgnoreCase(".wav")) {
//
//        if (file.exists()) {
//            if(mReturnIntent) {
//                returnIntentResults(file);
//
//            } else {
//                Intent movieIntent = new Intent();
//                movieIntent.setAction(android.content.Intent.ACTION_VIEW);
//                movieIntent.setDataAndType(Uri.fromFile(file), "video/*");
//                startActivity(movieIntent);
//            }
//        }
//    }
//
//        /*zip file */
//    else if(item_ext.equalsIgnoreCase(".zip")) {
//
//        if(mReturnIntent) {
//            returnIntentResults(file);
//
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            AlertDialog alert;
//            mZippedTarget = mFileMag.getCurrentDir() + "/" + item;
//            CharSequence[] option = {"Extract here", "Extract to..."};
//
//            builder.setTitle("Extract");
//            builder.setItems(option, new DialogInterface.OnClickListener() {
//
//                public void onClick(DialogInterface dialog, int which) {
//                    switch(which) {
//                        case 0:
//                            String dir = mFileMag.getCurrentDir();
//                            mHandler.unZipFile(item, dir + "/");
//                            break;
//
//                        case 1:
//                            mDetailLabel.setText("Holding " + item +
//                                    " to extract");
//                            mHoldingZip = true;
//                            break;
//                    }
//                }
//            });
//
//            alert = builder.create();
//            alert.show();
//        }
//    }
//
//        /* gzip files, this will be implemented later */
//    else if(item_ext.equalsIgnoreCase(".gzip") ||
//            item_ext.equalsIgnoreCase(".gz")) {
//
//        if(mReturnIntent) {
//            returnIntentResults(file);
//
//        } else {
//            //TODO:
//        }
//    }
//
//        /*pdf file selected*/
//    else if(item_ext.equalsIgnoreCase(".pdf")) {
//
//        if(file.exists()) {
//            if(mReturnIntent) {
//                returnIntentResults(file);
//
//            } else {
//                Intent pdfIntent = new Intent();
//                pdfIntent.setAction(android.content.Intent.ACTION_VIEW);
//                pdfIntent.setDataAndType(Uri.fromFile(file),
//                        "application/pdf");
//
//                try {
//                    startActivity(pdfIntent);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(this, "Sorry, couldn't find a pdf viewer",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//        /*Android application file*/
//    else if(item_ext.equalsIgnoreCase(".apk")){
//
//        if(file.exists()) {
//            if(mReturnIntent) {
//                returnIntentResults(file);
//
//            } else {
//                Intent apkIntent = new Intent();
//                apkIntent.setAction(android.content.Intent.ACTION_VIEW);
//                apkIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                startActivity(apkIntent);
//            }
//        }
//    }
//
//        /* HTML file */
//    else if(item_ext.equalsIgnoreCase(".html")) {
//
//        if(file.exists()) {
//            if(mReturnIntent) {
//                returnIntentResults(file);
//
//            } else {
//                Intent htmlIntent = new Intent();
//                htmlIntent.setAction(android.content.Intent.ACTION_VIEW);
//                htmlIntent.setDataAndType(Uri.fromFile(file), "text/html");
//
//                try {
//                    startActivity(htmlIntent);
//                } catch(ActivityNotFoundException e) {
//                    Toast.makeText(this, "Sorry, couldn't find a HTML viewer",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//        /* text file*/
//    else if(item_ext.equalsIgnoreCase(".txt")) {
//
//        if(file.exists()) {
//            if(mReturnIntent) {
//                returnIntentResults(file);
//
//            } else {
//                Intent txtIntent = new Intent();
//                txtIntent.setAction(android.content.Intent.ACTION_VIEW);
//                txtIntent.setDataAndType(Uri.fromFile(file), "text/plain");
//
//                try {
//                    startActivity(txtIntent);
//                } catch(ActivityNotFoundException e) {
//                    txtIntent.setType("text/*");
//                    startActivity(txtIntent);
//                }
//            }
//        }
//    }
//
//        /* generic intent */
//    else {
//        if(file.exists()) {
//            if(mReturnIntent) {
//                returnIntentResults(file);
//
//            } else {
//                Intent generic = new Intent();
//                generic.setAction(android.content.Intent.ACTION_VIEW);
//                generic.setDataAndType(Uri.fromFile(file), "text/plain");
//
//                try {
//                    startActivity(generic);
//                } catch(ActivityNotFoundException e) {
//                    Toast.makeText(this, "Sorry, couldn't find anything " +
//                                    "to open " + file.getName(),
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }

}
