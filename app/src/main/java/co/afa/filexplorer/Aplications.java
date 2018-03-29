package co.afa.filexplorer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Aplications extends AppCompatActivity {
    ListView lv;
    String[] items;
    public static ArrayList<File> myapps;
    ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv=(ListView)findViewById(R.id.appList);
        registerForContextMenu(lv);
        myapps=findapps(Environment.getExternalStorageDirectory());
        items=new String[myapps.size()];
        for(int i=0;i<myapps.size();i++){
            items[i]=myapps.get(i).getName().toString();
        }
        adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.song_list,R.id.song_name,items);
        lv.setAdapter(adp);

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.contexual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.id_delete:
                Toast.makeText(getApplicationContext(), myapps.get(info.position).getAbsolutePath(), Toast.LENGTH_LONG).show();
                File f=new File(myapps.get(info.position).getAbsolutePath());

                try{
                    if(f.exists()){
                        if(f.isDirectory()){
                            File[] list=f.listFiles();
                            for(File fu:list){
                                fu.delete();
                            }
                            f.delete();
                        }
                        f.delete();
                    }
                }catch(Exception e){

                }

                myapps.remove(info.position);
                adp.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
        //return super.onContextItemSelected(item);
    }

    public ArrayList<File> findapps(File root){
        ArrayList<File> a=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File singlefile:files){
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                a.addAll(findapps(singlefile));
            }
            else{
                if(singlefile.getName().endsWith(".apk")) {
                    a.add(singlefile);
                }
            }
        }

        return a;
    }


}
