package co.afa.filexplorer;

import android.content.Context;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Video extends AppCompatActivity {
    private List<Model_text> myVIDS = new ArrayList<Model_text>();
    ListView lv;
    String[] items;
    long[] size;
    public static ArrayList<File> myvids;
    ArrayAdapter<Model_text> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        myvids=findsongs(Environment.getExternalStorageDirectory());
        items=new String[myvids.size()];
        lv=(ListView)findViewById(R.id.videolist);
        registerForContextMenu(lv);
        size=new long[myvids.size()];
        for (int i = 0; i < myvids.size(); i++) {
            items[i] = myvids.get(i).getName().toString();
            size[i] = (myvids.get(i).length())/1048576;
            myVIDS.add(new Model_text(items[i], R.drawable.vid_image,size[i]));
        }
        adp=new MyListadapter();
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getBaseContext(), Play_video.class).putExtra("positn", position).putExtra("vidList", myvids));
            }
        });

    }
    public class  MyListadapter extends ArrayAdapter<Model_text>{
        public MyListadapter(){
            super(Video.this,R.layout.single_text_format,myVIDS);
        }

        public MyListadapter(Context context, int resource) {
            super(context, resource);
        }

        public View getView(int position,View convertView,ViewGroup parent){
            View itemView=convertView;
            if (itemView == null) {
                itemView=getLayoutInflater().inflate(R.layout.single_text_format,parent,false);
            }
            Model_text current=myVIDS.get(position);
            ImageView imageview=(ImageView)itemView.findViewById((R.id.mp3View));
            imageview.setImageResource(current.getIconID());
            TextView maketext=(TextView)itemView.findViewById(R.id.docu_name);
            maketext.setText(current.getName());
            TextView sizee=(TextView)itemView.findViewById(R.id.size);
            sizee.setText("Size: "+ current.getSize()+" MB");

            return itemView;
            //    return super.getView(position,convertView,parent);
        }
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
                Toast.makeText(getApplicationContext(), myvids.get(info.position).getAbsolutePath(), Toast.LENGTH_LONG).show();
                File eachfile=new File(myvids.get(info.position).getAbsolutePath());

                try{
                    if(eachfile.exists()){
                        if(eachfile.isDirectory()){
                            File[] list=eachfile.listFiles();
                            for(File file:list){
                                file.delete();
                            }
                            eachfile.delete();
                        }
                        eachfile.delete();
                    }
                }catch(Exception e){

                }

                myVIDS.remove(info.position);
                adp.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
        //return super.onContextItemSelected(item);
    }



    public ArrayList<File> findsongs(File root){
        ArrayList<File> a=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File singlefile:files){
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                a.addAll(findsongs(singlefile));
            }
            else{
                if(singlefile.getName().endsWith(".mp4")) {
                    a.add(singlefile);
                }
            }
        }

        return a;
    }


}
