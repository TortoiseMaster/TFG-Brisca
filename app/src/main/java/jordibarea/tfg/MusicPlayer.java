package jordibarea.tfg;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import jordibarea.tfg.R;

public class MusicPlayer extends Service {

    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate()
    {
        mp = MediaPlayer.create(getApplicationContext(), R.raw.backgorund_music);
        mp.setLooping(true);
    }
    public void onDestroy()
    {
        mp.stop();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        mp.setVolume(0.5f,0.5f);
        return Service.START_NOT_STICKY;
    }

    public void buttonClick(){
        mp = MediaPlayer.create(this, R.raw.button_sound);
        mp.setLooping(false);
    }

    public void cardSound(){
        mp = MediaPlayer.create(this, R.raw.card_move);
        mp.setLooping(false);
    }
}