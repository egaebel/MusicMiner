package com.musicminer.musicminer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fragment used to display the music collection.
 */
public class MusicBrowserFragment extends Fragment implements View.OnClickListener {

    //~Data Fields----------------------------------------------------------------------------------
    private static final String TAG = "MUSIC_BROSWER_FRAGMENT";

    private AudioCollectionSearchInterface audioCollectionSearchInterface;
    private AudioControlInterface audioControlInterface;

    private EditText searchBar;
    private Button searchButton;
    private ListView songListView;
    private Map<String, Song> songSearchMap;

    //~Constructors---------------------------------------------------------------------------------
    public MusicBrowserFragment() {
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
        View layout = inflater.inflate(R.layout.fragment_music_browser, container, false);

        searchBar = (EditText) layout.findViewById(R.id.music_browser_search_bar_edit_text);
        searchButton = (Button) layout.findViewById(R.id.music_browser_search_button);
        searchButton.setOnClickListener(this);
        songListView = (ListView) layout.findViewById(R.id.music_browser_list_view);

        System.out.println("ListView Context: " + songListView.getContext());

        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            audioCollectionSearchInterface = (AudioCollectionSearchInterface) activity;
            audioControlInterface = (AudioControlInterface) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        audioCollectionSearchInterface = null;
    }

    //~Methods--------------------------------------------------------------------------------------
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MusicBrowserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicBrowserFragment newInstance() {
        MusicBrowserFragment fragment = new MusicBrowserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {

        if (v.equals(searchButton)) {

            Log.i(TAG, "SEARCH BUTTON CLICKED");
            SearchResults searchResults = audioCollectionSearchInterface.onSearchPressed(searchBar.getText().toString());
            Log.i(TAG, "Search Results:\n" + searchResults.toString());

            ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this.getActivity(),
                    R.layout.music_browser_list_view_child,
                    R.id.list_view_child_text_field,
                    searchResults.getAllResults());

            songListView.setAdapter(adapter);

            //Add all songs to the HashMap
            this.songSearchMap = new HashMap<String, Song>();
            for (Song song : searchResults.getAllResults()) {

                songSearchMap.put(song.getName()
                                    + song.getArtist()
                                    + song.getAlbum(), song);
            }
        }
    }

    /**
     * Interface to expose various methods related
     * to click actions of the childView of the ListView contained in this
     * Fragment to the enclosing Activity.
     */
    public interface MusicBrowserListViewChildClick {

        public void onMusicBrowserChildViewPlayClicked(View v);
    }

    /**
     * Starts playing the song specified in the item whose play button was clicked.
     *
     * @param v the view that was clicked.
     */
    public void onChildViewPlayClicked(View v) {

        View listViewEntry = (View) v.getParent();
        TextView listViewText = (TextView) listViewEntry.findViewById(R.id.list_view_child_text_field);
        String childStr = listViewText.getText().toString();
        String nameTag = "Name: ";
        String artistTag = "Artist: ";
        String albumTag = "Album: ";
        int nameTagIndex = childStr.indexOf(nameTag);
        int artistTagIndex = childStr.indexOf(artistTag);
        int albumTagIndex = childStr.indexOf(albumTag);
        String name = childStr.substring((nameTagIndex + nameTag.length()), artistTagIndex);
        String artist = childStr.substring((artistTagIndex + artistTag.length()), albumTagIndex);
        String album = childStr.substring((albumTagIndex + albumTag.length()));
        String hashKey = name + artist + album;
        hashKey = hashKey.replace("\n", "");
        Log.i(TAG, "" + songSearchMap.keySet());
        Log.i(TAG, "" + hashKey);
        audioCollectionSearchInterface.onSearchPlayPressed(songSearchMap.get(hashKey));
    }
}