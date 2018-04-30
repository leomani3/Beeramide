package com.example.leove.beeramide;

import android.content.Intent;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Liste de règles
    ArrayList<Rule> rules = new ArrayList<Rule>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("ruleList")){
            rules = (ArrayList<Rule>) getIntent().getSerializableExtra("ruleList");
        }


        //Reference à tous les widget
        ListView lv = findViewById(R.id.player_list);
        ImageButton addPlayerBt = findViewById(R.id.add_player);
        Button startGameBt = findViewById(R.id.start_game);

        Button addRuleBt = findViewById((R.id.go_add_rule));

        final NumberPicker numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(50);
        //final TextView nbTurns = findViewById(R.id.nb_turns);
        final EditText editText = findViewById(R.id.player_name);

        //Création de la list de player
        final ArrayList<String> playerList = new ArrayList<String>();
        //Attache à l'ArrayAdapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.player_list_adapter, R.id.textView, playerList);
        lv.setAdapter(arrayAdapter);

        if(rules.size() == 0 ){
            fillRules();
        }

        //Add player
        addPlayerBt.setOnClickListener(new View.OnClickListener(){

            public  void onClick(View view){
                if (editText.getText().length() > 0 && !editText.getText().toString().contains(" ")){
                    playerList.add(editText.getText().toString());
                    editText.setText("");
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        //Start game
        startGameBt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(playerList.size()>1 && numberPicker.getValue()>0)
                {
                    Intent intent = new Intent(MainActivity.this, TakePhotoActivity.class);
                    intent.putStringArrayListExtra("playerList", playerList);
                    intent.putExtra("gameMoment", 0);
                    intent.putExtra("nbTurns",numberPicker.getValue());
                    intent.putExtra("ruleList", rules);
                    startActivity(intent);
                }
            }
        });

        addRuleBt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, AddRuleActivity.class);
                intent.putExtra("ruleList", rules);
                startActivity(intent);
            }
        });


    }

    private void fillRules(){
        rules.add(new Rule("regle 1", true, Rule.ruleType.CUSTOM));
        rules.add(new Rule("regle 2", true, Rule.ruleType.CUSTOM));
        rules.add(new Rule("regle 3", true, Rule.ruleType.CUSTOM));
        rules.add(new Rule("regle 4", true, Rule.ruleType.CUSTOM));
    }
}
