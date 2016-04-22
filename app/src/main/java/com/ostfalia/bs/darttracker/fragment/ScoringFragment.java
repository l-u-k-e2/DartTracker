package com.ostfalia.bs.darttracker.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ostfalia.bs.darttracker.R;
import com.ostfalia.bs.darttracker.database.UserDbHelper;

import java.util.ArrayList;

/**
 * Created by lukas on 21.04.2016.
 */
public class ScoringFragment extends Fragment implements View.OnClickListener {

    UserDbHelper userDbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userDbHelper = new UserDbHelper(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.scoring, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        //Array mit ausgewählten IDs
        long[] ids = args.getLongArray("user");
        TableLayout table = (TableLayout)getActivity().findViewById(R.id.table_layout_scoring);
        //Button initialisieren
        Button ok = (Button)getActivity().findViewById(R.id.scoring_ok_button);
        ok.setOnClickListener(this);

        for (int i = 0; i < ids.length ; i++) {
            if (ids[i]!=0){
                //Row erstellen + Layout
                TableRow tableRow = new TableRow(getActivity());
                TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,20);
                tableRow.setLayoutParams(layoutParams);
                //Vorname
                TextView playerName = new TextView(getActivity());
                playerName.setText(userDbHelper.getUser(ids[i]).getVorname());
                playerName.setTextSize(20);
                tableRow.addView(playerName);
                //Punktzahl
                TextView score = new TextView(getActivity());
                score.setText("501");
                score.setTextSize(20);
                tableRow.addView(score);
                //Row der Table hinzufügen
                table.addView(tableRow);
            }
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        //Runterrechnen
    }
}
