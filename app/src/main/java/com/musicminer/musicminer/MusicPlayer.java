package com.musicminer.musicminer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by egaebel on 7/17/14.
 */
public class MusicPlayer {

    private static final String TAG = "MUSIC PLAYER";

    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;

    public MusicPlayer (Context context) {

        mediaPlayer = new MediaPlayer();
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void playSong(Context context, Uri uri) throws IOException {

        //Clear out remnants of any previous songs (if there are any)
        mediaPlayer.reset();

        mediaPlayer.setDataSource(context, uri);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
    }

    /**
     * Toggles the state of the song between playing or paused.
     * If the MediaPlayer is in some other state then start randomly playing.
     */
    public void togglePlayPause() {

        Log.i(TAG, "Toggle play pause...");
        if (mediaPlayer.isPlaying()) {

            mediaPlayer.pause();
        }
        else {

            mediaPlayer.start();
        }
    }
}