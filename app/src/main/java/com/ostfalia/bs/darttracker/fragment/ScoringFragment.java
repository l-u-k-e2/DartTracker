package com.ostfalia.bs.darttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView text = (TextView) getActivity().findViewById(R.id.textView2);
        text.setText(text.getText() + arrayList.get(0).toString());
        super.onActivityCreated(savedInstanceState);
    }
}
