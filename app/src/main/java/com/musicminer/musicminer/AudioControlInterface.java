package com.musicminer.musicminer;

/**
 * Provides methods to control audio playback.
 */
public interface AudioControlInterface {

    public void onVolumeLowered();
    public void onVolumeRaised();
    public void onVolumeMuted();
    public void onPrevButtonPressed();
    public void onPlayButtonPressed();
    public void onNextButtonPressed();
}