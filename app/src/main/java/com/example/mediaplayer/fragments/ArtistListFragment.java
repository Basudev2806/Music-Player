package com.example.mediaplayer.fragments;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.R;
import com.example.mediaplayer.adapters.ArtistAdapter;
import com.example.mediaplayer.models.ArtistModel;
import com.example.mediaplayer.services.MusicService;

import java.util.ArrayList;
import java.util.List;


public class ArtistListFragment extends MusicServiceFragment {

    private MusicService musicService;
    private boolean musicServiceStatus = false;
    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;
    private List<ArtistModel> artists;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_list,container,false);
        recyclerView =  view.findViewById(R.id.rv_artist_list);
        artists = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        artistAdapter = new ArtistAdapter(artists,getContext());
        if(musicServiceStatus) {initFragment(); }
        return view;
    }

    @Override
    public void onServiceConnected(MusicService musicService) {
        this.musicService = musicService;
        musicServiceStatus=true;
        initFragment();
    }

    @Override
    public void onServiceDisconnected() {

    }

    public void initFragment() {
            artists = musicService.getArtists();
            artistAdapter = new ArtistAdapter(artists, getContext());
            recyclerView.setAdapter(artistAdapter);
    }

}
