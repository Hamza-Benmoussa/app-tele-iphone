package basedonnées;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.myapplication.appel;
import com.example.myapplication.favor;
import com.example.myapplication.listcontact;

import java.util.ArrayList;

public class bdd2 extends SQLiteOpenHelper {
    private static final String nbd = "basedonee";
    private static final String nom_table = "contact";
    private static final String idc = "id";
    private static final String nomc = "nom";
    private static final String prenom = "prenom";
    private static final String numero = "numero";
    private static final String email = "email";
    private static final String image = "image";
    private static final String adresse = "adresse";
    private static final String nom_table2 = "appel";
    private static final String id_appel = "id_appel";
    private static final String nom_table3 = "favori";
    private static final String idfv = "idfv";
    private static final String idcv = "idcv";
    private  static  final  String idco ="idco";
    private  static final  String date ="datex";
    private static  final String  numx ="num_x";

    public bdd2(Context context) {
        super(context, nbd, null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + nom_table + "("+
                idc + " INTEGER PRIMARY KEY, " +
                nomc + " TEXT," +
                prenom + " TEXT," +
                numero +" Text,"+
                 numx + " Text ,"+
                email+" Text,"+
                image + " Blob,"+
                adresse+" Text)";
        String appel ="CREATE TABLE " + nom_table2 + " ( " + id_appel + "  INTEGER Primary Key AUTOINCREMENT ," + nomc + "  Text ," + prenom + "  Text ," + date + " TEXT ," + numero + " TEXT , " + idco + " INTEGER , FOREIGN KEY( " + idco + ") REFERENCES " + nom_table + '(' + idc + ") );";

        String fav = "CREATE TABLE " + nom_table3 + " ( " + idfv + " INTEGER Primary Key AUTOINCREMENT ," + idcv + " INTEGER , " + "FOREIGN KEY (" + idcv + ") REFERENCES " + nom_table + "(" + idc + ") ); ";


        db.execSQL(create);
        db.execSQL(appel);
        db.execSQL(fav);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion) {
        db.execSQL("DROP TABLE  IF EXISTS " + nom_table);
        db.execSQL("DROP TABLE  IF EXISTS " + nom_table2);
        db.execSQL("DROP TABLE  IF EXISTS " + nom_table3);
        onCreate(db);
    }

    public void insert(
            String nom, String prenoma, String tele, String emailx, String adressec, byte[] img , String nume
    ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(nomc,nom);
        content.put(prenom,prenoma);
        content.put(numero ,tele);
        content.put(email,emailx);
        content.put(adresse,adressec);
        content.put(image,img);
        content.put(numx,nume);
        db.insert(nom_table,null,content);
        db.close();
    }


    public ArrayList<listcontact> affiche() {
        ArrayList<listcontact> list = new ArrayList<>();
        int id;
        String nom;
        String prenomc;
        String telec;
        String emailc;
        String adrss;
        byte[] imagec;
        String nume ;

        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + nom_table;
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(idc));
                nom = cursor.getString(cursor.getColumnIndexOrThrow(nomc));
                prenomc = cursor.getString(cursor.getColumnIndexOrThrow(prenom));
                telec = cursor.getString(cursor.getColumnIndexOrThrow(numero));
                adrss = cursor.getString(cursor.getColumnIndexOrThrow(adresse));
                emailc = cursor.getString(cursor.getColumnIndexOrThrow(email));
                imagec = cursor.getBlob(cursor.getColumnIndexOrThrow(image));
nume =cursor.getString(cursor.getColumnIndexOrThrow(numx));
                listcontact fin = new listcontact(nom, prenomc, telec, emailc, adrss, imagec, id,nume);
                list.add(fin);
            } while(cursor.moveToNext());
        }

        return list;
    }

    public void aff(int ide) {
        int id;
        String nom;
        String prenomc;
        String telec;
        String emailc;
        String adrss;
        byte[] imagec;

        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + nom_table + " WHERE " + idc + " = " + ide;
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(idc));
                nom = cursor.getString(cursor.getColumnIndexOrThrow(nomc));
                prenomc = cursor.getString(cursor.getColumnIndexOrThrow(prenom));
                telec = cursor.getString(cursor.getColumnIndexOrThrow(numero));
                adrss = cursor.getString(cursor.getColumnIndexOrThrow(adresse));
                emailc = cursor.getString(cursor.getColumnIndexOrThrow(email));
                imagec = cursor.getBlob(cursor.getColumnIndexOrThrow(image));
               String nume =cursor.getString(cursor.getColumnIndexOrThrow(numx));

                listcontact fin = new listcontact(nom, prenomc, telec, emailc, adrss, imagec, id,nume);
            } while(cursor.moveToNext());
        }
    }

    public void supprimer(int ide) {
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "DELETE FROM " + nom_table + " WHERE " + idc + " = " + ide;
        db.execSQL(req);
        db.close();
    }

    public void modifier(int idm, String nom, String prenoma, String tele, String emailx, String adressec, byte[] img,String vnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(nomc, nom);
        value.put(prenom, prenoma);
        value.put(numero, tele);
        value.put(email, emailx);
        value.put(adresse, adressec);
        value.put(image, img);
        value.put(numx,vnum);

        db.update(nom_table, value, idc + " = ?", new String[] { String.valueOf(idm) });
        db.close();
    }


    public void inserer(String dt, int ide, String numeroz,String  name ,String prenoma) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(date, dt);
        value.put(idco, ide);
        value.put(numero, numeroz);
   value.put(prenom,prenoma);
   value.put(nomc,name);
        db.insert(nom_table2, null, value);
        db.close();
    }

    public ArrayList<appel> affi() {
        String nomy, pren, datey, num ;
        int id , idcox;
        ArrayList<appel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String str = "SELECT  " + id_appel + ',' + nom_table2 + '.' + date + ',' + nom_table2 + '.' + numero + ',' + nom_table + '.' + nomc + ',' + nom_table + '.' + prenom + " , " + nom_table2 + '.' + idco + " FROM " + nom_table + ',' + nom_table2 + " WHERE " + nom_table + '.' + idc + " = " + nom_table2 + '.' + idco + "  OR " + nom_table2 + '.' + idco + " = 0 ORDER BY " + id_appel + " DESC";
        Cursor cursor = db.rawQuery(str, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(id_appel));
                nomy = cursor.getString(cursor.getColumnIndexOrThrow(nomc));
                pren = cursor.getString(cursor.getColumnIndexOrThrow(prenom));
                datey = cursor.getString(cursor.getColumnIndexOrThrow(date));
                num = cursor.getString(cursor.getColumnIndexOrThrow(numero));
                idcox =cursor.getInt(cursor.getColumnIndexOrThrow(idco));
                list.add(new appel(id,datey,nomy,num,pren,idcox));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }



    public void inserx(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(idcv, id);
        db.insert(nom_table3, null, values);
        db.close();
    }

    public ArrayList<favor> afferx() {
        String nomf, prenomf;
        int id;
        ArrayList<favor> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT DISTINCT " + nom_table + "." + nomc + "," + nom_table + "." + prenom + "," +
                nom_table3 + "." + idfv + " FROM " + nom_table3 + "," + nom_table +
                " WHERE " + nom_table3 + "." + idcv + " = " + nom_table + "." + idc + " GROUP BY " +
                nom_table + "." + nomc + "," + nom_table + "." + prenom;

        Cursor cursor = db.rawQuery(req, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(idfv));
                nomf = cursor.getString(cursor.getColumnIndexOrThrow(nomc));
                prenomf = cursor.getString(cursor.getColumnIndexOrThrow(prenom));
                list.add(new favor(id, nomf, prenomf));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }


    public void inser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(idcv, id);
        db.insert(nom_table3, null, values);
        db.close();
    }

    public ArrayList<favor> affer(){
        ArrayList<favor> list = new ArrayList<>();
        String nomf;
        String prenomf;
        int id;
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT DISTINCT " + nom_table + "." + nomc + "," + nom_table + "." + prenom + "," + nom_table3 + "." + idfv + " FROM " + nom_table3 + "," + nom_table + " WHERE " + nom_table3 + "." + idcv + " = " + nom_table + "." + idc + " GROUP BY " + nom_table + "." + nomc + "," + nom_table + "." + prenom;

        Cursor cursor = db.rawQuery(req, null);
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow(idfv));
                nomf = cursor.getString(cursor.getColumnIndexOrThrow(nomc));
                prenomf = cursor.getString(cursor.getColumnIndexOrThrow(prenom));
                list.add(new favor(id, nomf, prenomf));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void deletefav(int ide){
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "DELETE FROM " + nom_table3 + " WHERE " + idfv + " = " + ide;
        db.execSQL(req);
        db.close();
    }
    public void delete(int ide) {
        SQLiteDatabase db = this.getWritableDatabase();
        String req = "DELETE FROM " + nom_table2 + " WHERE " + id_appel + " = " + ide;
        db.execSQL(req);
        db.close();
    }

}