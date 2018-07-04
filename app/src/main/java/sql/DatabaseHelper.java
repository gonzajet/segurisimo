package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import model.Imangen;
import model.Siniestros;
import model.User;
import model.UserSiniestro;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 35;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // Tabla de usuario
    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_DNI = "dni";
    private static final String COLUMN_USER_PATENTE = "patente";
    private static final String COLUMN_USER_SEGURO = "seguro";
    private static final String COLUMN_USER_TELEFONO = "telefono";
    private static final String COLUMN_USER_POLIZA = "poliza";
    private static final String COLUMN_USER_DIRECCION = "direccion";
    private static final String COLUMN_USER_VALIDO = "valido";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_PATENTE + " TEXT,"
            + COLUMN_USER_SEGURO + " TEXT,"
            + COLUMN_USER_DIRECCION + " TEXT,"
            + COLUMN_USER_VALIDO + " BOOLEAN,"
            + COLUMN_USER_TELEFONO + " INTEGER,"
            + COLUMN_USER_POLIZA + " INTEGER,"
            + COLUMN_USER_DNI + " INTEGER)";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    //siniestro
    private static final String TABLE_SINIESTRO = "siniestro";

    // User Table Columns names
    private static final String COLUMN_SINIESTRO_ID = "id";
    private static final String COLUMN_SINIESTRO_LAT = "lat";
    private static final String COLUMN_SINIESTRO_LON = "lon";
    private static final String COLUMN_SINIESTRO_USER_ID = "user_id";
    private static final String COLUMN_SINIESTRO_ESTADO = "estado";
    private static final String COLUMN_SINIESTRO_FECHA = "fecha";
    private static final String COLUMN_SINIESTRO_HORA = "hora";

    // create table sql query
    private String CREATE_SINIESTRO_TABLE = "CREATE TABLE " + TABLE_SINIESTRO + "("
            + COLUMN_SINIESTRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SINIESTRO_LAT + " DOUBLE,"
            + COLUMN_SINIESTRO_LON + " DOUBLE,"
            + COLUMN_SINIESTRO_ESTADO + " TEXT,"
            + COLUMN_SINIESTRO_FECHA + " TEXT,"
            + COLUMN_SINIESTRO_HORA + " TEXT,"
            + COLUMN_SINIESTRO_USER_ID + " INTEGER" + ")";

    // drop table sql query
    private String DROP_SINIESTRO_TABLE = "DROP TABLE IF EXISTS " + TABLE_SINIESTRO;

    //Imagenes
    private static final String TABLE_IMAGEN = "imagen";

    // User Table Columns names
    private static final String COLUMN_IMAGEN_ID = "id";
    private static final String COLUMN_IMAGEN_SINIESTRO_ID = "siniestro_id";
    private static final String COLUMN_IMAGEN_PATH = "path";
    private static final String COLUMN_IMAGEN_NOMBRE = "nombre";

    // create table sql query
    private String CREATE_IMAGEN_TABLE = "CREATE TABLE " + TABLE_IMAGEN + "("
            + COLUMN_IMAGEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_IMAGEN_PATH + " TEXT,"
            + COLUMN_IMAGEN_NOMBRE + " TEXT,"
            + COLUMN_IMAGEN_SINIESTRO_ID + " INTEGER)";

    // drop table sql query
    private String DROP_IMAGEN_TABLE = "DROP TABLE IF EXISTS " + TABLE_IMAGEN;
    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_IMAGEN_TABLE);
        db.execSQL(CREATE_SINIESTRO_TABLE);
        db.execSQL("INSERT INTO "+TABLE_USER+" ("+COLUMN_USER_NAME+","
                +COLUMN_USER_PASSWORD+","
                +COLUMN_USER_EMAIL+","
                +COLUMN_USER_PATENTE+","
                +COLUMN_USER_SEGURO+","
                +COLUMN_USER_TELEFONO+","
                +COLUMN_USER_DIRECCION+","
                +COLUMN_USER_VALIDO+","
                +COLUMN_USER_POLIZA+","
                +COLUMN_USER_DNI+") "+
                "VALUES ('Matias', '123', 'test@test.com', 'aaa000', 'credicoop', '42774756','MAGARITA 122','1','123','12345')," +
                "('Gonza', '123', 'test1@test.com', 'aaa001', 'lacaja', '42775566','ESMERALDA 123','1','321','54321')," +
                "('Duznarito', '123', 'duznarito@test.com', 'aaa002', 'Pelon', '12345678','SIMPREVIVA 123','1','111','43210');"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_IMAGEN_TABLE);
        db.execSQL(DROP_SINIESTRO_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_VALIDO, "1");

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUserForSiniestro(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PATENTE, user.getPatente());
        values.put(COLUMN_USER_POLIZA, user.getPoliza());
        values.put(COLUMN_USER_SEGURO, user.getSeguro());
        values.put(COLUMN_USER_TELEFONO, user.getTelefono());
        values.put(COLUMN_USER_DIRECCION, user.getDireccion());
        values.put(COLUMN_USER_VALIDO, "0");


        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to create user record
     *
     * @param siniestros
     */
    public void addSiniestro(Siniestros siniestros) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SINIESTRO_USER_ID, siniestros.getUserId());
        values.put(COLUMN_SINIESTRO_ESTADO, "PENDIENTE");
        values.put(COLUMN_SINIESTRO_LAT, siniestros.getLat());
        values.put(COLUMN_SINIESTRO_LON, siniestros.getLon());
        values.put(COLUMN_SINIESTRO_FECHA, siniestros.getFecha());
        values.put(COLUMN_SINIESTRO_HORA, siniestros.getHora());
        // Inserting Row
        db.insert(TABLE_SINIESTRO, null, values);
        db.close();
    }

    public void addImage(Imangen imagen) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGEN_PATH, imagen.getPath());
        values.put(COLUMN_IMAGEN_SINIESTRO_ID, imagen.getSiniestro_id());
        // Inserting Row
        db.insert(TABLE_IMAGEN, null, values);
        db.close();
    }

    public int searchIdSiniestro(int userId, double lat, double lon) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_SINIESTRO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_SINIESTRO_USER_ID + " = ?" + " AND " + COLUMN_SINIESTRO_LAT + " = ?" + " AND " + COLUMN_SINIESTRO_LON + " = ?";

        // selection arguments
        String[] selectionArgs = {String.valueOf(userId), String.valueOf(lat), String.valueOf(lon)};

        Cursor cursor = db.query(TABLE_SINIESTRO, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        try {
            cursor.moveToFirst();

            Integer id = cursor.getInt(0);
            cursor.close();
            db.close();
            return id;
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public ArrayList<UserSiniestro> getAllSiniestros() {

        ArrayList<UserSiniestro> userList = new ArrayList<UserSiniestro>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        String query = "Select siniestro.id," +
                "siniestro.lat," +
                "siniestro.lon," +
                "siniestro.estado," +
                "siniestro.fecha," +
                "siniestro.hora," +
                "siniestro.user_id," +
                "user.name," +
                "user.email," +
                "user.patente," +
                "user.direccion," +
                "user.telefono," +
                "user.poliza " +
                "from siniestro " +
                "left join user on siniestro.user_id = user.user_id";
        Cursor cursor = db.rawQuery(query,null);

        // Traversing through all rows and adding to list
        while (cursor.moveToNext()) {
                UserSiniestro siniestro = new UserSiniestro();
                siniestro.setId(cursor.getInt(0));
                siniestro.setLat(cursor.getDouble(1));
                siniestro.setLon(cursor.getDouble(2));
                siniestro.setEstado(cursor.getString(3));
                siniestro.setFecha(cursor.getString(4));
                siniestro.setHora(cursor.getString(5));
                siniestro.setUser_id(cursor.getInt(6));
                siniestro.setName(cursor.getString(7));
                siniestro.setEmail(cursor.getString(8));
                siniestro.setPatente(cursor.getString(9));
                siniestro.setDireccion(cursor.getString(10));
                siniestro.setTelefono(cursor.getString(11));
                siniestro.setPoliza(cursor.getString(12));
                userList.add(siniestro);
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public ArrayList<UserSiniestro> getAllSiniestros(String patente,String estado,String fecha) {

        ArrayList<UserSiniestro> userList = new ArrayList<UserSiniestro>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Log.i("test",patente);
        Log.i("test",estado);
        String query = "Select siniestro.id," +
                "siniestro.lat," +
                "siniestro.lon," +
                "siniestro.estado," +
                "siniestro.fecha," +
                "siniestro.hora," +
                "siniestro.user_id," +
                "user.name," +
                "user.email," +
                "user.patente," +
                "user.direccion," +
                "user.telefono," +
                "user.poliza " +
                "from siniestro " +
                "left join user on siniestro.user_id = user.user_id";

        if(!patente.isEmpty() || !estado.isEmpty()  || !fecha.isEmpty()){
            query = query + " where ";

            if(!patente.isEmpty()){
                query = query + " user.patente = \""+patente+"\"";
            }
            if(!patente.isEmpty() && !estado.isEmpty()){
                query = query + " and ";
            }
            if(!estado.isEmpty()){
                query = query + " siniestro.estado = \""+ estado+"\"";
            }
            if(!patente.isEmpty() && !estado.isEmpty() && !fecha.isEmpty()){
                query = query + " and ";
            }
            if(!fecha.isEmpty()){
                query = query + " siniestro.fecha = \""+ fecha+"\"";
            }
        }
        
        Cursor cursor = db.rawQuery(query,null);
        try {
        // Traversing through all rows and adding to list
        while (cursor.moveToNext()) {
            UserSiniestro siniestro = new UserSiniestro();
            siniestro.setId(cursor.getInt(0));
            siniestro.setLat(cursor.getDouble(1));
            siniestro.setLon(cursor.getDouble(2));
            siniestro.setEstado(cursor.getString(3));
            siniestro.setFecha(cursor.getString(4));
            siniestro.setHora(cursor.getString(5));
            siniestro.setUser_id(cursor.getInt(6));
            siniestro.setName(cursor.getString(7));
            siniestro.setEmail(cursor.getString(8));
            siniestro.setPatente(cursor.getString(9));
            siniestro.setDireccion(cursor.getString(10));
            siniestro.setTelefono(cursor.getString(11));
            siniestro.setPoliza(cursor.getString(12));
            userList.add(siniestro);
        }
        cursor.close();
        db.close();
        }catch (Exception e){
            return userList;
        }

        // return user list
        return userList;
    }


    public ArrayList<Imangen> getAllImagen(int siniestroId) {

        ArrayList<Imangen> userList = new ArrayList<Imangen>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        String query = "Select path " +
                "from imagen " +
                "where siniestro_id = " + siniestroId;
        Cursor cursor = db.rawQuery(query,null);

        // Traversing through all rows and adding to list
        while (cursor.moveToNext()) {
            Imangen imagen = new Imangen();
            imagen.setPath(cursor.getString(0));
           ;
            userList.add(imagen);
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public String checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        try {
            cursor.moveToFirst();

            String id = cursor.getString(0);
            cursor.close();
            db.close();
            return id;
        }catch (Exception e){
            return "";
        }

    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */

    public User getUserForPatenteYPoliza(String patente, String poliza) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PATENTE,
                COLUMN_USER_SEGURO,
                COLUMN_USER_TELEFONO,
                COLUMN_USER_POLIZA,
                COLUMN_USER_DIRECCION,
                COLUMN_USER_VALIDO
        };


        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        String selection = COLUMN_USER_PATENTE + " = ?" + " AND " + COLUMN_USER_POLIZA + " = ?";

        String[] selectionArgs = {patente, poliza};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
//        try {
            User user = new User();
            if (cursor.moveToFirst()) {
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PATENTE)));
                user.setSeguro(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SEGURO)));
                user.setTelefono(cursor.getString(cursor.getColumnIndex(COLUMN_USER_TELEFONO)));
                user.setPoliza(cursor.getString(cursor.getColumnIndex(COLUMN_USER_POLIZA)));
                user.setDireccion(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DIRECCION)));
                user.setValido(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_VALIDO)));
            }

            cursor.close();
            db.close();
            return user;
//        }catch (Exception e){
//            return "";
//        }
    }
}
