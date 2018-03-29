package co.afa.filexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShowTexts extends AppCompatActivity {
    String fileuri;
    TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_texts);

        Intent i=getIntent();
        fileuri =i.getStringExtra("fullText");
        File texts = new File(fileuri);
        String[] loadText=Load(texts);
        String finalstr="";
        for(int j=0;j<loadText.length;j++){
            finalstr += loadText[j]+System.getProperty("line.separator");
        }
        content =(TextView)findViewById(R.id.textView2);
        content.setText(finalstr);

    }

    public static String[] Load(File file)
    {
        FileInputStream fileinputstream = null;
        try
        {
            fileinputstream = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fileinputstream);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int newlinecounter=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                newlinecounter++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fileinputstream.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[newlinecounter];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }


}
