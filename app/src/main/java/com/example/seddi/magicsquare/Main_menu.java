package com.example.seddi.magicsquare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main_menu extends AppCompatActivity {//Menue principale
    SharedPreferences.Editor score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

    }
    public void Score(View view){
        Intent i=new Intent(getBaseContext(),Score.class);//activité qui va afficher un resumé des scores selon le niveau
        startActivity(i);
    }

    public void newGame(View view) {//lancer une nouvelle partie
            Intent i=new Intent(getBaseContext(),LevelActivity.class);
            startActivity(i);
    }

    public void aboutGame(View view) {
        String url="https://en.wikipedia.org/wiki/Magic_square";
        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void exitGame(View view) {
        this.finish();
    }//quitter le jeux
}
