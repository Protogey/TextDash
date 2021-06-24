package edu.utep.cs.cs4330.textdash;
//Cesar Lopez
//80503346
//CS4330
//Dr. Cheon - Mobile Development

//Multiplayer is still incomplete.
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Show buttons for single player and scores
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button single = findViewById(R.id.Singbutton);
        //button that launches single player
        single.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, TimeMode.class);
                startActivity(intent);
            }
        });
    }
}