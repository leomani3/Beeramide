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
import android.widget.Switch;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by corentin on 11/04/2018.
 */

public class AddRuleActivity extends AppCompatActivity {

    ArrayList<Rule> rules = new ArrayList<Rule>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rule_activity);



        //Reference à tous les widget
        ListView lv = findViewById(R.id.rule_list);
        Button addRuleBt = findViewById(R.id.add_rule);
        Button Retour = findViewById(R.id.retour);
        final Switch repeatableSwitch = findViewById(R.id.repeatSwitch);
        final EditText editText = findViewById(R.id.newRule);

        //liste des règles
        rules = (ArrayList<Rule>) getIntent().getSerializableExtra("ruleList");

        //Attache à l'ArrayAdapter
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.rule_list_adapter, R.id.textViewRule, rules);
        RuleAdapter arrayAdapter = new RuleAdapter(this, R.layout.rule_list_adapter, rules);
        lv.setAdapter(arrayAdapter);

        //Add rule
        addRuleBt.setOnClickListener(new View.OnClickListener(){

            public  void onClick(View view){
                if(editText.getText().length()>0)
                {
                    //rules.add(editText.getText().toString());
                    rules.add(new Rule(editText.getText().toString(), repeatableSwitch.isChecked(), Rule.ruleType.CUSTOM));
                    editText.setText("");
                    //arrayAdapter.notifyDataSetChanged();
                }

            }
        });

        //retour
        Retour.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(AddRuleActivity.this, MainActivity.class);
                //intent.putStringArrayListExtra("ruleList", rules);
                startActivity(intent);
            }
        });

    }
}
