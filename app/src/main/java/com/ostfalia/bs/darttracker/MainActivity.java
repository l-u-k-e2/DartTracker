package com.ostfalia.bs.darttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ostfalia.bs.darttracker.database.UserDbHelper;

public class MainActivity extends Activity implements View.OnClickListener {

    private String vorname;
    private EditText editText;
    UserDbHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button)findViewById(R.id.button_new_game);
        button.setOnClickListener(this);
        userDbHelper = new UserDbHelper(getApplicationContext());
        Button saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.user_name_input);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_button){
            saveName();
        }else{
            startActivity(new Intent(this, Game.class));
            Log.d("Absprung","Spring zur Spielerauswahl");
        }
    }

    private void saveName(){
        vorname = editText.getText().toString();
        long id = userDbHelper.createUser(vorname);
        Log.d("DB","id: " + String.valueOf(id) + " wurde Gespeichert!");
    }
}
