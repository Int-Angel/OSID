package com.example.osid.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.osid.GLOBAL.GLOBAL;
import com.example.osid.POJOs.Glucose;
import com.example.osid.POJOs.Insuline;
import com.example.osid.POJOs.User;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBCONTROLLER {
    private DBCREATOR dbcreator;

    public DBCONTROLLER(Context context){
        dbcreator = new DBCREATOR(context);
    }

    public long InsertUser(User newUser){
        SQLiteDatabase database = dbcreator.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Nombre",newUser.getNombre());
        contentValues.put("PrimerApellido",newUser.getPrimerApellido());
        contentValues.put("SegundoApellido",newUser.getSegundoApellido());
        contentValues.put("Edad",newUser.getEdad());
        contentValues.put("Peso",newUser.getPeso());
        contentValues.put("Basal",newUser.getBasal());
        contentValues.put("Genero",newUser.isGender());

        return database.insert(dbcreator.TABLA_USUARIO, null, contentValues);
    }

    public long InsertGlucose(Glucose newGlucose){
        SQLiteDatabase database = dbcreator.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("FechaHora",new SimpleDateFormat(GLOBAL.DATE_FORMAT).format(newGlucose.getFecha()));
        contentValues.put("Glucosa",newGlucose.getGlucose());

        return database.insert(dbcreator.TABLA_USUARIO, null, contentValues);
    }

    public long InsertInsuline(Insuline newInsuline){
        SQLiteDatabase database = dbcreator.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("FechaHora",new SimpleDateFormat(GLOBAL.DATE_FORMAT).format(newInsuline.getFecha()));
        contentValues.put("Glucosa",newInsuline.getInsuline());

        return database.insert(dbcreator.TABLA_USUARIO, null, contentValues);
    }

    public ArrayList<Glucose> GetArrayGlucose(){
        ArrayList<Glucose> glucoseArrayList = new ArrayList<>();

        SQLiteDatabase database = dbcreator.getReadableDatabase();

        String[] columnasAConsultar = {"Id_Glucose","FechaHora","Glucosa"};

        Cursor cursor = database.query(
                dbcreator.TABLA_GLUCOSE,
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor == null){
            //hubo un error, regresa lista vacia
            return glucoseArrayList;
        }

        if(!cursor.moveToFirst()){
            //no hay datos y la lista se queda vacia
            return glucoseArrayList;
        }

        do{

            Glucose x = new Glucose(
                    cursor.getInt(0),    // Id
                    stringToDate(cursor.getString(1), GLOBAL.DATE_FORMAT), // Fecha
                    cursor.getInt(2)    // Glucose
            );
            glucoseArrayList.add(x);
        }while(cursor.moveToNext());

        cursor.close();
        return glucoseArrayList;
    }

    public ArrayList<Insuline> GetArrayInsuline(){
        ArrayList<Insuline> insulineArrayList = new ArrayList<>();

        SQLiteDatabase database = dbcreator.getReadableDatabase();

        String[] columnasAConsultar = {"Id_Insuline","FechaHora","Insuline"};

        Cursor cursor = database.query(
                dbcreator.TABLA_INSULINE,
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor == null){
            //hubo un error, regresa lista vacia
            return insulineArrayList;
        }

        if(!cursor.moveToFirst()){
            //no hay datos y la lista se queda vacia
            return insulineArrayList;
        }

        do{

            Insuline x = new Insuline(
                    cursor.getInt(0),    // Id
                    stringToDate(cursor.getString(1), GLOBAL.DATE_FORMAT), // Fecha
                    cursor.getFloat(2)    // Glucose
            );
            insulineArrayList.add(x);
        }while(cursor.moveToNext());

        cursor.close();
        return insulineArrayList;
    }

    public User getUser(){
       // ArrayList<Insuline> insulineArrayList = new ArrayList<>();

        //User tempUser = new User();

        SQLiteDatabase database = dbcreator.getReadableDatabase();

        String[] columnasAConsultar = {"Id_Usuario",
                "Nombre",
                "PrimerApellido",
                "SegundoApellido",
                "Edad",
                "Peso",
                "Basal",
                "Genero"
        };

        Cursor cursor = database.query(
                dbcreator.TABLA_INSULINE,
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor == null){
            //hubo un error, regresa lista vacia
            return null;
        }

        if(!cursor.moveToFirst()){
            //no hay datos y la lista se queda vacia
            return null;
        }

        do{

            User user = new User(
                    cursor.getInt(0),    // Id
                    cursor.getString(1), // Nombre
                    cursor.getString(2),    // primer apellido
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getFloat(5),
                    cursor.getFloat(6),
                    cursor.getInt(7)>0
            );
            cursor.close();
            return user;

        }while(cursor.moveToNext());

        //cursor.close();
       // return tempUser;
    }

    private Date stringToDate(String aDate, String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
}
