package com.example.mediaplayer.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import com.example.mediaplayer.R;


import com.example.mediaplayer.activities.PlayerActivity;
import com.example.mediaplayer.models.SongModel;
import com.example.mediaplayer.services.MusicService;


public class NotificationHandler {

    public static Notification createNotification(Context context, SongModel currentSong, boolean playStatus) {
        Notification.Builder nbuilder = null;
        Intent openAppIntent = new Intent(context, PlayerActivity.class);
        PendingIntent pendingOpenAppIntent = PendingIntent.getActivity(context, 0, openAppIntent, 0);

        Intent previousIntent = new Intent(context, MusicService.class);
        previousIntent.setAction("action.prev");
        PendingIntent ppreviousIntent = PendingIntent.getService(context, 0, previousIntent, 0);

        Intent playIntent = new Intent(context, MusicService.class);
        playIntent.setAction("action.play");
        PendingIntent pplayIntent = PendingIntent.getService(context, 0, playIntent, 0);

        Intent pauseIntent = new Intent(context, MusicService.class);
        pauseIntent.setAction("action.pause");
        PendingIntent ppauseIntent = PendingIntent.getService(context, 0, pauseIntent, 0);


        Intent nextIntent = new Intent(context, MusicService.class);
        nextIntent.setAction("action.next");
        PendingIntent pnextIntent = PendingIntent.getService(context, 0, nextIntent, 0);

        Bitmap bitmap = getBitmap(context, currentSong.getAlbumArt());
        nbuilder = new Notification.Builder(context)
                .setContentTitle(currentSong.getTitle())
                .setTicker(currentSong.getTitle())
                .setContentText(currentSong.getArtistName())
                .setSmallIcon(R.drawable.music_icon)
                .setContentIntent(pendingOpenAppIntent)
                .setOngoing(true)
                .setLargeIcon(bitmap)
                .addAction(android.R.drawable.ic_media_previous, "Previous", ppreviousIntent);

        if (playStatus) {
            nbuilder.addAction(android.R.drawable.ic_media_pause, "Play", ppauseIntent);
        } else {
            nbuilder.addAction(android.R.drawable.ic_media_play, "Pause", pplayIntent);
        }

        nbuilder.addAction(android.R.drawable.ic_media_next, "Next", pnextIntent)
                .setLargeIcon(bitmap);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            nbuilder.setStyle(new Notification.MediaStyle());
        }


        return nbuilder.build();
    }

    public static Bitmap getBitmap(Context context, String uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(uri));
        } catch (Exception e) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.album_default);
        }
        return bitmap;
    }

}
