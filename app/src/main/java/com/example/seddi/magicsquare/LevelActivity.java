package com.example.seddi.magicsquare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
    }

    public void levelEasy(View view) {//lancer l'activité Main en lui envoyant le niveau selectioner par l'utilisateur
        Intent i=new Intent(getBaseContext(),MainActivity.class);
        i.putExtra("level",5);//envoie de la valeur 5 (afficher 5 cases en debut de partie)
        startActivity(i);
    }

    public void levelMedium(View view) {
        Intent i=new Intent(getBaseContext(),MainActivity.class);
        i.putExtra("level",3);//envoie de la valeur 5 (afficher 5 cases en debut de partie)
        startActivity(i);
    }

    public void levelHard(View view) {
        Intent i=new Intent(getBaseContext(),MainActivity.class);
        i.putExtra("level",0);//envoie de la valeur 5 (afficher 5 cases en debut de partie)
        startActivity(i);
    }
}
