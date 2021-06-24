package edu.utep.cs.cs4330.textdash;
//Cesar Lopez
//80503346
//CS4330
//Dr. Cheon - Mobile Development

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Single extends AppCompatActivity {
    private Timer timerModel;
    private TextView time;
    private EditText Reader;
    private TextView wordDisplay;
    private TextView textDisplay;
    private TextView WPM;
    private String userText;
    private String totalText;
    private ProgressBar pb;
    private Button nameB;
    private int word;
    private String name;
    private long minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        Intent intent = getIntent();
        minutes = intent.getLongExtra("minutes", 0);
        //name button, hidden for when score is calculated
        nameB = findViewById(R.id.nameB);
        nameB.setVisibility(View.GONE);
        //progress bar
        pb = findViewById(R.id.prog);
        pb.setVisibility(View.VISIBLE);
        //Timer stops at 2 minutes
        timerModel = new Timer();
        time = findViewById(R.id.Time);
        //edit text reader
        Reader = findViewById(R.id.Reader);
        TCListener(Reader);
        //the words that can be chosen from and displayed on app, random
        ArrayList<String> words = new ArrayList<String>();
        words.add("Let's go out on a run, she said. I looked her in the eyes and felt my world shatter. I can't keep up with her during a run, she's going to make fun of me." +
                " I'm so anxious right now, should I confess to her I lied? I don't know what to do! This is too much for me right now.");
        words.add("Some say typing is easy, others say the latter. Typing is like reading with your fingers, if you can do it fast, you won't ever want to stop. It all takes " +
                "time and practice, but if you put in the work, you can really get far and have fun! Hope you enjoy the app!");
        wordDisplay = findViewById(R.id.WordDisplay);//what we type
        Random rand = new Random();
        textDisplay = findViewById(R.id.TextDisplay);//what to type
        textDisplay.setMovementMethod(new ScrollingMovementMethod());
        textDisplay.setText(words.get(rand.nextInt(2)));
        WPM = findViewById(R.id.WPM);//words-per-minute view
        nameB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                showNameInput();
            }//show dialog
        });
        timerModel.start();//timer thread stops at 1 minute
        new Thread(() -> {
            while (timerModel.isRunning()) {
                this.runOnUiThread(this::displayTime);
                try {
                    Thread.sleep(200); // in millis
                } catch (InterruptedException e) {}
            }
        }).start();
        new Thread(() -> {//progressbar thread
           while(pb.getProgress()< 100 || timerModel.isRunning()){
               this.runOnUiThread(this::displayProgress);
               try{
                   Thread.sleep(500);
               }
               catch(InterruptedException e){}
           }
        }).start();
        displayTime();
    }

    //updates the progress bar
    private void displayProgress(){
        if(userText == null){
            return;
        }
        if(totalText == null){
            return;
        }
        if(userText.length() <= 0){
            return;
        }
        if(totalText.length() <= 0){
            return;
        }
        double x = userText.length();
        double y = totalText.length();
        double hold = (x/y)*100;
        pb.setProgress((int)hold);
    }

    //Text Change Listener
    private void TCListener(EditText Reader){
        Reader.addTextChangedListener(new TextWatcherDEL(){
            //Not used?
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userText = s.toString();
            }
            //updates text when there is change in our display
            @SuppressLint("ResourceType")
            @Override
            public void afterTextChanged(Editable s, boolean backSpace) {
                userText = s.toString();
                totalText = textDisplay.getText().toString();

                if(totalText.length() < userText.length()){
                    return;//if length of inputted text is more than the given text, do not update
                }
                for(int i = 0; i<s.length(); i++){
                    if(userText.charAt(i) != totalText.charAt(i)){
                        wordDisplay.setTextColor(Color.RED);//if character is incorrect.
                    }
                    else{
                        wordDisplay.setTextColor(Color.GREEN);
                    }
                }
                wordDisplay.setText(s.toString());
            }
        });
    }

    //dialog to show input box
    private void showNameInput(){
        Dialog dialog = new Dialog(Single.this);
        dialog.setTitle("Name: ");
        dialog.setContentView(R.layout.input_box);
        TextView message = (TextView)dialog.findViewById(R.id.txt);
        message.setText("Name: ");
        Button ret = (Button)dialog.findViewById(R.id.button);
        EditText et = (EditText)dialog.findViewById(R.id.input);
        ret.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = et.getText().toString();
                dialog.dismiss();
                dbhelper dbHelper = new dbhelper(Single.this);
                dbHelper.addScore(Integer.toString(word), name);
                showToast("Saved!");
                nameB.setVisibility(View.GONE);
                Intent intent = new Intent(Single.this, scores.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    //display time from timer lab
    private void displayTime() {
        long sec = timerModel.elapsedTime() / 1000;
        long min = sec / 60;
        sec %= 60;
        min %= 60;
        long hold = 60-sec;
        long hold2 = minutes-1;
        if(sec == 0){
            hold = 0;
            hold2 = minutes;
        }
        if(min >= 1){
            hold2 -= 1;
        }
        if(min >= 2){
            hold2 -= 1;
        }
        time.setText(String.format("%02d:%02d", hold2, hold));
        if(min == minutes){
            Reader.setFocusable(false);
            Reader.setEnabled(false);
            Reader.setCursorVisible(false);
            Reader.setKeyListener(null);
            Reader.setBackgroundColor(Color.TRANSPARENT);
            timerModel.stop();
            calculateWPM();
        }
    }

    //after 1 minute we calculate our words per minute
    private void calculateWPM(){
        ArrayList<String> system = new ArrayList<String>();
        ArrayList<String> user = new ArrayList<String>();
        if(userText == null){
            return;
        }
        for(String word : totalText.split(" ")) {
            system.add(word);
        }
        for(String word : userText.split(" ")) {
            user.add(word);
        }
        if(user.size() > system.size()){
            WPM.setText("User, your text has more words than the original text! Error");
        }
        word = 0;
        for(int i = 0; i<user.size(); i++){
            if(system.get(i).equals(user.get(i))){
                word++;
            }
        }
        WPM.setText(String.format("WPM: %d", word));
        nameB.setVisibility(View.VISIBLE);
    }

    //used for testing
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
