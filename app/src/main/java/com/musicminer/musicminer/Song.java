package com.musicminer.musicminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by egaebel on 7/17/14.
 */
public class Song {

    private static final String TAG = "SONG";

    //~Class Fields-----------------------------------------------------------------------------
    private String artist;
    private String name;
    private String album;
    private String albumId;
    private Uri uri;

    public Song(String name, String artist, String album, Uri uri, String albumId) {

        this.name = name;
        this.artist = artist;
        this.album = album;
        this.uri = uri;
        this.albumId = albumId;
    }

    @Override
    public String toString() {

        StringBuilder build = new StringBuilder();

        build.append("Name: ").append(name).append("\n");
        build.append("Artist: ").append(artist).append("\n");
        build.append("Album: ").append(album).append("\n");

        return build.toString();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    /**
     * Gets the Bitmap for the album art for this song's album.
     *
     * @param context the application context, needed for ContentResolver.
     * @return the Bitmap for the album art, or null if it was not found.
     */
    public Bitmap getAlbumArt(Context context) {

        try {
            InputStream is = context.getContentResolver().openInputStream(Uri.parse("content://media/external/audio/albumart/" + albumId));
            return BitmapFactory.decodeStream(is);
        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
            Log.wtf(TAG, "COULDN'T FIND ALBUM ART URI!");
            return null;
        }
    }
}