package co.afa.filexplorer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

public class TextFiles extends AppCompatActivity {

    private List<Model_text> myTxts = new ArrayList<Model_text>();
    ArrayList<String> list;
    ListView txtfiless;
    String[] items;
    long[] size;
    public static ArrayList<File> mytxts;
    ArrayAdapter<Model_text> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_files);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtfiless = (ListView) findViewById(R.id.textlists);
        registerForContextMenu(txtfiless);

        mytxts = findtxts(Environment.getExternalStorageDirectory());

        items = new String[mytxts.size()];
        size=new long[mytxts.size()];
        for (int i = 0; i < mytxts.size(); i++) {
            items[i] = mytxts.get(i).getName().toString();
            size[i] = mytxts.get(i).length();
            myTxts.add(new Model_text(items[i], R.drawable.txt_image,size[i]));
        }

        adapter=new MyListadapter();
        txtfiless.setAdapter(adapter);
        txtfiless.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File texts = new File(mytxts.get(position).getAbsolutePath());
                String a = mytxts.get(position).getAbsolutePath();
                //String[] loadText = Load(texts);
                startActivity(new Intent(getApplicationContext(), ShowTexts.class).putExtra("fullText", a));


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
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.id_delete:
                Toast.makeText(getApplicationContext(), mytxts.get(info.position).getAbsolutePath(), Toast.LENGTH_LONG).show();
                File f=new File(mytxts.get(info.position).getAbsolutePath());

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

                myTxts.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
        //return super.onContextItemSelected(item);
    }

    public class  MyListadapter extends ArrayAdapter<Model_text>{
        public MyListadapter(){
            super(TextFiles.this,R.layout.single_text_format,myTxts);
        }

        public MyListadapter(Context context, int resource) {
            super(context, resource);
        }

        public View getView(int position,View convertView,ViewGroup parent){
            View itemView=convertView;
            if (itemView == null) {
                itemView=getLayoutInflater().inflate(R.layout.single_text_format,parent,false);
            }
            Model_text current=myTxts.get(position);
            ImageView imageview=(ImageView)itemView.findViewById((R.id.mp3View));
            imageview.setImageResource(current.getIconID());
            TextView maketext=(TextView)itemView.findViewById(R.id.docu_name);
            maketext.setText(current.getName());
            TextView sizee=(TextView)itemView.findViewById(R.id.size);
            sizee.setText("Size: "+ current.getSize()+" byte");

            return itemView;
            //    return super.getView(position,convertView,parent);
        }
    }


    public ArrayList<File> findtxts(File root){
        ArrayList<File> a=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File singlefile:files){
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                a.addAll(findtxts(singlefile));
            }
            else{
                if(singlefile.getName().endsWith(".txt")) {
                    a.add(singlefile);
                }
            }
        }

        return a;
    }

}
