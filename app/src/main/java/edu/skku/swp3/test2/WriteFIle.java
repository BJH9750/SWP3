package edu.skku.swp3.test2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriteFIle extends AsyncTask<ArrayList<MyItem>, Void, Void> {

    private File mFIle;
    private JsonObject jsonObject;
    private PrintWriter pw;
    private final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test";
    WriteFIle(Context context, String name) {
        File parent = context.getDataDir();
        mFIle = new File(sdPath, name);
        try{
            pw = new PrintWriter(new BufferedWriter(new FileWriter(mFIle,false)));
            Log.d("File Make", "Success");
        }catch (IOException ioe){
            ioe.printStackTrace();
            Log.d("File Make", "Fail");
        }
    }

    @Override
    protected Void doInBackground(ArrayList<MyItem>... myItems) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        jsonObject = new JsonObject();
        JsonArray nameArray = new JsonArray();
        for (MyItem oneItem : myItems[0]){
            String name = oneItem.getName();
            String id = oneItem.getSerial();
            String code = oneItem.getCode();
            nameArray.add(name);
            JsonObject tmp1 = new JsonObject();

            tmp1.addProperty("Name", name);
            tmp1.addProperty("DeviceID", id);
            tmp1.addProperty("Code", code);

            jsonObject.add(name,tmp1);
        }

        jsonObject.add("Place_Name", nameArray);
        pw.print(gson.toJson(jsonObject));
        pw.flush();
        pw.close();
        pw = null;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}

/*
jsonObject
{
        "Place_Name" : ["1", "2", "3"], << nameArray
        "1" : {"Name" : "1", "DeviceID" : "a", "Code" : "S002"}, << tmp1
        "2" : {"Name" : "2", "DeviceID" : "a", "Code" : "S001"}
}
        */