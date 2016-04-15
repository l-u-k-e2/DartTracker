package com.ostfalia.bs.darttracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ostfalia.bs.darttracker.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 15.04.2016.
 */
public class NewGame extends Activity {


    private List<User> user = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);
        //add TestUser später aus DB
        user.add(new User("Lukas","Müller","Luke"));
        user.add(new User("Dominik","Förster","Sniper"));

        for (int i = 0; i < user.size() ; i++) {
            //Benutzer und Checkbox anzeigen
        }

    }

}
