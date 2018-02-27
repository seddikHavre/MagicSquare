package com.example.seddi.magicsquare;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public EditText[][] grille=new EditText[3][3] ;//tableau qui va contenir les elements entrés par l'utilisateur
    public int[][] grilleG=new int[3][3] ;//va contenir les grille generé (la reference)
    public ArrayList<Integer> roulette=new ArrayList<>();//utiliser pour le tirage aleratoire des indices des cases a afficher en debit de partie pour l'utlisateur
    public TextView[][] tabResult =new TextView[2][3];//stockage des valeurs sommé en ligne et en colonnes
    public Long temps,timePause=0L;
    Chronometer c;//chronometre
    SharedPreferences  BestScore;//
    TextView bs;//affiche le score
    int level;
    String Slevel;
   public ArrayList<Integer> r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r=new ArrayList<>();
        level=getIntent().getIntExtra("level",0);//recuperation du niveau depuis LevelActivity
        InitGrille();//generer les la grille de referencde et faire les sommes
        r=help(level);//recuperation des indices des elements a afficher dans la grille en debut de partie
        /////afficher les elements dans la grille
        for(int i=1;i<r.size();i++){
            grille[r.get(i)/3][r.get(i)%3].setText(grilleG[r.get(i)/3][r.get(i)%3]+"");
        }
        ////
        Slevel="";
        switch (level){
            case 5:
                Slevel="Easy";
                break;
            case 3:
                Slevel="Medium";
                break;
            case 0:
                Slevel="Hard";
                break;
        }
        this.c=findViewById(R.id.chronometer1);

        c.start();//demarrer le chronometre
        BestScore=getSharedPreferences(Slevel,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=BestScore.edit();

        bs=findViewById(R.id.BestScore);

        String value=BestScore.getString(Slevel,"");//initialiser le  score

        bs.setText("Best Score\n"+ value);

        if(value==""){
            editor.putString(Slevel,"00:00");
              editor.commit();
        }
    }




    @Override
    protected void onPause() {
        super.onPause();
        c.stop();
        timePause = SystemClock.elapsedRealtime() - c.getBase();//sauvegarder l'etat du timer
    }

    @Override
    protected void onResume() {
        super.onResume();
        c.setBase(SystemClock.elapsedRealtime()-timePause);//restituer l'etat du timer lors de la reprise
        c.start();
    }


    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){//enregistrer les elements lors de la rotation de l'ecran
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putSerializable("solutions",grilleG);
        temps=c.getBase();
        saveInstanceState.putLong("temps",temps);
    }

    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState){//restituer les valeurs lors de la rotation de l'ecran
        super.onRestoreInstanceState(saveInstanceState);
        grilleG=(int[][])saveInstanceState.getSerializable("solutions");
        /////////////////////////////////////////////////////////////////
        for(int i=0;i<3;i++){
            tabResult[0][i].setText(Integer.toString(sommeLigne(i)));
        }
        for(int i=0;i<3;i++){
            tabResult[1][i].setText(Integer.toString(sommeCol(i)));
        }
        /////////////////////////////////////////////////////////////////
       temps=(Long)saveInstanceState.getLong("temps");
       c.setBase(temps);
    }

    public void InitGrille(){
        EditText case00=findViewById(R.id.x00);
        grille[0][0]=case00;

        EditText case01=findViewById(R.id.x02);
        grille[0][1]=case01;

        EditText case02=findViewById(R.id.x04);
        grille[0][2]=case02;

        EditText case10=findViewById(R.id.x20);
        grille[1][0]=case10;

        EditText case11=findViewById(R.id.x22);
        grille[1][1]=case11;

        EditText case12=findViewById(R.id.x24);
        grille[1][2]=case12;

        EditText case20=findViewById(R.id.x40);
        grille[2][0]=case20;

        EditText case21=findViewById(R.id.x42);
        grille[2][1]=case21;

        EditText case22=findViewById(R.id.x44);
        grille[2][2]=case22;

        TextView  case03=findViewById(R.id.x06);
        tabResult[0][0]=case03;

        TextView  case13=findViewById(R.id.x26);
        tabResult[0][1]=case13;

        TextView  case23=findViewById(R.id.x46);
        tabResult[0][2]=case23;

        TextView  case30=findViewById(R.id.x60);
        tabResult[1][0]=case30;

        TextView  case31=findViewById(R.id.x62);
        tabResult[1][1]=case31;

        TextView  case32=findViewById(R.id.x64);
        tabResult[1][2]=case32;

        getGrille();
        /////////////
        for(int i=0;i<3;i++){//somme des lignes
            tabResult[0][i].setText(Integer.toString(sommeLigne(i)));
        }
        for(int i=0;i<3;i++){//somme des colonnes
            tabResult[1][i].setText(Integer.toString(sommeCol(i)));
        }
        ////////////
    }
    public void getGrille(){//genere la grille (9 elements entre 1 et 9 sans repetition) pour initialiser la grille

     for(int i=0;i<9;i++){
            roulette.add(i+1);
        }
        Collections.shuffle(roulette);

        for(int i=0;i<9;i++){
           grilleG[i/3][i%3]=roulette.get(i);
        }

    }

    public int sommeLigne(int index){

        int somme=grilleG[index][0]+  grilleG[index][1]+  grilleG[index][2];
        return somme;
    }
    public int sommeCol(int index){

        int somme=grilleG[0][index]+  grilleG[1][index]+  grilleG[2][index];
        return somme;
    }




   public ArrayList<Integer> help(int nbr){//fonction qui genere les indices des elements a afficher dans la grille en debut de partie

        roulette.clear();

        for(int i=1;i<10;i++){
            roulette.add(i-1);
        }
        Collections.shuffle(roulette);
        ArrayList<Integer> r=new ArrayList();
        for(int i=0;i<nbr+1;i++){
            r.add(roulette.get(i));
        }

    return r;

    }


    public int testCaseVide(){//teste combiens de cases vides restant dans la grille
        int nbCases=0;
       for(int i=0;i<9;i++){
           if(grille[i/3][i%3].getText().toString().matches("")){
               nbCases++;
           }
       }
       return nbCases ;
    }
    public void submit(View view){
        if(testCaseVide()==0){//si toutes les case sont vides on peut tester si la grille est correcte
            int nbrErrors=0;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(Integer.parseInt(grille[i][j].getText().toString())!= grilleG[i][j]){//si les elements entrés par l'utilisateur
                                                                                            //ne correspondes pas a la grille de depart
                        nbrErrors++;//incrementer le nombre d'erreurs
                    }
                }
            }
            if(nbrErrors==0){//si erreurs==0 arreter le chronometre et enregistrer le score

               c.stop();
                SaveTime(c.getText().toString());

            }
          }
    }
    void SaveTime(String time){//enregistre le score (valeur du chronometre) si celle ci est inferieur au meilleur score actuel


        String newScore[]=time.split(":");//recupere les minutes et secondes du score du obtenue dans un tableau

        SharedPreferences.Editor editor=BestScore.edit();

        String value=BestScore.getString(Slevel,"");

        if(value.matches("00:00")){
            bs.setText("Best Score\n"+time);
            editor.putString(Slevel,time);
            editor.commit();
        }



        value=BestScore.getString(Slevel,"");//recupere le score enregistré

        String best[]=value.split(":");//recupere les minutes et secondes du meilleur score du obtenue dans un tableau

       if(Integer.parseInt(newScore[0])<Integer.parseInt(best[0])){//si minutes nouveau score inferieur a minutes meilleur score
            editor.putString(Slevel,time);//remplacer la valeur du meilleur score apr le score actuel
            editor.commit();
             bs.setText("Best Score\n"+ time);//afficher le meilleur score sur l'ecran

        }else if (Integer.parseInt(newScore[0])==Integer.parseInt(best[0])){//si le minutes des deux scores sont egaux

           if (Integer.parseInt(newScore[1])<Integer.parseInt(best[1])){//comparer les secondes
               editor.putString(Slevel,time);//remplacer la valeur du meilleur score apr le score actuel
               editor.commit();
               bs.setText("Best Score\n"+ time);//afficher le meilleur score sur l'ecran
           }
        }

        }



        public void Help(View view){//affiche une valeur suplementaire pour aider l'utilisateur
            grille[r.get(0)/3][r.get(0)%3].setText(grilleG[r.get(0)/3][r.get(0)%3]+"");//
            Button help=findViewById(R.id.help);
            help.setTextColor(Color.BLACK);
            help.setEnabled(false);
        }
    }