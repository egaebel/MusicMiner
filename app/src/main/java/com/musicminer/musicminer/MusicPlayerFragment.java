package com.musicminer.musicminer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Currency;
import java.util.List;

/**
 * Fragment used for user interaction with the music player.
 *
 */
public class MusicPlayerFragment extends Fragment implements View.OnClickListener {

    //~Class Fields---------------------------------------------------------------------------------
    private static final String TAG = "MUSIC_PLAYER_FRAGMENT";

    /**
     * Music Control Buttons
     */
    private Button playPauseButton;
    private Button prevButton;
    private Button nextButton;
    private ImageView albumArtView;

    /**
     * Interface with the owner of this fragment.
     */
    private AudioControlInterface audioControlListener;
    private AudioCollectionSearchInterface searchListener;

    private Song searchResult = null;

    //~Constructors---------------------------------------------------------------------------------
    public MusicPlayerFragment() {
        // Required empty public constructor
    }

    //~Lifecycle Methods----------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_music_player, container, false);

        //Views
        albumArtView = (ImageView) layout.findViewById(R.id.album_art_view);

        //Buttons
        prevButton = (Button) layout.findViewById(R.id.prev_button);
        prevButton.setOnClickListener(this);
        playPauseButton = (Button) layout.findViewById(R.id.play_pause_button);
        playPauseButton.setOnClickListener(this);
        nextButton = (Button) layout.findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            audioControlListener = (AudioControlInterface) activity;
            searchListener = (AudioCollectionSearchInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AudioControlInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        audioControlListener = null;
        searchListener = null;
    }

    //~Methods--------------------------------------------------------------------------------------
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MusicPlayerFragment.
     */
    public static MusicPlayerFragment newInstance() {

        MusicPlayerFragment fragment = new MusicPlayerFragment();
        return fragment;
    }

    public void setAlbumArt(Bitmap albumArtwork) {

        albumArtView.setImageBitmap(albumArtwork);
    }

    // TODO: FIX THIS UGLINESS!
    Song currentSong;
    @Override
    public void onClick(View v) {

        if (v.equals(playPauseButton)) {

            audioControlListener.onPlayButtonPressed();
        }
        else if (v.equals(prevButton)) {

            audioControlListener.onPrevButtonPressed();
        }
        else if (v.equals(nextButton)) {

            audioControlListener.onNextButtonPressed();
        }
    }
}