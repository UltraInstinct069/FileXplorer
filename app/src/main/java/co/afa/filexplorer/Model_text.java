package co.afa.filexplorer;

/**
 * Created by Abrar Abir on 2/19/2016.
 */
public class Model_text {
    private String name;
    private int iconID;
    private long size;
    public long getSize() {
        return size;
    }




    public Model_text(String name,int iconID,long si){
        super();
        this.iconID=iconID;
        this.name=name;
        this.size=si;

    }



    public String getName(){
        return  name;
    }
    public int getIconID(){
        return iconID;
    }
}
