package com.ostfalia.bs.darttracker.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
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
public class ScoringFragment extends Fragment implements View.OnClickListener {

    private UserDbHelper userDbHelper;
    private List<User> users = new ArrayList<>();
    private TableLayout table;
    private int posOfAktuellerSpieler = 0;
    private TextView score1Text;
    private TextView score2Text;
    private TextView score3Text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Zugriff auf DB
        userDbHelper = new UserDbHelper(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.scoring, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //ScoringFelder
        score1Text = (TextView)getActivity().findViewById(R.id.score1_edit_text);
        score2Text = (TextView)getActivity().findViewById(R.id.score2_edit_text);
        score3Text = (TextView)getActivity().findViewById(R.id.score3_edit_text);
        //Table
        table = (TableLayout)getActivity().findViewById(R.id.table_layout_scoring);
        //Button initialisieren
        Button ok = (Button)getActivity().findViewById(R.id.scoring_ok_button);
        ok.setOnClickListener(this);
        //Args aus Fragment
        Bundle args = getArguments();
        long[] ids = args.getLongArray("user");
        //Initiale Befüllung der ScoringTable
        fillUserTable(ids);
        super.onActivityCreated(savedInstanceState);
        //Ersten User unterstreichen
        markCurrentUser();
    }

    @Override
    public void onClick(View v) {
        if (elligableShot()){
            reduceScore(countScore());
            setNextPlayer();
            deleteScoreText();
        }else{
            showElligableShotDialog();
        }
    }

    private boolean elligableShot(){
        if (score1Text.getText().length() == 0 || score2Text.getText().length() == 0 || score3Text.getText().length() == 0){
            return false;
        }else if((Integer.valueOf(score1Text.getText().toString()) > 60)
                || (Integer.valueOf(score2Text.getText().toString()) > 60)
                || (Integer.valueOf(score3Text.getText().toString()) > 60)){
            return false;
        }
        return true;
    }

    private List<Integer> countScore(){
        List<Integer> scoreList = new ArrayList<>();
        Integer score1 = Integer.valueOf((score1Text).getText().toString());
        Integer score2 = Integer.valueOf((score2Text).getText().toString());
        Integer score3 = Integer.valueOf((score3Text).getText().toString());
        scoreList.add(score1);
        scoreList.add(score2);
        scoreList.add(score3);
        return scoreList;
    }

    private void reduceScore(List<Integer> scoreList){
        Integer scoreShot = scoreList.get(0) + scoreList.get(1) + scoreList.get(2);
        TextView score = (TextView)getCurrentUserRow().getChildAt(1);
        Integer current = Integer.valueOf(score.getText().toString());
        if ((current - scoreShot)>0){
            score.setText(String.valueOf(current - scoreShot));
            //save Aufnahme in DB
            userDbHelper.saveScore(scoreList,users.get(posOfAktuellerSpieler).getId());
        }else if((current - scoreShot) == 0){
            score.setText(String.valueOf(current - scoreShot));
            //save Aufnahme in DB
            userDbHelper.saveScore(scoreList,users.get(posOfAktuellerSpieler).getId());
            showWinner();
        }
    }

    private void setNextPlayer(){
        demarkCurrentUser();
        if (posOfAktuellerSpieler >= users.size()-1){
            posOfAktuellerSpieler = 0;
        }else {
            posOfAktuellerSpieler++;
        }
        markCurrentUser();
    }

    private void deleteScoreText(){
        score1Text.setText("");
        score2Text.setText("");
        score3Text.setText("");
    }

    private void markCurrentUser(){
        TextView username = (TextView)getCurrentUserRow().getChildAt(0);
        username.setPaintFlags(username.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void demarkCurrentUser(){
        TextView username = (TextView)getCurrentUserRow().getChildAt(0);
        username.setPaintFlags(0);
    }

    private void showWinner(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(users.get(posOfAktuellerSpieler).getVorname() + " hat gewonnen");
        builder.setTitle("Winner");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showElligableShotDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Eingabe Wiederholen!");
        builder.setTitle("Ungültige Aufnahme");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private TableRow getCurrentUserRow(){
        for (int i=1; i < table.getChildCount(); i++){
            TableRow row = (TableRow) table.getChildAt(i);
            TextView tv = (TextView)row.getChildAt(0);
            User user = (User)tv.getTag();
            if (user.getId() == users.get(posOfAktuellerSpieler).getId()){
                return row;
            }
        }
        return null;
    }

    private void fillUserTable(long ids[]){
        for (int i = 0; i < ids.length ; i++) {
            if (ids[i]!=0){
                //User holen
                User user = userDbHelper.getUser(ids[i]);
                users.add(user);
                //Row erstellen + Layout
                TableRow tableRow = new TableRow(getActivity());
                TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,20);
                tableRow.setLayoutParams(layoutParams);
                //Vorname der Row hinzufügen
                TextView playerName = new TextView(getActivity());
                playerName.setText(userDbHelper.getUser(ids[i]).getVorname());
                playerName.setTextSize(20);
                playerName.setTag(user);
                tableRow.addView(playerName);
                //Punktzahl der Row hinzufügen
                TextView score = new TextView(getActivity());
                score.setText("501");
                score.setTextSize(20);
                tableRow.addView(score);
                //Row der Table hinzufügen
                table.addView(tableRow);
            }
        }
    }
}
