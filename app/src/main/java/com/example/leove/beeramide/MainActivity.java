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
import android.widget.Toast;

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
        numberPicker.setMinValue(1);
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
                else{
                    Toast toast = Toast.makeText(MainActivity.this, "Caratères non valides", Toast.LENGTH_SHORT);
                    toast.show();
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
                else{
                    Toast toast = Toast.makeText(MainActivity.this, "Il faut au moins deux joueurs", Toast.LENGTH_SHORT);
                    toast.show();
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
        rules.add(new Rule("Player1 poste une phrase débile sur Facebook contenant les mots <ressembler>, <peau morte>, <Clara Morgane> et <Player2> ou finis ton verre.", true, Rule.ruleType.ACTION));
        rules.add(new Rule("Player1 va nous faire le plaisir de finir son verre ...", true, Rule.ruleType.CULSEC));
        rules.add(new Rule("Player1 doit maintenant parler avec l'accent chinois ", false, Rule.ruleType.VIRUS));
        rules.add(new Rule("Player1 , si tu as déjà eu un trou noir à cause de l'alcool, tu bois 5 gorgées", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 , interdiction de boire dans ton verre. Tu devras choisir celui d'une autre personneà chaque tour", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Les binoclards boivent 3 gorgée(s)", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 choisit un mot interdit, la personne qui le prononce doit boire une gorgée, jusqu'à nouvel ordre ", false, Rule.ruleType.STANDART));
        rules.add(new Rule("Le joueur avec le plus d'étude distribue 4 gorgées", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 est le pilier de bar, à chaque fois que tu bois tu dois trinquer avce quelqu'un qui te suivra sur autant de gorgées", true, Rule.ruleType.VIRUS));
        rules.add(new Rule("Plutôt Star Trek ou Star Wars ? Votez tous en même temps, les minoritaires boivent 3 gorgées !", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 donne 3 gorgées au joueur que tu connais le plus", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 , le Ruskov ! Da Da ! Garde l'accent russe jusqu'à nouvel ordre, 2 gorgées à chaque manque ", false, Rule.ruleType.VIRUS));
        rules.add(new Rule("Les joueurs qui ont un M dans leur prénom, cul sec !", true, Rule.ruleType.CULSEC));
        rules.add(new Rule("Player1 , distribue autant de gorgées que tu portes de vêtements rouges. Bois 2 gorgées si tu n'en a pas ", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 et Player2 échangent leurs verres", true, Rule.ruleType.VIRUS));
        rules.add(new Rule("Elisez celui ou celle qui a l'habitude de s'endormir avant tout le monde en soirée, cette personne prendre 3 gorgées", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Ceux qui sont plus grands que Player1 boivent leur trop de plein centimètre en gorgées", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 si tu sors 3 titres de chanson de Christophe Maé distribue 2 gorgées. Sinon bois-les", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 distribue autant de gorgées qu'il a de frères et soeurs !", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 bois autant de gorgées que le nombre de partenaire avec qui elle/il a couché dans la pièce", true, Rule.ruleType.VERITE));
        rules.add(new Rule("Nouvelle règle ! On peut uniquement s'adresser à quelqu'un d'autre par une question !", true, Rule.ruleType.VIRUS));
        rules.add(new Rule("Les choses qu'on peut trouver dans une bouche d'enfant, le joueur qui répète ou ne trouve pas prend 5 gorgées. Player1 commence", true, Rule.ruleType.ACTION));
        rules.add(new Rule("Bois 2 gorgées si tu as parmi tes potes un ou une ex", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Player1 est marié(e) à Player2, ils/elles ont le même nombre de gorgées", false, Rule.ruleType.VIRUS));
        rules.add(new Rule("Player1 bois 4 gorgées. Le voisin de droite une de moins, ainsi de suite jusqu'à arriver à 0 ", true, Rule.ruleType.ACTION));
        rules.add(new Rule("Player1 pose une question. La première personne à répondre distribue 2 gorgées", true, Rule.ruleType.ACTION));
        rules.add(new Rule("Player1 envoie <je t'aime> par texto (sans rien envoyer derrière) à une personne chosir par Player2, ou boit 3 gorgées", true, Rule.ruleType.ACTION));
        rules.add(new Rule("Tous ceux dont le prénom comporte un e boivent 3 gorgées", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Distribue 3 gorgées si tu t'es fait(e) larger plus de 4 fois ", true, Rule.ruleType.STANDART));
        rules.add(new Rule("Le/La joueuse avce le verre le plus alcoolisé boit 4 gorgées", true, Rule.ruleType.STANDART));
        rules.add(new Rule("la personne à gauche de Player1 boit 3 gorgées", true, Rule.ruleType.CUSTOM));
        rules.add(new Rule("Player1 choisis un objet dans la pièce. le premier joueur à toucher l'objet distribuera 4 gorgées", true, Rule.ruleType.CUSTOM));
        rules.add(new Rule("Enoncez à tour de rôle un slogan de publicité, Le joueur qui n'a plus d'idée boit 4 gorgées", true, Rule.ruleType.STANDART));
    }
}
