package com.example.carltidelius.systembolagetappen;

import android.os.AsyncTask;
import android.os.Debug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by carltidelius on 2017-02-09.
 */

public class AlcoholTask extends AsyncTask<Void, Void, ArrayList<String>>{
    public interface AlcoholListener{
        public void newAlcohols(ArrayList<String> alcohols);

    }
    private AlcoholListener listener;

    public AlcoholTask(AlcoholListener listener) {
        this.listener = listener;
    }

    private ArrayList<String> alcohols = new ArrayList<String>();
    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        listener.newAlcohols(strings);
        super.onPostExecute(strings);
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

            try {

                URL url = new URL("http://systemetapi.se/product");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                StringBuffer stringBuffer = new StringBuffer();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String s;
                while((s = in.readLine()) != null){
                    stringBuffer.append(s + "\r\n");
                }
                try {
                    JSONArray root = new JSONArray(stringBuffer.toString());
                    for (int i = 0; i < root.length(); i++) {
                        String name = root.getJSONObject(i).getString("name");
                        alcohols.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
            e.printStackTrace();

        }
        return alcohols;
    }
}
