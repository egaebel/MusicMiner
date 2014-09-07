package com.musicminer.musicminer;

/**
 * Interface for fragments to communicate with the activity for search results.
 *
 * Created by egaebel on 7/27/14.
 */
public interface AudioCollectionSearchInterface {
    public SearchResults onSearchPressed(String searchText);
    public void onSearchPlayPressed(Song song);
}