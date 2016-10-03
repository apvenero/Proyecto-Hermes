package lab.comunicador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class BD extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ComunicadorDB";
    // Database Tables
    private static final String TABLE_ALUMNOS = "Alumno";
    private static final String[] COLUMNS_ALUMNOS = {"id","nombre","apellido","pages","tamanio","sexo","pictogramas"};

    private static final String TABLE_PICTOGRAMA = "Pictograma";
    private static final String TABLE_NOTIFICACION = "Notificacion";
    private static final String TABLE_CONFIGURACION = "Configuracion";

    public BD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_ALUMNOS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nombre TEXT NOT NULL, apellido TEXT NOT NULL, pages BLOB, tamanio TEXT , sexo TEXT, pictogramas BLOB)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PICTOGRAMA +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre TEXT, carpeta TEXT)");

        db.execSQL("CREATE TABLE Configuracion (id INTEGER PRIMARY KEY AUTOINCREMENT, ip TEXT NOT NULL, puerto TEXT NOT NULL )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICACION +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, nombreNinio TEXT, contenido TEXT, categoria TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CONFIGURACION +
                "(id INTEGER PRIMARY KEY, ip TEXT, puerto TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Alumno");
        db.execSQL("DROP TABLE IF EXISTS Pictograma");
        db.execSQL("DROP TABLE IF EXISTS Configuracion");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTOGRAMA);
        onCreate(db);
    }


    public void agregarAlumno(Alumno alumno){
        SQLiteDatabase db = this.getWritableDatabase();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ContentValues values = new ContentValues();
        values.put("nombre", alumno.getNombre());
        values.put("apellido", alumno.getApellido());
        values.put("pages", gson.toJson(alumno.getPages()));
        values.put("tamanio", alumno.getTamanio());
        values.put("sexo", alumno.getSexo());
        values.put("pictogramas", gson.toJson(alumno.getPictogramas()));
        db.insert(TABLE_ALUMNOS, null, values);
        db.close();
    }

    public List<Alumno> getAllAlumnos(){
        List<Alumno> alumnos = new ArrayList<Alumno>();

        String query = "SELECT  * FROM " + TABLE_ALUMNOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Alumno al=null;

        if (cursor.moveToFirst()){
            do {
                al = new Alumno();
                al.setId(cursor.getInt(0));
                al.setNombre(cursor.getString(1));
                al.setApellido(cursor.getString(2));
                al.setSexo(cursor.getString(5));
                al.setTamanio(cursor.getString(4));
                Type type = new TypeToken<ArrayList<String>>(){}.getType();
                al.setPages((ArrayList<Integer>)gson.fromJson(cursor.getString(3).toString(), type));

                al.setPictogramas((ArrayList<String>) gson.fromJson(cursor.getString(6).toString(), type));
                alumnos.add(al);
            }while (cursor.moveToNext());
        }
        return alumnos;
    }

    public Alumno getAlumno(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_ALUMNOS,
                        COLUMNS_ALUMNOS,
                        "id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);
        if (cursor != null)
            cursor.moveToFirst();

        Alumno al = new Alumno();
        al.setId(cursor.getInt(0));
        al.setNombre(cursor.getString(1));
        al.setApellido(cursor.getString(2));
        al.setSexo(cursor.getString(5));
        al.setTamanio(cursor.getString(4));

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        al.setPages((ArrayList<Integer>) gson.fromJson(cursor.getString(3).toString(), type));
        Type type2 = new TypeToken<ArrayList<String>>() {
        }.getType();
        al.setPictogramas((ArrayList<String>) gson.fromJson(cursor.getString(6).toString(), type2));
        return al;
    }



    public int editarAlumno(Alumno alumno){

        SQLiteDatabase db = this.getWritableDatabase();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ContentValues values = new ContentValues();
        values.put("nombre", alumno.getNombre());
        values.put("apellido", alumno.getApellido());
        values.put("pages", gson.toJson(alumno.getPages()));
        values.put("tamanio",alumno.getTamanio());
        values.put("sexo", alumno.getSexo());
        values.put("pictogramas", gson.toJson(alumno.getPictogramas()));
        int i = db.update(TABLE_ALUMNOS,
                values,
                "id = ?",
                new String[]{String.valueOf(alumno.getId())});
        db.close();
        return i;
    }

    public void borrarAlumno(Alumno alumno){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ALUMNOS,
                "id = ?",
                new String[]{String.valueOf(alumno.getId())});
        db.close();
    }



    public void agregarNotificacion(String nombreNinio, String contenido, String categoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreNinio", nombreNinio);
        values.put("contenido", contenido);
        values.put("categoria", categoria);
        db.insert(TABLE_NOTIFICACION, null, values);
        db.close();
    }

    public List<Notificacion> getAllNotificaciones(){
        List<Notificacion> notificaciones = new ArrayList<Notificacion>();
        String query = "SELECT  * FROM " + TABLE_NOTIFICACION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Notificacion no=null;

        if (cursor.moveToFirst()){
            do {
                no = new Notificacion();
                no.setId(cursor.getInt(0));
                no.setNombreNinio(cursor.getString(1));
                no.setContenido(cursor.getString(2));
                no.setCategoria(cursor.getString(3));


                notificaciones.add(no);
            }while (cursor.moveToNext());
        }
        return notificaciones;
    }

    public void borrarNotificaciones(List<Notificacion> lista) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Notificacion notification :lista) {
            db.delete(TABLE_NOTIFICACION, "id='"+ notification.getId() +"'", null);
        }
        db.close();
    }

    public Configuracion getConfiguracion() {
        String query = "SELECT * FROM " + TABLE_CONFIGURACION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Configuracion config=null;
        if (cursor.moveToFirst()){
            do {
                config = new Configuracion();
                config.setIp(cursor.getString(1));
                config.setPuerto(cursor.getString(2));
            }while (cursor.moveToNext());
        }
        return config;
    }

    public void agregarConfiguracion(String ip, String puerto){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("ip", ip);
            values.put("puerto", puerto);
            db.insert("configuracion", null, values);
            db.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateConfiguracion(String ip, String puerto){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put("ip", ip);
            values.put("puerto", puerto);
            db.update("configuracion", values, "id = 1", null);
            db.close();
        }
    }


}
