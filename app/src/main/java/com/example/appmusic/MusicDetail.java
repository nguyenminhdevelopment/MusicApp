package com.example.appmusic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicDetail extends AppCompatActivity {

    List<Music> musicList = new ArrayList<>();
    int position;
    @BindView(R.id.tv_Title)
    TextView tvTitle;
    @BindView(R.id.imgHinh)
    ImageView imgHinh;
    @BindView(R.id.img_prev)
    AppCompatImageView imgPrev;
    @BindView(R.id.img_play)
    AppCompatImageView imgPlay;
    @BindView(R.id.img_next)
    AppCompatImageView imgNext;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvTimeTotal)
    TextView tvTimeTotal;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitty_detail);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        position = getIntent().getIntExtra("position", 0);
        musicList = getIntent().getParcelableArrayListExtra("lstData");
        assert musicList != null;
        tvTitle.setText(musicList.get(position).getName());
        imgHinh.setImageResource(musicList.get(position).getImage());
        mediaPlayer = MediaPlayer.
                create(MusicDetail.this,musicList.get(position).getUriMusic());
        mediaPlayer.start();
        SettimeTotal();
        UpdateTimeSong();
        Log.d("AAAAAAAAAAA", musicList.size() + "");


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // seekTo: nhay đến đoạn mình kéo
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });
    }

    @OnClick({R.id.img_prev, R.id.img_play, R.id.img_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_prev:
                position--;
                if (position < 0){
                    position = musicList.size()-1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoiTaoMediaPlayer();
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.pause);
                SettimeTotal();
                break;
            case R.id.img_play:
                if (mediaPlayer.isPlaying()){
                    // neu dang phat ->>> đỏi hinh ->> pause
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.btn_play);
                }else {
                    // đang dừng ->> phát ->>   start
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.pause);
                }
                SettimeTotal();
                UpdateTimeSong();
                break;
            case R.id.img_next:
                position++;
                if (position > musicList.size()-1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoiTaoMediaPlayer();
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.pause);
                SettimeTotal();
                UpdateTimeSong();
                break;
        }
    }

    private void UpdateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition())); //getCurrentPosition() vị trí hiện tại đang dc phát
                // update progress skSong
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,500);

                // kiểm tra thời gian bài hát kết thúc  thì -> next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > musicList.size()-1){
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        khoiTaoMediaPlayer();
                        mediaPlayer.start();
                        imgPlay.setImageResource(R.drawable.pause);
                        SettimeTotal();
                        UpdateTimeSong();
                    }
                });
            }
        }, 100);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void khoiTaoMediaPlayer() {
        mediaPlayer = MediaPlayer.
                create(MusicDetail.this,musicList.get(position).getUriMusic());

        imgHinh.setImageResource(musicList.get(position).getImage());
        tvTitle.setText(musicList.get(position).getName());
        mediaPlayer.start();
    }

    private void SettimeTotal() {
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        tvTimeTotal.setText(dinhDangGio.format(mediaPlayer.getDuration()) + "");  // getduration tra ra time
        // gan' max cua skSong la  mediaplayer.getduration
        seekBar.setMax(mediaPlayer.getDuration());
    }
}
