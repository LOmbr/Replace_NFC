package com.example.ombr_ronan.nfc_replacer;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

public class Emetteur extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    NdefRecord record;
    BDD bdd;
    ArrayList<NFC> list;
    LinearLayout origin;
    String tag;
    int id, select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emetteur);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        record = null;
        origin = findViewById(R.id.liste);

        bdd = new BDD(this);
        bdd.open();
        list = bdd.getAll();

        select = -1;

        if (nfcAdapter != null) {

            if(nfcAdapter.isEnabled()) {
                if(list != null && !list.isEmpty()) {
                    initAffichage();
                }
                else {
                    TextView tv = new TextView(this);
                    tv.setText("Aucun tag disponible");
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    origin.addView(tv);

                }
            }
            else
            {
                this.finish();
                Intent intent2 = new Intent(this, CheckNFC.class);
                startActivity(intent2);
            }
        }
        else
        {
            this.finish();
            Intent intent2 = new Intent(this, CheckNFC.class);
            startActivity(intent2);
        }


    }

    private void initAffichage() {


        //Cas de rafraichissage de la page
        if(origin.getChildCount() > 0)
            origin.removeAllViews();

        //Définition des paramètres des futur boutons
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = 50;
        params.height = 50;

        for(NFC nfc : list) {
            LinearLayout line = new LinearLayout(this);
            line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            line.setOrientation(LinearLayout.HORIZONTAL);

            EditText name = new EditText(this);
            name.setText(nfc.getName());
            line.addView(name);


            tag = nfc.getTag();
            id = nfc.getId();


            if(select != id) {
                Button activate = new Button(this);
                activate.setBackgroundResource(R.drawable.ok);
                activate.setLayoutParams(params);
                activate.setOnClickListener(new View.OnClickListener() {

                    String info = tag;
                    int num = id;

                    @Override
                    public void onClick(View v) {
                        select = num;
                        selectEmission(info);
                        initAffichage();
                    }
                });
                line.addView(activate);
            }
            else {
                TextView selected = new TextView(this);
                selected.setBackgroundResource(R.drawable.nfclogo);
                selected.setLayoutParams(params);
                line.addView(selected);
            }

            origin.addView(line);
        }

    }

    private void selectEmission(String message) {
        record = creerRecord(message);
    }

    /********************* NFC *********************/
    NdefRecord creerRecord(String message) {
        byte[] langBytes = Locale.FRANCE.getLanguage().getBytes(Charset.forName("US-ASCII"));
        byte[] textBytes = message.getBytes(Charset.forName("UTF-8"));
        char status = (char) (langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    NdefMessage creerMessage(NdefRecord record) {
        NdefRecord[] records = new NdefRecord[1];
        records[0] = record;
        NdefMessage message = new NdefMessage(records);
        return message;
    }

    /***********************************************/
    /********************* VIE *********************/
    @Override
    public void onBackPressed() {
        bdd.close();
        nfcAdapter.setNdefPushMessage(null, this,this);
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bdd.close();
        nfcAdapter.setNdefPushMessage(null, this,this);
    }

    @Override
    protected  void onDestroy() {
        super.onDestroy();
        bdd.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //NdefRecord record = creerRecord("Exemple de message à envoyer");

        if(record != null) {
            NdefMessage message = creerMessage(record);
            nfcAdapter.setNdefPushMessage(message, this, this);
        }
    }
    /***********************************************/
}
