package com.harry;

import android.graphics.AvoidXfermode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.util.ArrayList;

/**
 * Created by Harry on 8/15/15.
 */
public class NetworkConnection {
//    ArrayList<Model> list;

    public void fetchData(final ConnectionListener connectionListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "run");

                NetworkConnection networkConnection = new NetworkConnection();
                String string = networkConnection.downloadWithURLConnecton();
//                list = networkConnection.parseData(string);
//                for (Model m : list) {
//                    Log.d("test", m.getPlace());
//                }
                connectionListener.updateUI(string);
            }

        }).start();
    }

    public String downloadData() {
        String input="";
        String url = "http://api.geonames.org/postalCodeLookupJSON?postalcode=6600&country=AT&username=demo";
        InputStream inputStream = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            inputStream = httpResponse.getEntity().getContent();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder b = new StringBuilder();

            while ((line = br.readLine()) != null) {
                b.append(line);
            }
            input = b.toString();
//            Log.d("test", input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public String downloadWithURLConnecton() {
        String url = "http://api.geonames.org/postalCodeLookupJSON?postalcode=6600&country=AT&username=demo";
        InputStream inputStream = null;
        StringBuilder b = new StringBuilder();


        try {
            URL url1 = new URL(url);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                httpURLConnection.setRequestMethod("GET");
                inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while ((line = br.readLine()) != null) {
                    b.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return b.toString();
    }

    public ArrayList<Model> parseData(String string) {
        ArrayList<Model> modelList = new ArrayList<>();
        Log.d("test", "got in parse data");
        Log.d("test", string);
        try {
            JSONObject object = new JSONObject(string);
            JSONArray array = object.optJSONArray("postalcodes");
            for (int i = 0; i < array.length(); i++) {
                JSONObject arrayObject = array.optJSONObject(i);
                String country = arrayObject.optString("countryCode");
                String place = arrayObject.optString("placeName");
                double longitude = arrayObject.optDouble("lng");
                double lattitude = arrayObject.optDouble("lat");
                Model model = new Model(country, place, longitude, lattitude);
                modelList.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }
}
