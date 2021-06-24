package edu.utep.cs.cs4330.textdash;
//Cesar Lopez
//80503346
//CS4330
//Dr. Cheon - Mobile Development

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextWatcherDEL implements TextWatcher {
    private int last;

    public abstract void afterTextChanged(Editable s, boolean backSpace);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){
        last = s.length();
    }

    @Override
    public void afterTextChanged(Editable s){
        afterTextChanged(s, last > s.length());
    }
}
