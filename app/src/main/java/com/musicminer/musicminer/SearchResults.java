package com.musicminer.musicminer;

import android.view.GestureDetector;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egaebel on 7/19/14.
 */
public class SearchResults {

    //~Class Fields---------------------------------------------------------------------------------
    private List<Song> titleSearchSongs;
    private List<Song> albumSearchSongs;
    private List<Song> artistSearchSongs;

    //~Constructors---------------------------------------------------------------------------------
    public SearchResults(List<Song> titleSearchSongs,
                         List<Song> albumSearchSongs,
                         List<Song> artistSearchSongs) {

        this.titleSearchSongs = titleSearchSongs;
        this.albumSearchSongs = albumSearchSongs;
        this.artistSearchSongs = artistSearchSongs;
    }

    //~Methods--------------------------------------------------------------------------------------

    /**
     * Tells whether there are any search results to be had.
     *
     * @return true if there are any songs in here, false otherwise.
     */
    public boolean isEmpty() {

        return 0 == (albumSearchSongs.size() + artistSearchSongs.size() + titleSearchSongs.size());
    }

    /**
     * Gets the top search result.
     * Top is defined as the first result in one of the three lists, the lists are searched in this
     * order:
     * title
     * album
     * artist
     *
     * @return the first Song to appear in one of these lists.
     */
    public Song getTopResult() {

        if (titleSearchSongs.size() > 0) {

            return titleSearchSongs.get(0);
        }
        else if (albumSearchSongs.size() > 0) {

            return albumSearchSongs.get(0);
        }
        else if (artistSearchSongs.size() > 0) {

            return artistSearchSongs.get(0);
        }

        return null;
    }

    /**
     * Gets all of the search results in order:
     * title
     * album
     * artist
     *
     * @return A List<Song> holding all of the results.
     */
    public List<Song> getAllResults() {

        List<Song> allResults = new ArrayList<Song>(titleSearchSongs.size()
                                                    + albumSearchSongs.size()
                                                    + artistSearchSongs.size());
        allResults.addAll(titleSearchSongs);
        allResults.addAll(albumSearchSongs);
        allResults.addAll(artistSearchSongs);

        return allResults;
    }

    @Override
    public String toString() {

        StringBuilder returnBuild = new StringBuilder();

        if (titleSearchSongs.size() > 0) {

            //returnBuild.append(titleSearchSongs.get(0).getName() + " - " + titleSearchSongs.get(0).getArtist());
            for (Song song : titleSearchSongs) {
                returnBuild.append(song.getName()).append(" - ").append(song.getArtist()).append("\n");
            }
        }
        else if (albumSearchSongs.size() > 0) {

            //returnBuild.append(albumSearchSongs.get(0).getAlbum() + " - " + albumSearchSongs.get(0).getName());
            for (Song song : albumSearchSongs) {
                returnBuild.append(song.getAlbum()).append(" - ").append(song.getName()).append("\n");
            }
        }
        else if (artistSearchSongs.size() > 0) {

            //returnBuild.append(artistSearchSongs.get(0).getArtist() + " - " + artistSearchSongs.get(0).getName());
            for (Song song : artistSearchSongs) {
                returnBuild.append(song.getArtist()).append(" - ").append(song.getName()).append("\n");
            }
        }
        else {

            returnBuild.append("NO RESULTS FOUND!!!");
        }

        return returnBuild.toString();
    }
}