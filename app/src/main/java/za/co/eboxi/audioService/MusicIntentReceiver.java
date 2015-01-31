package za.co.eboxi.audioService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
/**
 * Receives broadcasted intents. In particular, we are interested in the
 * android.media.AUDIO_BECOMING_NOISY intent, which is broadcast, for example, when the user
 * disconnects the headphones. This class works because we are declaring it in a &lt;receiver&gt;
 * tag in AndroidManifest.xml.
 */
public class MusicIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            Toast.makeText(ctx, "Headphones disconnected.", Toast.LENGTH_SHORT).show();
// send an intent to our MusicService to telling it to pause the audio
            ctx.startService(new Intent(HAGaudioPlayer.ACTION_PAUSE));
        }
    }
}