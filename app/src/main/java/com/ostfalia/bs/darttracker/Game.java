package com.ostfalia.bs.darttracker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ostfalia.bs.darttracker.fragment.ScoringFragment;
import com.ostfalia.bs.darttracker.fragment.UserChoiceFragment;
import com.ostfalia.bs.darttracker.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 15.04.2016.
 */
public class Game extends FragmentActivity implements UserChoiceFragment.OnGameStartedListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        UserChoiceFragment userChoiceFragment = new UserChoiceFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, userChoiceFragment).commit();

    }

    @Override
    public void onGameStarted(long[] ids) {
        ScoringFragment scoringFragment = new ScoringFragment();
        Bundle args = new Bundle();
        args.putLongArray("user",ids);
        scoringFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, scoringFragment);

        transaction.commit();

        Log.d("onGameStarted","Starte das SPiel!!!");
    }


}
