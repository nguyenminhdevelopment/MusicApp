package com.example.appmusic;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumActivity extends AppCompatActivity {

    @BindView(R.id.rcl_music)
    RecyclerView rclMusic;

    MusicAdapter adapter;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        appDatabase = AppDatabase.getInMemoryDatabase(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        String album = getIntent().getStringExtra("album");

        adapter = new MusicAdapter(this, appDatabase.employDao().findAlbum(album));
        rclMusic.setAdapter(adapter);
    }

}