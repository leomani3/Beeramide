package com.example.leove.beeramide;

import android.content.Intent;
import android.os.Debug;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by leove on 06/03/2018.
 */

public class GameActivity extends AppCompatActivity {

    public ArrayList<String> rules = new ArrayList<String>();
    public ArrayList<String> playerList = new ArrayList<String>();
    public static int NB_ROUND_MAX;
    public int currentRound = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        playerList = getIntent().getStringArrayListExtra("playerList");
        rules=getIntent().getStringArrayListExtra("ruleList");
        NB_ROUND_MAX = getIntent().getIntExtra("nbTurns",15);
        newRound();
        TextView textTurns = findViewById(R.id.textTurns);
        textTurns.setText("Turn "+currentRound+" out of "+NB_ROUND_MAX+"!!");
    }



    public boolean onTouchEvent(MotionEvent event){

        TextView activeRuleTv = findViewById(R.id.activeRule);

        int action = event.getAction();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                //new round
                newRound();

                return true;
            case (MotionEvent.ACTION_MOVE) :
                return true;
            case (MotionEvent.ACTION_UP) :
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    public String pickNewRandomRule(){
        //création d'un nombre aléatoire
        Random rdm = new Random();
        int rdmNumber = rdm.nextInt(rules.size());

        //on va chercher la règle qui correspond au random
        String tempRule = rules.get(rdmNumber);
        //on retire cette règle de l'ArrayList afin qu'elle ne soit plus choisie
        rules.remove(rdmNumber);
        tempRule = remplacePlayerString(tempRule);

        return tempRule;
    }

    public void newRound(){
        TextView tv = findViewById(R.id.activeRule);

        if (currentRound == NB_ROUND_MAX){
            //démarrer une nouvelle activité
            Intent intent = new Intent(this, TakePhotoActivity.class);
            intent.putStringArrayListExtra("playerList", playerList);
            intent.putExtra("gameMoment", 1);
            startActivity(intent);

            Log.e("feedback", "Fin de la partie");
        }
        else{
            tv.setText(pickNewRandomRule());
            currentRound++;
            TextView textTurns = findViewById(R.id.textTurns);
            textTurns.setText("Turn "+currentRound+" out of "+NB_ROUND_MAX+"!!");
        }
    }



    private String pickRandomPlayer(){
        Random rdm = new Random();
        int randomNumber = rdm.nextInt(playerList.size());

        return playerList.get(randomNumber);
    }

    //Cette fonction sert à remplacer les string "player" par le nom d'un des joueur choisi aléatoirement dans le liste
    private String remplacePlayerString(String str){
        String player1 = "";
        String player2 = "";
        String player3 = "";
        String player4 = "";

        if (str.contains("Player1")){
            player1 = pickRandomPlayer();
            str = str.replace("Player1", player1);
        }
        if (str.contains("Player2")){
            do {
                player2 = pickRandomPlayer();
            }while (player2 == player1);
            str = str.replace("Player2", player2);
        }
        if (str.contains("Player3")){
            do {
                player3 = pickRandomPlayer();
            }while (player3 == player1 || player3 == player2);
            str = str.replace("Player3", player3);
        }
        if (str.contains("Player4")){
            do {
                player4 = pickRandomPlayer();
            }while (player4 == player3 || player4 == player2 || player4 == player1);
            str = str.replace("Player4", player4);
        }


        return str;
    }
}
