package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


import model.User;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;

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

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_PATENTE + " TEXT,"
            + COLUMN_USER_SEGURO + " TEXT,"
            + COLUMN_USER_DIRECCION + " TEXT,"
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

    // create table sql query
    private String CREATE_SINIESTRO_TABLE = "CREATE TABLE " + TABLE_SINIESTRO + "("
            + COLUMN_SINIESTRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SINIESTRO_LAT + " DOUBLE,"
            + COLUMN_SINIESTRO_LON + " DOUBLE,"
            + COLUMN_SINIESTRO_ESTADO + " TEXT,"
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
                +COLUMN_USER_POLIZA+","
                +COLUMN_USER_DNI+") "+
                "VALUES ('Matias', '123', 'test@test.com', 'aaa000', 'credicoop', '42774756','37899555','12345')," +
                "('Gonza', '123', 'test1@test.com', 'aaa001', 'lacaja', '42775566','98653215','54321')," +
                "('Duznarito', '123', 'duznarito@test.com', 'aaa002', 'Pelon', '12345678','98653218','43210');"
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

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
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
                COLUMN_USER_DIRECCION
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
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SEGURO)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_TELEFONO)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_POLIZA)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DIRECCION)));
            }

            cursor.close();
            db.close();
            return user;
//        }catch (Exception e){
//            return "";
//        }
    }
}
