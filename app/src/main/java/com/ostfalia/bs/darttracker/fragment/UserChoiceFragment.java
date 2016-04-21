package com.ostfalia.bs.darttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ostfalia.bs.darttracker.R;
import com.ostfalia.bs.darttracker.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 21.04.2016.
 */
public class UserChoiceFragment extends Fragment{

    private List<User> user = new ArrayList<>();
    private ViewGroup userFrame;
    private float massstab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_choice, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userFrame = (ViewGroup)getActivity().findViewById(R.id.user_frame);
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
            TextView userText = new TextView(getActivity());
            userText.setText(user.get(i).getVorname());
            userText.setGravity(Gravity.RIGHT);
            userText.setTextSize(20);
            userFrame.addView(userText,params);
            //Checkbox
            CheckBox userCheck = new CheckBox(getActivity());
            userCheck.setGravity(Gravity.LEFT);
            userFrame.addView(userCheck,params);
        }
    }
}
