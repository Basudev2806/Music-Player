package com.example.mediaplayer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mediaplayer.R;
import com.example.mediaplayer.adapters.SectionsPageAdapter;
import com.example.mediaplayer.fragments.AlbumListFragment;
import com.example.mediaplayer.fragments.ArtistListFragment;
import com.example.mediaplayer.fragments.PlayListFragment;
import com.example.mediaplayer.fragments.SongListFragment;
import com.example.mediaplayer.fragments.SongPlayerFragment;
import com.google.android.material.tabs.TabLayout;

public class PlayerActivity extends MusicServiceActivity {
    public static final String TAG = PlayerActivity.class.getSimpleName();
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }@Override
    protected void onResume() {
        super.onResume();
        removeNotification();
    }

    public void removeNotification() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(12302);
    }

    @Override
    public void onServiceConnected() {
        Log.d(TAG,"onService Connected");
        handleAllView();
    }

    public void handleAllView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Fragment songPlayerFragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (songPlayerFragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_content, new SongPlayerFragment(), "SongPlayer").commit();
            Log.d(TAG, "songPlayerFragment Fragment new created");
        } else {
            Log.d(TAG, "songPlayerFragment Fragment reused ");
        }

    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        SongListFragment songListFragment;
        AlbumListFragment albumListFragment;
        PlayListFragment playListFragment;
        ArtistListFragment artistListFragment;
        songListFragment = new SongListFragment();
        albumListFragment = new AlbumListFragment();
        artistListFragment = new ArtistListFragment();
        playListFragment = new PlayListFragment();
        adapter.addFragment(songListFragment, "All Songs");
        adapter.addFragment(albumListFragment, "Albums");
        adapter.addFragment(artistListFragment, "Artist");
        adapter.addFragment(playListFragment, "PlayList");
        viewPager.setAdapter(adapter);
    }

    public Fragment findWithId(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent k = new Intent(this, SettingsActivity.class);
            startActivity(k);
            return true;
        } else if (id == R.id.searchSongItem) {
            Intent search = new Intent(this, SearchActivity.class);
            startActivity(search);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}