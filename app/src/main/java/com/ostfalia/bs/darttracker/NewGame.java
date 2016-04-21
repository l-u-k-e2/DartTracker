package com.ostfalia.bs.darttracker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ostfalia.bs.darttracker.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 15.04.2016.
 */
public class NewGame extends Activity {


    private List<User> user = new ArrayList<>();
    private ViewGroup userFrame;
    private float massstab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);
        userFrame = (ViewGroup)findViewById(R.id.user_frame);
        massstab = getResources().getDisplayMetrics().density;

        user.add(new User("Lukas", "Müller", "Luke"));
        user.add(new User("Dominik", "Förster", "Sniper"));

        Log.d("massstab",String.valueOf(massstab));

        int width = userFrame.getLayoutParams().width;

        for (int i = 0; i < user.size() ; i++) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,(int)Math.round(40*massstab));
            params.rightMargin = (int)Math.round(150*massstab);
            params.leftMargin = (int)Math.round(150*massstab);
            params.topMargin = (int)Math.round(40*massstab) * (i+1);
            params.gravity = Gravity.TOP + Gravity.LEFT;
            //Textview
            TextView userText = new TextView(this);
            userText.setText(user.get(i).getVorname());
            userText.setGravity(Gravity.RIGHT);
            userText.setTextSize(20);
            userFrame.addView(userText,params);
            //Checkbox
            CheckBox userCheck = new CheckBox(this);
            userCheck.setGravity(Gravity.LEFT);
            userFrame.addView(userCheck,params);
        }


    }

}
