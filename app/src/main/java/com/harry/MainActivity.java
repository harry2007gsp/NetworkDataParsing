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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
    }

    public void fetchDataOnButton(View view) {
        networkConnection = new NetworkConnection();
        networkConnection.fetchData(this);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("test", "run");
//
//                NetworkConnection networkConnection = new NetworkConnection();
//                String string = networkConnection.downloadWithURLConnecton();
//                list = networkConnection.parseData(string);
//                for (Model m : list) {
//                    Log.d("test", m.getPlace());
//                }
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        text.setText(list.get(0).getPlace());
////                    }
////                });
//                updateUI();
//            }
//        }).start();
    }

    public void updateUI(String string) {
        Log.d("test", "update");
        list = networkConnection.parseData(string);
        for (Model m : list) {
            Log.d("test", m.getPlace());
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(list.get(0).getPlace());
            }
        });

    }

}
