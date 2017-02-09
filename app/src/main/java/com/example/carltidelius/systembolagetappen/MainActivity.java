package com.example.carltidelius.systembolagetappen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AlcoholTask.AlcoholListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);



        new AlcoholTask(this).execute();
    }

    @Override
    public void newAlcohols(ArrayList<String> alcohols) {
        arrayAdapter.addAll(alcohols);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        String pos = (String)parent.getItemAtPosition(position);
        bundle.putString("name", pos);

        Intent intent = new Intent(this, AlcoholActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
