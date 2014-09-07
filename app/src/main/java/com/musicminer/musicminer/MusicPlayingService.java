package com.musicminer.musicminer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicPlayingService extends Service {
    public MusicPlayingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
