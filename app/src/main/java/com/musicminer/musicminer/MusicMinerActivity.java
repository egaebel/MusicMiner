package com.musicminer.musicminer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.List;

public class MusicMinerActivity extends Activity
        implements AudioControlInterface,
                    AudioCollectionSearchInterface,
                    MusicBrowserFragment.MusicBrowserListViewChildClick {

    //~Class Fields---------------------------------------------------------------------------------
    private static final String TAG = "MUSIC_MINER_ACTIVITY";

    //Functional Fields
    private AudioMaster audioMaster;
    private MusicPlayer musicPlayer;
    private Song currentSong;
    private MusicPlayerFragment musicPlayerFragment;
    private MusicBrowserFragment musicBrowserFragment;

    //GUI Fields
    private ViewPager pager;
    private FragmentAdapter fragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Before set content view....");
        setContentView(R.layout.activity_music_miner);

        /*
        if (savedInstanceState == null) {
            musicPlayerFragment = new MusicPlayerFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, musicPlayerFragment)
                    .commit();
        }
        else {
            //TODO: Stuff and stuff
        }
        */

        fragAdapter = new FragmentAdapter(this.getFragmentManager());
        Log.i(TAG, "after frag adapter");

        musicBrowserFragment = new MusicBrowserFragment();
        musicPlayerFragment = new MusicPlayerFragment();

        fragAdapter.addFragment(musicBrowserFragment);
        fragAdapter.addFragment(musicPlayerFragment);

        pager = (ViewPager) this.findViewById(R.id.activity_view_pager);

        Log.i(TAG, "after view pager");
        pager.setAdapter(fragAdapter);
        Log.i(TAG, "after set adapter");


        audioMaster = new AudioMaster();
        musicPlayer = new MusicPlayer(this.getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_miner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVolumeLowered() {

    }

    @Override
    public void onVolumeRaised() {

    }

    @Override
    public void onVolumeMuted() {

    }

    @Override
    public void onPrevButtonPressed() {

    }

    @Override
    public void onPlayButtonPressed() {

        musicPlayer.togglePlayPause();
    }

    @Override
    public void onNextButtonPressed() {

    }

    @Override
    public SearchResults onSearchPressed(String searchText) {

        SearchResults searchResults = audioMaster.search(this.getApplicationContext(), searchText);
        return searchResults;
    }

    @Override
    public void onSearchPlayPressed(Song song) {

        try {
            musicPlayer.playSong(this, song.getUri());
            musicPlayerFragment.setAlbumArt(song.getAlbumArt(this));
        }
        catch (IOException e) {
            Log.i(TAG, "IOException in onSearchPlayPressed");
            e.printStackTrace();
        }
    }

    @Override
    public void onMusicBrowserChildViewPlayClicked(View v) {

        musicBrowserFragment.onChildViewPlayClicked(v);
    }
}