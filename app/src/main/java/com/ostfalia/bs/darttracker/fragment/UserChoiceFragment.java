package com.ostfalia.bs.darttracker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.ostfalia.bs.darttracker.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 21.04.2016.
 */
public class UserChoiceFragment extends Fragment implements View.OnClickListener{

    private List<User> user = new ArrayList<>();
    private OnGameStartedListener onGameStartedListener;
    private UserDbHelper userDbHelper;
    private TableLayout tableLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userDbHelper = new UserDbHelper(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.user_choice, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button start = (Button)getActivity().findViewById(R.id.start_button);
        start.setOnClickListener(this);
        user = userDbHelper.getAllUser();
        for (int i = 0; i < user.size() ; i++) {
            tableLayout = (TableLayout)getActivity().findViewById(R.id.spieler_auswahl_table);
            TableRow tableRow = new TableRow(getActivity());
            //Vorname
            TextView playerName = new TextView(getActivity());
            playerName.setText(user.get(i).getVorname());
            playerName.setTag(user.get(i));
            tableRow.addView(playerName);
            //Checkbox
            CheckBox userCheck = new CheckBox(getActivity());
            tableRow.addView(userCheck);
            //Row der Table hinzufügen
            tableLayout.addView(tableRow);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onGameStartedListener = (OnGameStartedListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnGameStartedListener");
        }
    }

    @Override
    public void onClick(View v) {
        boolean minOneUserChecked = false;
        long[] ids = new long[user.size()];
        for (int i=1; i < tableLayout.getChildCount(); i++){
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            CheckBox checkBox = (CheckBox)row.getChildAt(1);
            if (checkBox.isChecked()){
                minOneUserChecked = true;
                TextView userText = (TextView)row.getChildAt(0);
                User user = (User)userText.getTag();
                ids[i-1] = user.getId();
            }
        }
        if (minOneUserChecked){
            onGameStartedListener.onGameStarted(ids);
        }else{
            showDialogChooseUser();
        }
    }

    private void showDialogChooseUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Mindestens Einen Spieler auswählen!");
        builder.setTitle("Spielerwahl");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnGameStartedListener {
        void onGameStarted(long[] ids);
    }
}
