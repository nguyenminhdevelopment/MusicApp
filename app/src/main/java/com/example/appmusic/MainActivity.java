package com.example.appmusic;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rcl_music)
    RecyclerView rclMusic;

    MusicAdapter adapter;
    List<Music> musicList = new ArrayList<>();
    @BindView(R.id.search)
    AppCompatEditText search;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        appDatabase = AppDatabase.getInMemoryDatabase(this);
        if (appDatabase.employDao().findAllEmploySync().size() == 0) {
            appDatabase.employDao().insertAllOrders(createListMusic());
        }

        Objects.requireNonNull(getSupportActionBar()).hide();

        adapter = new MusicAdapter(this, appDatabase.employDao().findAllEmploySync());
        rclMusic.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.setMusicList(getListSearchArtist(s.toString(), appDatabase.employDao().findAllEmploySync()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public List<Music> createListMusic() {
        musicList.add(new Music("Laylalay", "Jack - J97", R.drawable.bai1, R.raw.lay_la_lay, 1));
        musicList.add(new Music("Qu?? Kh??? ????ng B??ng", "Gemini Band", R.drawable.bai2, R.raw.qua_khu_dong_bang, 1));
        musicList.add(new Music("R??ng Kh??n", "Ph?? Ph????ng Anh, RIN9", R.drawable.bai3, R.raw.rang_khon, 2));
        musicList.add(new Music("?????ng H???n Ki???p Sau", "????nh D??ng, ACV", R.drawable.bai4, R.raw.dunghenkiepsau, 2));
        musicList.add(new Music("H??m N??ng", "Emily, 1989s Entertainment", R.drawable.bai5, R.raw.hamnong, 2));
        musicList.add(new Music("Th??ng M???y Em Nh??? Anh?", "H?? Anh Tu???n", R.drawable.bai6, R.raw.thang_may_em_nho_anh, 1));
        musicList.add(new Music("Ng?????i L??? T???ng Th????ng", "Nh?? Vi???t, ACV", R.drawable.bai7, R.raw.nguoi_la_tung_thuong, 2));
        musicList.add(new Music("?????ng ????a V???i L???a (Thi??n Th???n H??? M???nh Ost)", "Lena", R.drawable.bai8, R.raw.dung_dua_voi_lua, 1));
        musicList.add(new Music("Th?????ng Th???c N???i Bu???n", "Ti??n Cookie, 1989s Entertainment", R.drawable.bai9, R.raw.thuong_thuc_noi_buon, 2));
        musicList.add(new Music("Em ?????ng Kh??c", "Chillies", R.drawable.bai10, R.raw.em_dung_khoc, 2));
        musicList.add(new Music("M?????n Gi?? B??? M??ng", "X2X", R.drawable.muon_gio_be_mang, R.raw.muon_gio_be_mang, 1));
        musicList.add(new Music("Thi??n H??? H???u T??nh Nh??n", "??an Tr?????ng, Juky San", R.drawable.thien_ha_huu_tinh, R.raw.thien_ha_huu_tinh, 2));
        musicList.add(new Music("H??? C??n V????ng N???ng", "DatKaa", R.drawable.ha_con_vuong_nang, R.raw.ha_con_vuong_nang, 1));
        return musicList;
    }

    public List<Music> getListSearchArtist(String textQuery, List<Music> artists) {
        List<Music> listAlbumSearch = new ArrayList<>();
        for (Music movies : artists) {
            if (movies.getName().toLowerCase().contains(textQuery.toLowerCase())) {
                listAlbumSearch.add(movies);
            }
        }
        return listAlbumSearch;
    }

    @OnClick({R.id.album1, R.id.album2})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, AlbumActivity.class);
        switch (view.getId()) {
            case R.id.album1:
                intent.putExtra("album","1");
                break;
            case R.id.album2:
                intent.putExtra("album","2");
                break;
        }
        startActivity(intent);
    }
}