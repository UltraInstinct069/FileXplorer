package co.afa.filexplorer;

import android.media.MediaPlayer;
import android.net.Uri;
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

public class SdCard extends AppCompatActivity {
    ListView lv;
    String[] items;
    String[] item;

    public static ArrayList<File> myFiles;
    ArrayAdapter<String> adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sd_card);
        lv=(ListView)findViewById(R.id.memoryFiles);
        myFiles=findFiles(Environment.getExternalStorageDirectory());
        registerForContextMenu(lv);
        items=new String[myFiles.size()];
        for(int i=0;i<myFiles.size();i++){
            items[i]=myFiles.get(i).getName().toString();
        }
        adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.single_file,R.id.fileName,items);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File selet = new File(myFiles.get(position).getAbsolutePath());
                //String a = myFiles.get(position).getAbsolutePath().toString();
                //Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
                if (myFiles.get(position).getAbsoluteFile().isDirectory()) {
                    //Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
                    myFiles = findFiles(selet);

                    item = new String[myFiles.size()];
                    for (int i = 0; i < myFiles.size(); i++) {
                        item[i] = myFiles.get(i).getName().toString();
                    }
                    ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.single_file, R.id.fileName, item);
                    lv.setAdapter(adp);


                }

            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.contexual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.id_delete:
                Toast.makeText(getApplicationContext(), myFiles.get(info.position).getAbsolutePath(), Toast.LENGTH_LONG).show();
                File eachfile = new File(myFiles.get(info.position).getAbsolutePath());

                try {
                    if (eachfile.exists()) {
                        if (eachfile.isDirectory()) {
                            File[] list = eachfile.listFiles();
                            for (File file : list) {
                                file.delete();
                            }
                            eachfile.delete();
                        }
                        eachfile.delete();
                    }
                } catch (Exception e) {

                }

                myFiles.remove(info.position);
                adp.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

        public ArrayList<File> findFiles(File root){
        ArrayList<File> file=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File singlefile:files){
            file.add(singlefile);
        }

        return file;
    }

}
