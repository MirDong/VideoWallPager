package com.example.v_zakudong.videowallpager;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    private Button wall_paper;
    private CheckBox voice_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wall_paper = (Button)findViewById(R.id.set_wall_paper);
        voice_setting = (CheckBox)findViewById(R.id.voice_setting);
        wall_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWallPaper(MainActivity.this);
            }
        });
        voice_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    VideoLiveWallPaper.setVoiceSlient(getApplicationContext());
                }else {
                    VideoLiveWallPaper.setVoiceNormal(getApplicationContext());
                }
            }
        });
    }

    public static void setWallPaper(Context context){
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(context,VideoLiveWallPaper.class));
        context.startActivity(intent);
    }
}
