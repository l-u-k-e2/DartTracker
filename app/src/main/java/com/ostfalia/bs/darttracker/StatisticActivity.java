package com.ostfalia.bs.darttracker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.ostfalia.bs.darttracker.database.UserDbHelper;
import com.ostfalia.bs.darttracker.fragment.UserChoiceFragment;
import com.ostfalia.bs.darttracker.model.Shot;
import com.ostfalia.bs.darttracker.model.User;

import java.util.List;

/**
 * Created by lukas on 26.04.2016.
 */
public class StatisticActivity extends Activity {

    private TextView statisticValue;
    private List<User> users;
    private UserDbHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic);
        statisticValue = (TextView)findViewById(R.id.text_statistic_value);
        userDbHelper = new UserDbHelper(getApplicationContext());
        users = userDbHelper.getAllUser();
        fillStatistic();
    }

    private void fillStatistic(){
        List<Shot> shots = userDbHelper.getShots(users.get(0));
        Double countOfAllShots = Double.valueOf(userDbHelper.getShots(users.get(0)).size());
        Double countOfTwenties = 0d;
        for (int i = 0; i < shots.size(); i++) {
            if (shots.get(i).getPunkte() == 20){
                countOfTwenties++;
            }
        }
        String percentageTwenties = String.valueOf(countOfTwenties/countOfAllShots * 100) + " %";
        statisticValue.setText(percentageTwenties);
    }

}
