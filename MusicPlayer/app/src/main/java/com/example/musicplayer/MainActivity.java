package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private static final int PICK_AUDIO_REQUEST = 1;
    private MediaPlayer mediaPlayer;
    private List<Uri> audioList = new ArrayList<>();
    private int currentAudioIndex = 0;
    private boolean isButtonLongPressed = false;
    private static final long LONG_PRESS_DURATION = 1000;
    private boolean isPlaying = false;  // playback status
    private int pausedTime = 0;
    private SeekBar seekBar;
    private TextView songNameTextView;
    private TextView timerTextView;
    private ImageView albumImageView;
    private Handler seekBarHandler;

    private static final int SEEK_BAR_UPDATE_INTERVAL = 1000;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton playButton = findViewById(R.id.imageButton);
        ImageButton nextButton = findViewById(R.id.imageButton2);
        ImageButton previousButton = findViewById(R.id.imageButton3);
        seekBar = findViewById(R.id.seekBar2);
        songNameTextView = findViewById(R.id.songNameTextView);
        timerTextView = findViewById(R.id.textView2);
        albumImageView = findViewById(R.id.albumImageView);
        View mainLayout = findViewById(R.id.main_view);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                pickAudio();
                playButton.setImageResource(android.R.drawable.ic_media_pause);
            }

            @Override
            public void onSwipeRight() {
                pickAudio();
                playButton.setImageResource(android.R.drawable.ic_media_pause);

            }
        });

        seekBarHandler = new Handler();
        playButton.setOnTouchListener(new View.OnTouchListener() {
            private Runnable runnable;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isButtonLongPressed = false;
                        seekBarHandler.postDelayed(runnable = new Runnable() {
                            @Override
                            public void run() {
                                isButtonLongPressed = true;
                                pickAudio();
                                playButton.setImageResource(android.R.drawable.ic_media_pause);
                            }
                        }, LONG_PRESS_DURATION);
                        break;
                    case MotionEvent.ACTION_UP:
                        seekBarHandler.removeCallbacks(runnable);
                        if (!isButtonLongPressed) {
                            if (mediaPlayer != null) {
                                if (isPlaying) {
                                    pausedTime = mediaPlayer.getCurrentPosition();
                                    mediaPlayer.pause();
                                    playButton.setImageResource(android.R.drawable.ic_media_play);
                                    isPlaying = false;
                                } else {
                                    playButton.setImageResource(android.R.drawable.ic_media_pause);
                                    if (audioList.isEmpty() || currentAudioIndex >= audioList.size()) {
                                        pickAudio();
                                    } else {
                                        playSelectedAudio(audioList.get(currentAudioIndex));
                                    }
                                }
                            }
                        }
                        break;
                }
                return true;
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNextAudio();
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPreviousAudio();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    int newPosition = (int) ((float) progress / 100 * mediaPlayer.getDuration());
                    mediaPlayer.seekTo(newPosition);
                    updateSeekBar();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        startSeekBarUpdate();
    }
    private void startSeekBarUpdate() {
        Runnable updateSeekBar = new Runnable() {
            @Override
            public void run() {
                updateSeekBar();
                seekBarHandler.postDelayed(this, SEEK_BAR_UPDATE_INTERVAL);
            }
        };
        seekBarHandler.postDelayed(updateSeekBar, SEEK_BAR_UPDATE_INTERVAL);
    }
    private void pickAudio() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_AUDIO_REQUEST);
    }

    private String getSongNameFromUri(Uri audioUri) {
        Cursor cursor = getContentResolver().query(audioUri, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        String songName = cursor.getString(nameIndex);
        cursor.close();
        return songName;
    }

    private void playSelectedAudio(Uri audioUri) {
        releaseMediaPlayer();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, audioUri);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isPlaying = true;
                    mediaPlayer.seekTo(pausedTime);
                    mediaPlayer.start();
                    String songName = getSongNameFromUri(audioUri);
                    if (songName.length() > 30) {
                        songName = songName.substring(0, 30) + "";
                    }
                    songNameTextView.setText(songName);
                    try {
                        displayAlbumArt(audioUri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    updateSeekBar();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playNextAudio();
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "خطا هنگام پخش موسیقی!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayAlbumArt(Uri audioUri) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, audioUri);
        byte[] albumArt = retriever.getEmbeddedPicture();
        if (albumArt != null) {
            Glide.with(this)
                    .load(albumArt)
                    .apply(RequestOptions.circleCropTransform())
                    .into(albumImageView);
        } else {
            Glide.with(this)
                    .load(R.drawable.default_album_art)
                    .apply(RequestOptions.circleCropTransform())
                    .into(albumImageView);
        }
        retriever.release();
    }

    private void playNextAudio() {
        if (audioList.isEmpty()) {
            return;
        }

        currentAudioIndex = (currentAudioIndex + 1) % audioList.size();
        Uri nextAudioUri = audioList.get(currentAudioIndex);
        playSelectedAudio(nextAudioUri);
    }

    private void playPreviousAudio() {
        if (audioList.isEmpty()) {
            return;
        }
        currentAudioIndex = (currentAudioIndex - 1 + audioList.size()) % audioList.size();
        Uri previousAudioUri = audioList.get(currentAudioIndex);
        playSelectedAudio(previousAudioUri);
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int totalDuration = mediaPlayer.getDuration();
            int progress = (int) (((float) currentPosition / totalDuration) * 100);
            seekBar.setProgress(progress);
            String elapsedTime = millisecondsToTime(currentPosition);
            timerTextView.setText(elapsedTime);
        }
    }
    private String millisecondsToTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AUDIO_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                audioList.clear();
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri selectedAudioUri = data.getClipData().getItemAt(i).getUri();
                        audioList.add(selectedAudioUri);
                    }
                } else if (data.getData() != null) {
                    Uri selectedAudioUri = data.getData();
                    audioList.add(selectedAudioUri);
                }
                if (!audioList.isEmpty()) {
                    playSelectedAudio(audioList.get(0));
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        seekBarHandler.removeCallbacksAndMessages(null);
    }
}
