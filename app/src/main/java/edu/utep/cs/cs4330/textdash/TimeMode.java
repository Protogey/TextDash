package edu.utep.cs.cs4330.textdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TimeMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_mode);
        Button minute1 = findViewById(R.id.minute1);
        Button minute2 = findViewById(R.id.minute2);
        Button minute3 = findViewById(R.id.minute3);
        minute1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TimeMode.this, Single.class);
                long minutes = 1;
                intent.putExtra("minutes", minutes);
                startActivity(intent);
            }
        });
        minute2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TimeMode.this, Single.class);
                long minutes = 2;
                intent.putExtra("minutes", minutes);
                startActivity(intent);
            }
        });
        minute3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TimeMode.this, Single.class);
                long minutes = 3;
                intent.putExtra("minutes", minutes);
                startActivity(intent);
            }
        });
    }
}
