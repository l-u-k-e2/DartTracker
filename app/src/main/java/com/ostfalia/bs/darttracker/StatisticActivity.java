package com.ostfalia.bs.darttracker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ostfalia.bs.darttracker.database.UserDbHelper;
import com.ostfalia.bs.darttracker.fragment.UserChoiceFragment;
import com.ostfalia.bs.darttracker.model.Shot;
import com.ostfalia.bs.darttracker.model.User;
import com.ostfalia.bs.darttracker.util.OnSwipeTouchListener;

import java.util.List;

/**
 * Created by lukas on 26.04.2016.
 */
public class StatisticActivity extends Activity {

    private TextView statisticTwenty;
    private TextView statisticFourty;
    private TextView statisticSixty;
    private TextView heading;
    private List<User> users;
    private UserDbHelper userDbHelper;
    private int userZeiger = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relative_layout_statistic);
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                lastUser();
            }
            @Override
            public void onSwipeLeft() {
                nextUser();
            }
        });
        statisticTwenty = (TextView)findViewById(R.id.text_statistic_20);
        statisticFourty = (TextView)findViewById(R.id.text_statistic_40);
        statisticSixty = (TextView)findViewById(R.id.text_statistic_60);
        heading = (TextView)findViewById(R.id.heading_statistic_activity);
        userDbHelper = new UserDbHelper(getApplicationContext());
        users = userDbHelper.getAllUser();
        fillStatistic(users.get(0));
    }

    private void lastUser() {
        if (userZeiger>0){
            userZeiger--;
            fillStatistic(users.get(userZeiger));
        }
    }

    private void nextUser() {
        if (userZeiger<users.size()-1){
            userZeiger++;
            fillStatistic(users.get(userZeiger));
        }
    }

    private void fillStatistic(User user){
        heading.setText("Statistik " +  user.getVorname());
        List<Shot> shots = userDbHelper.getShots(user);
        Double countOfAllShots = Double.valueOf(userDbHelper.getShots(user).size());
        Double countOfTwenties = 0d;
        Double countOfForties = 0d;
        Double countOFSixties = 0d;
        for (int i = 0; i < shots.size(); i++) {
            switch (shots.get(i).getPunkte()){
                case 20:
                    countOfTwenties++;
                    break;
                case 40:
                    countOfForties++;
                    break;
                case 60:
                    countOFSixties++;
                    break;
            }
        }
        String percentageTwenties = String.valueOf(Math.round(countOfTwenties/countOfAllShots * 100)) + " %";
        String percentageFourties = String.valueOf(Math.round(countOfForties/countOfAllShots * 100)) + " %";
        String percentageSixties = String.valueOf(Math.round(countOFSixties/countOfAllShots * 100)) + " %";
        statisticTwenty.setText(percentageTwenties);
        statisticFourty.setText(percentageFourties);
        statisticSixty.setText(percentageSixties);
    }

}
