package com.example.appmusic;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@androidx.room.Dao
public interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllOrders(List<Music> order);

    @Query("SELECT * FROM Music")
    List<Music> findAllEmploySync();

    @Query("SELECT * FROM Music WHERE album = :album ")
    List<Music> findAlbum(String album);
}
