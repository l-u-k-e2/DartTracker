package com.ostfalia.bs.darttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ostfalia.bs.darttracker.R;

import java.util.ArrayList;

/**
 * Created by lukas on 21.04.2016.
 */
public class ScoringFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scoring, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        ArrayList<Integer> arrayList = args.getIntegerArrayList("user");
        TableLayout tableLayout = (TableLayout)getActivity().findViewById(R.id.table_layout_scoring);
        TableRow tableRow = new TableRow(getActivity());
        TextView playerName = new TextView(getActivity());
        playerName.setText(arrayList.get(0).toString());//hier muss Name aus DB geholt werden
        tableRow.addView(playerName);
        tableLayout.addView(tableRow);
        super.onActivityCreated(savedInstanceState);
    }
}
