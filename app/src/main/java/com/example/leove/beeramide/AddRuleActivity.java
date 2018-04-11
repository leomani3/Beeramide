package com.example.leove.beeramide;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by corentin on 11/04/2018.
 */

public class AddRuleActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rule_activity);

        //Reference à tous les widget
        ListView lv = findViewById(R.id.rule_list);
        Button addRuleBt = findViewById(R.id.add_rule);
        Button Retour = findViewById(R.id.retour);
        final EditText editText = findViewById(R.id.newRule);

        //Création de la list de player
        final ArrayList<String> RuleList = new ArrayList<String>();
        //Attache à l'ArrayAdapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.rule_list_adapter, R.id.textViewRule, RuleList);
        lv.setAdapter(arrayAdapter);

        //Add player
        addRuleBt.setOnClickListener(new View.OnClickListener(){

            public  void onClick(View view){
                RuleList.add(editText.getText().toString());
                editText.setText("");
                arrayAdapter.notifyDataSetChanged();
            }
        });

        //Start game
        /*startGameBt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(AddRuleActivity.this, TakePhotoActivity.class);
                intent.putStringArrayListExtra("playerList", playerList);
                intent.putExtra("gameMoment", "before");
                startActivity(intent);
            }
        });*/

    }
}
