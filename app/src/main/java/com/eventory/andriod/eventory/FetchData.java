package com.eventory.andriod.eventory;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Michael on 3/6/2018.
 */

public class FetchData extends AsyncTask<Void,Void,Void> {

    String data = "";
    static String name = "";
    private static Context mContext;

    static public String mUrl;

    public FetchData(String url, Context context){
        mUrl = url;
        mContext = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        try{
            URL url = new URL(mUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null)
            {
                line = bufferedReader.readLine();
                data = data + line;
            }
            //TODO this only works if it can find a item with that barcode does not handle error
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray.length() != 0) {
                try {
                    jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.has("title")) {
                        name = jsonObject.getString("title");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(mContext,"Could Not Find UPC In Database",Toast.LENGTH_LONG).show();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        //Setting text of fields go here
    }
}
