package com.example.seddi.magicsquare;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences scoreH=getSharedPreferences("Hard", Context.MODE_PRIVATE);
        SharedPreferences scoreM=getSharedPreferences("Medium", Context.MODE_PRIVATE);
        SharedPreferences scoreE=getSharedPreferences("Easy", Context.MODE_PRIVATE);


        TextView hard=findViewById(R.id.hard);
        String scoreHard=scoreH.getString("Hard","");
        hard.setText("Score Hard : "+scoreHard);

        TextView medium=findViewById(R.id.medium);
        String scoreMedium=scoreM.getString("Medium","");
        medium.setText("Score Medium : "+scoreMedium);

        TextView easy=findViewById(R.id.easy);
        String scoreEasy=scoreE.getString("Easy","");
        easy.setText("Score Hard : "+scoreEasy);
    }
}
