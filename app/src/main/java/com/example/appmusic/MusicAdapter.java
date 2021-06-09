package com.example.appmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private List<Music> musicList;
    private Context context;
    private LayoutInflater inflater;

    public MusicAdapter(Context context, List<Music> musicList) {
        this.musicList = musicList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_music,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.binData(music);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MusicDetail.class);
            intent.putExtra("position",position);
            intent.putParcelableArrayListExtra("lstData", (ArrayList<? extends Parcelable>) musicList);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name_music)
        AppCompatTextView tvName;

        @BindView(R.id.ic_music)
        AppCompatImageView imgMusic;

        @BindView(R.id.tv_author)
        AppCompatTextView tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void binData(Music music){
            imgMusic.setImageResource(music.getImage());
            tvName.setText(music.getName());
            tvAuthor.setText(music.getAuthor());
        }
    }
}
