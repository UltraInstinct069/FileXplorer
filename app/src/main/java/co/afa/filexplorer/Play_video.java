package co.afa.filexplorer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class Play_video extends AppCompatActivity {
     VideoView play;
    ArrayList<File> myvid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        myvid=(ArrayList)b.getParcelableArrayList("vidList");
        int position=b.getInt("positn",0);
        Uri u=Uri.parse(myvid.get(position).toString());

        play= (VideoView) findViewById(R.id.videoView);
        play.setVideoPath(String.valueOf(u));
        play.setMediaController(new MediaController(this));
        play.start();
        play.requestFocus();
    }

}
