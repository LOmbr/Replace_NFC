package com.example.ombr_ronan.nfc_replacer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ombr_ronan.nfc_replacer.BDD;

public class AjoutActivity extends AppCompatActivity {

    Button btnAdd;
    EditText nom;
    String tag;

    BDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        // Initialisation connexion BDD
        bdd = new BDD(this);
        bdd.open();

        // On déclare nos blocs
        btnAdd = findViewById(R.id.buttonAjouter);
        nom = findViewById(R.id.nom);

        tag = getIntent().getStringExtra("TAG");

    }

    public void creerNFC(String name) {
        // On créé notre nouvel étudiant
        bdd.add(name, tag);
        afficherMessage("Tag add");
        this.finish();
    }

    public void ajouter(View v) {
        String textNom = nom.getText().toString();

        if(textNom.equals("")) {
            afficherMessage("Veuillez renseigner un nom");
        }
        else {
            creerNFC(textNom);
        }
    }

    /********************* VIE *********************/
    public void onBackPressed() {
        this.finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        bdd.close();
    }
    /***********************************************/

    public void afficherMessage(String s) { Toast.makeText(this, s ,Toast.LENGTH_LONG).show(); }
}
