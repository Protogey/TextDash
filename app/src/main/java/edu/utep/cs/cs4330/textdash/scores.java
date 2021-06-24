package edu.utep.cs.cs4330.textdash;


import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class scores extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer);
        dbhelper db = new dbhelper(this);
        ArrayList<HashMap<String, String>> scores = db.GetScores();
        ListView lv = (ListView)findViewById(R.id.scoreList);
        //update listview with adapter and scores from database.
        ListAdapter adapter = new SimpleAdapter(scores.this, scores, R.layout.score, new String[]{"score", "name"}, new int[]{R.id.Score, R.id.Name});
        lv.setAdapter(adapter);
    }
}
