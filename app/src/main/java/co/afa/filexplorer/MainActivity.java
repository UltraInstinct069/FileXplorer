package co.afa.filexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView pic;
    ImageView music;
    ImageView video;
    ImageView docu;
    ImageView appli;
    ProgressBar memory;
    ImageView downld;
    ProgressBar p;
    TextView size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       pic= (ImageView) findViewById(R.id.galle);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Gallery.class);
                startActivity(i);
            }
        });
        music=(ImageView)findViewById(R.id.Item_music);
        music.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), Musics.class);
                startActivity(i);
            }
        });
        video=(ImageView)findViewById(R.id.Item_Video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Video.class);
                startActivity(i);
            }
        });

        docu=(ImageView)findViewById(R.id.Item_documents);
        docu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TextFiles.class);
                startActivity(i);
            }
        });


        appli=(ImageView)findViewById(R.id.Item_app);
        appli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Aplications.class);
                startActivity(i);
            }
        });
        memory=(ProgressBar)findViewById(R.id.SdCard);
        memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SdCard.class);
                startActivity(i);
            }
        });
        downld=(ImageView)findViewById(R.id.Download);
        downld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Download.class);
                startActivity(i);
            }
        });

        p=(ProgressBar)findViewById(R.id.SdCard);
        int size;
        size= (int)(Environment.getExternalStorageDirectory().length())/1000000000;
        p.setProgress(size);


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
