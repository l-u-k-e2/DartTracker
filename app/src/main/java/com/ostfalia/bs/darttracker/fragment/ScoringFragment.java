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
import com.ostfalia.bs.darttracker.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 21.04.2016.
 */
public class ScoringFragment extends Fragment implements View.OnClickListener {

    private UserDbHelper userDbHelper;
    private List<User> users = new ArrayList<>();
    private User aktuellerSpieler;
    private TableLayout table;
    private int spielerZeiger;



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
        table = (TableLayout)getActivity().findViewById(R.id.table_layout_scoring);
        //Button initialisieren
        Button ok = (Button)getActivity().findViewById(R.id.scoring_ok_button);
        ok.setOnClickListener(this);

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
                //Vorname
                TextView playerName = new TextView(getActivity());
                playerName.setText(userDbHelper.getUser(ids[i]).getVorname());
                playerName.setTextSize(20);
                playerName.setTag(user);
                tableRow.addView(playerName);
                //Punktzahl
                TextView score = new TextView(getActivity());
                score.setText("501");
                score.setTextSize(20);
                tableRow.addView(score);
                //Row der Table hinzufügen
                table.addView(tableRow);
            }
            startGame();
        }

        super.onActivityCreated(savedInstanceState);
    }

    private void startGame(){
        aktuellerSpieler = users.get(0);
        spielerZeiger = 1;
    }

    @Override
    public void onClick(View v) {
        Integer scoreShot = 0;
        TextView score1 = (TextView)getActivity().findViewById(R.id.score1_edit_text);
        TextView score2 = (TextView)getActivity().findViewById(R.id.score2_edit_text);
        TextView score3 = (TextView)getActivity().findViewById(R.id.score3_edit_text);
        scoreShot = Integer.valueOf(score1.getText().toString()) +
                    Integer.valueOf(score2.getText().toString()) +
                    Integer.valueOf(score3.getText().toString());
        for (int i=1; i < table.getChildCount(); i++){
            TableRow row = (TableRow) table.getChildAt(i);
            TextView vorname = (TextView)row.getChildAt(0);
            User user = (User)vorname.getTag();
            if (user.getId() == aktuellerSpieler.getId()){
                TextView score = (TextView)row.getChildAt(1);
                String test = score.getText().toString();
                score.setText(String.valueOf(Integer.valueOf(test) - scoreShot));
            }
        }
        if (spielerZeiger >= users.size()){
            spielerZeiger = 1;
        }else {
            spielerZeiger++;
        }
        aktuellerSpieler = users.get(spielerZeiger-1);
    }
}
