package com.example.v_zakudong.videowallpager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by v_zakudong on 2017/5/16.
 */

public class VideoLiveWallPaper extends WallpaperService{
    public static final String ACTION_FILTER="android.action.wallpaper.dzk";
    public static final String ACTION = "action";
    public static final  int WALL_PAPER_NORMAL=0;
    public static final  int WALL_PAPER_SLIENT=1;
    public BroadcastReceiver receiver;
    @Override
    public Engine onCreateEngine() {
        return new VideoEngine();
    }

    public static void setVoiceNormal(Context context){
        Intent intent = new Intent(ACTION_FILTER);
        intent.putExtra(ACTION,WALL_PAPER_NORMAL);
        context.sendBroadcast(intent);
    }
    public static void setVoiceSlient(Context context){
        Intent intent = new Intent(ACTION_FILTER);
        intent.putExtra(ACTION,WALL_PAPER_SLIENT);
        context.sendBroadcast(intent);
    }
    class VideoEngine extends Engine{
        private MediaPlayer mMediaPlayer;
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            IntentFilter filter = new IntentFilter(ACTION_FILTER);

            registerReceiver(receiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int resut = intent.getIntExtra(ACTION, 0);
                    switch (resut){
                        case WALL_PAPER_NORMAL:
                            mMediaPlayer.setVolume(1.0f,1.0f);
                            break;
                        case WALL_PAPER_SLIENT:
                            mMediaPlayer.setVolume(0,0);
                            break;
                    }
                }
            },filter);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            unregisterReceiver(receiver);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible){
                mMediaPlayer.start();
            }else {
                mMediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setSurface(holder.getSurface());
            try {
                AssetManager assets = getApplicationContext().getAssets();
                AssetFileDescriptor descriptor = assets.openFd("text1.mp4");
                mMediaPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.setVolume(1.0f,1.0f);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDesiredSizeChanged(int desiredWidth, int desiredHeight) {
            super.onDesiredSizeChanged(desiredWidth, desiredHeight);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
