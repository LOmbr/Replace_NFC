package com.example.ombr_ronan.nfc_replacer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class BDD {

    private static final int VERSION_BDD = 1;

    private SQLiteDatabase bdd;
    Database DB;

    //Définition du nom de la base de données
    private static final String TABLE = "table_nfc";

    //Définition des noms de colonnes de la base de données
    private static final String COL_ID = "_id";
    private static final String COL_NAME = "name";
    private static final String COL_TAG = "tag";

    //Définition des numéros de colonne de la base de données
    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NAME = 1;
    private static final int NUM_COL_TAG = 2;


    public BDD(Context context){
        DB = new Database(context, TABLE, null, VERSION_BDD);
    }

    //Ouvertire de la base de données
    public void open() {
        bdd = DB.getWritableDatabase();
        bdd.execSQL(DB.getCreate());
    }

    //Suppression de la base de données
    public void destroy() {
        DB.onUpgrade(bdd, VERSION_BDD, VERSION_BDD);
    }

    //Fermeture de la base de données
    public void close() {
        bdd.close();
    }

    //Récupération de la base de données
    public SQLiteDatabase getBDD() { return bdd; }


    //Ajout element
    public long add(NFC nfc) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, nfc.getName());
        values.put(COL_TAG, nfc.getTag());
        return bdd.insert(TABLE, null, values);
    }

    //Ajout paramètres
    public long add(String name, String tag) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_TAG, tag);
        return bdd.insert(TABLE, null, values);
    }

    //Convertion d'un curseur (NFC)
    @Nullable
    private NFC cursorToElement(Cursor c){
        if (c.getCount() == 0) return null;

        c.moveToFirst();

        NFC nfc = new NFC(
                c.getInt(NUM_COL_ID),
                c.getString(NUM_COL_NAME),
                c.getString(NUM_COL_TAG)
        );

        c.close();

        return nfc;
    }

    //Convertion d'un curseur (Array)
    @Nullable
    private ArrayList<NFC> cursorToElements(Cursor c) {
        if (c.getCount() == 0) return null;

        ArrayList<NFC> nfcs = new ArrayList<NFC>();

        c.moveToFirst();

        //Parcours du curseur
        while(!c.isAfterLast()) {
            NFC nfc = new NFC(
                    c.getInt(NUM_COL_ID),
                    c.getString(NUM_COL_NAME),
                    c.getString(NUM_COL_TAG)
            );

            nfcs.add(nfc);
            c.moveToNext();
        }

        c.close();

        return nfcs;
    }

    //Récupérer tout
    @Nullable
    public ArrayList<NFC> getAll() {
        Cursor c = bdd.query(TABLE, new String[] {"*"}, null, null, null, null, null);
        return cursorToElements(c);
    }

    //Récupérer 1
    public NFC getOne(int id) {
        Cursor c = bdd.query(TABLE, new String[] {"*"}, COL_ID + " = " + id, null, null, null, null);
        return cursorToElement(c);
    }

    //Mise à jour
    public int update(NFC nfc) {

        ContentValues values = new ContentValues();
        values.put(COL_ID, nfc.getId());
        values.put(COL_NAME, nfc.getName());
        values.put(COL_TAG, nfc.getTag());

        return bdd.update(TABLE, values, COL_ID + " = " + nfc.getId(), null);
    }

    //Suppression
    public boolean delete(NFC nfc) {
        return bdd.delete(TABLE, COL_ID + " = " + nfc.getId(), null) > 0;
    }

    //Suppression
    public boolean delete(int id) {
        return bdd.delete(TABLE, COL_ID + " = " + id, null) > 0;
    }

}
