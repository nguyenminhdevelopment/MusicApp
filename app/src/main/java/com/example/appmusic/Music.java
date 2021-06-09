package com.example.appmusic;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music")
public class Music implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "image")
    private int image;
    @ColumnInfo(name = "uriMusic")
    private int uriMusic;
    @ColumnInfo(name = "album")
    private int album;

    public Music(String name, String author, int image, int uriMusic, int album) {
        this.name = name;
        this.author = author;
        this.image = image;
        this.uriMusic = uriMusic;
        this.album = album;
    }

    protected Music(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        author = in.readString();
        image = in.readInt();
        uriMusic = in.readInt();
        album = in.readInt();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeString(author);
        dest.writeInt(image);
        dest.writeInt(uriMusic);
        dest.writeInt(album);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getUriMusic() {
        return uriMusic;
    }

    public void setUriMusic(int uriMusic) {
        this.uriMusic = uriMusic;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public static Creator<Music> getCREATOR() {
        return CREATOR;
    }
}
