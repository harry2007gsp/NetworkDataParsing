package com.harry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ConnectionListener {
    TextView text;
    NetworkConnection networkConnection;
    ArrayList<Model> list;
    String url = "http://api.geonames.org/postalCodeLookupJSON?postalcode=6600&country=AT&username=demo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
    }

    public void fetchDataOnButton(View view) {
        networkConnection = new NetworkConnection(url);
        networkConnection.fetchData(this);
    }

    // Interface method to be used by all Activities if any to get a way to have list from the worker
    // thread and update the UI thread
    public void updateUI(final ArrayList<Model> list) {
        Log.d("test", "update");
        this.list = list;
        for (Model m : list) {
            Log.d("test", m.getPlace());
        }
        // method to update UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(list.get(0).getPlace());
            }
        });

    }

}
