package com.musicminer.musicminer;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Main way point of the program.
 * All audio interaction has to come through here.
 * Has modules to search for files, retrieve files, and runs the main algorithm
 * for relationship mining.
 *
 * Created by egaebel on 7/12/14.
 */
public class AudioMaster {

    //~Class Fields---------------------------------------------------------------------------------
    private static final String TAG = "AUDIO_MASTER";



    //~Constructors---------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public AudioMaster() {


    }

    //~Methods--------------------------------------------------------------------------------------
    /**
     * Runs a query on both the internal and external ContentResolvers.
     * For the passed in args.
     *
     * @param context the application context.
     * @param columns the columns to request from the database.
     * @param selectionClause the where statement of the query.
     * @param selectionArgs the values to place in the where statement.
     * @return a List<Song> of the songs matching the query.
     */
    private List<Song> internalExternalQuery(Context context,
                                               String[] columns,
                                               String selectionClause,
                                               String[] selectionArgs) {

        Log.i(TAG, "Query: SELECT " + columns + " FROM table WHERE " + selectionClause + selectionArgs);

        List<Song> songs = new LinkedList<Song>();

        Cursor externalCursor = context.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        columns,
                        selectionClause,
                        selectionArgs,
                        null);

        // External music library
        if (externalCursor != null && externalCursor.getCount() > 0) {

            if (externalCursor.isBeforeFirst()) {
                externalCursor.moveToNext();
            }

            Song song;
            String id;
            String title;
            String artist;
            String album;
            String songUri;
            String albumId;
            while (!externalCursor.isAfterLast()) {

                id = externalCursor.getString(0);
                title = externalCursor.getString(1);
                artist = externalCursor.getString(2);
                album = externalCursor.getString(3);
                songUri = externalCursor.getString(4);
                albumId = externalCursor.getString(5);
                song = new Song(title, artist, album, Uri.parse(songUri), albumId);
                songs.add(song);
                externalCursor.moveToNext();
            }

            externalCursor.close();
        }
        else {

            Log.i(TAG, "Well....my external cursor is useless...");
        }

        Cursor internalCursor = context.getContentResolver()
                .query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                        columns,
                        selectionClause,
                        selectionArgs,
                        null);

        // Internal music library
        if (internalCursor != null && internalCursor.getCount() > 0) {

            if (internalCursor.isBeforeFirst()) {
                internalCursor.moveToNext();
            }

            Song song;
            String id;
            String title;
            String artist;
            String album;
            String songUri;
            String albumId;
            while (!internalCursor.isAfterLast()) {

                id = internalCursor.getString(0);
                title = internalCursor.getString(1);
                artist = internalCursor.getString(2);
                album = internalCursor.getString(3);
                songUri = internalCursor.getString(4);
                albumId = internalCursor.getString(5);
                song = new Song(title, artist, album, Uri.parse(songUri), albumId);
                songs.add(song);
                internalCursor.moveToNext();
            }

            internalCursor.close();
        }
        else {

            Log.i(TAG, "Well....my internal cursor is useless...");
        }

        return songs;
    }

    /**
     * Search the database using the given search text.
     * Search encompasses songs, albums, and artists, in that order of precedence.
     *
     * @param context the application context.
     * @param searchText the text to search for.
     * @return a list of songs (List<Song>) that match the search.
     */
    public SearchResults search(Context context, String searchText) {

        List<Song> songs = new LinkedList<Song>();

        String[] columns = new String[]{MediaStore.MediaColumns._ID,
                                        MediaStore.Audio.Media.TITLE,
                                        MediaStore.Audio.Artists.ARTIST,
                                        MediaStore.Audio.Albums.ALBUM,
                                        MediaStore.Audio.Media.DATA,
                                        MediaStore.Audio.Albums.ALBUM_ID};

        // Clauses for title album and artist
        String titleSelectionClause = MediaStore.Audio.Media.TITLE + " LIKE ?";//" = ?";
        String albumSelectionClause = MediaStore.Audio.Albums.ALBUM + " LIKE ?";//" = ?";
        String artistSelectionClause = MediaStore.Audio.Artists.ARTIST + " LIKE ?";//" = ?";

        // Selection arg
        String[] selectionArg = {searchText};

        // Run queries
        List<Song> titleSearch = internalExternalQuery(context, columns, titleSelectionClause, selectionArg);
        List<Song> albumSearch = internalExternalQuery(context, columns, albumSelectionClause, selectionArg);
        List<Song> artistSearch = internalExternalQuery(context, columns, artistSelectionClause, selectionArg);

        return new SearchResults(titleSearch, albumSearch, artistSearch);
    }

    /**
     * Uses a ContentResolver to retrieve all of the music on the device.
     *
     * @param context the application context.
     * @return List<Song> of all the songs on the user's device (internal and external).
     */
    public List<Song> getAllMusic(Context context) {

        String[] columns = new String[]{MediaStore.MediaColumns._ID,
                                        MediaStore.Audio.Media.TITLE,
                                        MediaStore.Audio.Artists.ARTIST,
                                        MediaStore.Audio.Albums.ALBUM,
                                        MediaStore.Audio.Media.DATA,
                                        MediaStore.Audio.Media.ALBUM_ID};

        return internalExternalQuery(context, columns, null, null);
    }
}
