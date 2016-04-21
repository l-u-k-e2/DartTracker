package com.ostfalia.bs.darttracker.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ScoringFragment extends Fragment {

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
        TableLayout tableLayout = (TableLayout)getActivity().findViewById(R.id.table_layout_scoring);


        for (int i = 0; i < ids.length ; i++) {
            if (ids[i]!=0){
                TableRow tableRow = new TableRow(getActivity());
                //Vorname
                TextView playerName = new TextView(getActivity());
                playerName.setText(userDbHelper.getUser(ids[i]).getVorname());
                tableRow.addView(playerName);
                //Row der Table hinzufügen
                tableLayout.addView(tableRow);
            }
        }

        super.onActivityCreated(savedInstanceState);
    }
}
