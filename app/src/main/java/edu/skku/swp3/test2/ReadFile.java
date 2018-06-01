package edu.skku.swp3.test2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile extends AsyncTask<Void, Void, Void> {

    private File mFile;
    private ListView mListView;
    private MyAdapter mMyAdapter;
    private final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test";

    ReadFile(Context context, String name, ListView listView, MyAdapter myAdapter) {
        File parent = context.getDataDir();
        mFile = new File(sdPath, name);
        mListView=listView;
        mMyAdapter=myAdapter;
    }
    /*{ << JsonObject
        "Place_Name" : ["1", "2", "3"], << JsonArray
        "1" : {"Name" : "1", "DeviceID" : "a", "Code" : "S002"}, << itemObject
        "2" : {"Name" : "2", "DeviceID" : "a", "Code" : "S001"}
    }*/
    @Override
    protected Void doInBackground(Void... voids) {

        ArrayList<String> names = new ArrayList<>();
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        try {
            JsonElement element = parser.parse(new FileReader(mFile));
            Log.d("FILE", gson.toJson(element));
            if (!element.isJsonNull()){
                JsonArray array = element.getAsJsonObject().getAsJsonArray("Place_Name");
                for(int i = 0; i < array.size(); i++){
                    String str = array.get(i).getAsString();
                    JsonObject object = element.getAsJsonObject().getAsJsonObject(str);

                    MyItem oneItem = gson.fromJson(object, MyItem.class);
                    Log.d("ITEM", oneItem.getSerial()+" "+oneItem.getName()+" "+oneItem.getCode());
                    mMyAdapter.addItem(oneItem);
                }
            }
            else{
                Log.d("Json Read", "element null");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        super.onPostExecute(voids);
        mListView.setAdapter(mMyAdapter);
    }
}
