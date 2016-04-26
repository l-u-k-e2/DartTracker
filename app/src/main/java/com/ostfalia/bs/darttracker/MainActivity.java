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
        Button buttonStatistic = (Button)findViewById(R.id.button_statistic);
        buttonStatistic.setOnClickListener(this);
        userDbHelper = new UserDbHelper(getApplicationContext());
        Button saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.user_name_input);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_button){
            saveName();
        }else if (v.getId() == R.id.button_new_game){
            startActivity(new Intent(this, Game.class));
        }else if (v.getId() == R.id.button_statistic){
            startActivity(new Intent(this, StatisticActivity.class));
        }
    }

    private void saveName(){
        vorname = editText.getText().toString();
        long id = userDbHelper.createUser(vorname);
        Log.d("DB","id: " + String.valueOf(id) + " wurde Gespeichert!");
    }
}
