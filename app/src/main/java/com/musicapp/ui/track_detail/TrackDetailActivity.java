package com.musicapp.ui.track_detail;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.musicapp.R;
import com.musicapp.databinding.ActivityTrackDetailBinding;
import com.musicapp.model.Track;

import java.io.IOException;

public class TrackDetailActivity extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, View.OnTouchListener {

    private ActivityTrackDetailBinding binding;
    private MediaPlayer mediaPlayer;
    private int mediaFileTotalLengthInMilliseconds = 0;
    private float mediaFileLengthInMilliseconds;
    private Track track;
    private Runnable notification;
    private final Handler handler = new Handler();
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_track_detail);
        track = getIntent().getParcelableExtra("data");
        binding.setData(track);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
                mediaFileTotalLengthInMilliseconds = mediaPlayer.getDuration();
                binding.tvSongTime.setText("0:0/"+getTime(mediaFileTotalLengthInMilliseconds));
            }
        });

        setPlayButtons();
        setClicksAndListeners();
        prepareSong();

    }

    private void setClicksAndListeners() {
        binding.seekBar.setOnTouchListener(this);

        binding.ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){
                    isPlaying = false;
                    stopMusic();
                }else {
                    isPlaying = true;
                    mediaPlayer.start();
                    primarySeekBarProgressUpdater();

                }
                setPlayButtons();
            }
        });
    }

    private void stopMusic() {
        mediaPlayer.pause();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        binding.seekBar.setSecondaryProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        isPlaying = false;
        handler.removeCallbacks(notification);
        setPlayButtons();
        binding.seekBar.setProgress(0);
        binding.tvSongTime.setText("0:0/"+getTime(mediaFileTotalLengthInMilliseconds));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.seek_bar){
            if(mediaPlayer.isPlaying()){
                SeekBar sb = (SeekBar)view;
                int playPositionInMilliSeconds = (int) ((mediaFileLengthInMilliseconds / 100) * sb.getProgress());
                mediaPlayer.seekTo(playPositionInMilliSeconds);
            }else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }

    private void primarySeekBarProgressUpdater() {
        binding.seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds)*100));
        String time = getTime(mediaPlayer.getCurrentPosition()) + "/" + getTime(mediaFileTotalLengthInMilliseconds);
        binding.tvSongTime.setText(time);
        if (mediaPlayer.isPlaying()) {
            notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }
    }

    private void setPlayButtons() {
        if (isPlaying)
            binding.ivPlayPause.setBackground(getResources().getDrawable(R.drawable.ic_pause));
        else
            binding.ivPlayPause.setBackground(getResources().getDrawable(R.drawable.ic_play));
    }

    private void prepareSong() {

        try {
            mediaPlayer.setDataSource(track.getPreviewUrl());
            mediaPlayer.prepareAsync();
            primarySeekBarProgressUpdater();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTime(float time){
        int minutes = (int) ((time / 1000)  / 60);
        int seconds = (int) ((time / 1000) % 60);
        return minutes+":"+seconds;
    }
}
