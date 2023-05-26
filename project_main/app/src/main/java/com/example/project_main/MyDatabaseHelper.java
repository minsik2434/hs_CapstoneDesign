package com.example.project_main;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    //db
    private static final String DATABASE_NAME = "ewAl_db2.db";
    private static final int DATABASE_VERSION = 1;
    //table
    private static final String USER_TABLE_NAME = "user_table";
    private static final String FOOD_TABLE_NAME = "food_table";
    private static final String NUTRITION_TABLE_NAME = "nutrition";
    private static final String INTAKE_TABLE_NAME = "intake_table";
    private static final String ALLERGY_TABLE_NAME = "allergy";
    private static final String ALLERGY_USER_TABLE_NAME = "allergy_user";
    private static final String DISEASE_TABLE_NAME = "disease";
    private static final String DISEASE_USER_TABLE_NAME = "disease_user";
    //user_table column
    private static final String USER_TABLE_COLUMN_NICKNAME = "nickname";
    private static final String USER_TABLE_COLUMN_AGE = "age";
    private static final String USER_TABLE_COLUMN_SEX = "sex";
    private static final String USER_TABLE_COLUMN_HEIGHT = "height";
    private static final String USER_TABLE_COLUMN_WEIGHT = "weight";
    private static final String USER_TABLE_COLUMN_ACTIVITY = "activity";
    //food_table column
    private static final String FOOD_TABLE_COLUMN_FOODID = "foodID";
    private static final String FOOD_TABLE_COLUMN_FOODNAME = "foodname";
    private static final String FOOD_TABLE_COLUMN_RAW_MATERIAL = "raw_material";
    private static final String FOOD_TABLE_COLUMN_ALLERGY = "allergy";
    private static final String FOOD_TABLE_COLUMN_BARCODE = "barcode";
    private static final String FOOD_TABLE_COLUMN_FOOD_IMAGE = "food_image";
    //nutrition_table column
    private static final String NUTRITION_TABLE_COLUMN_NUTRITIONID = "nutritionID";
    private static final String NUTRITION_TABLE_COLUMN_FOODID = "foodID";
    private static final String NUTRITION_TABLE_COLUMN_KCAL = "kcal";
    private static final String NUTRITION_TABLE_COLUMN_CARBOHYDRATE = "carbohydrate";
    private static final String NUTRITION_TABLE_COLUMN_PROTEIN = "protein";
    private static final String NUTRITION_TABLE_COLUMN_PROVINCE = "province";
    private static final String NUTRITION_TABLE_COLUMN_CHOLESTEROL = "cholesterol";
    private static final String NUTRITION_TABLE_COLUMN_TRANS_FAT = "trans_fat";
    private static final String NUTRITION_TABLE_COLUMN_SATURATED_FAT = "saturated_fat";
    //intake_table column
    private static final String INTAKE_TABLE_COLUMN_INTAKEID = "intakeID";
    private static final String INTAKE_TABLE_COLUMN_NICKNAME = "nickname";
    private static final String INTAKE_TABLE_COLUMN_FOODID = "foodID";
    private static final String INTAKE_TABLE_COLUMN_DATE = "date";
    private static final String INTAKE_TABLE_COLUMN_TIME = "time";
    //allergy_table column
    private static final String ALLERGY_TABLE_COLUMN_ALLERGYID = "allergyID";
    private static final String ALLERGY_TABLE_COLUMN_ALLERGY_NAME = "allergy_name";
    //allergy_user_table column
    private static final String ALLERGY_USER_TABLE_COLUMN_NICKNAME = "nickname";
    private static final String ALLERGY_USER_TABLE_COLUMN_ALLERGYID = "allergyID";
    //disease_table column
    private static final String DISEASE_TABLE_COLUMN_DISEASEID = "diseaseID";
    private static final String DISEASE_TABLE_COLUMN_DISEASE_NAME = "disease_name";
    //disease_user_table column
    private static final String DISEASE_USER_TABLE_COLUMN_NICKNAME = "nickname";
    private static final String DISEASE_USER_TABLE_COLUMN_DISEASEID = "diseaseID";

    public MyDatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        createTable_user_table(db);
        createTable_food_table(db);
        createTable_nutrition_table(db);
        createTable_intake_table(db);
        createTable_allergy_table(db);
        createTable_allergy_user_table(db);
        createTable_disease_table(db);
        createTable_disease_user_table(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NUTRITION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INTAKE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ALLERGY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ALLERGY_USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DISEASE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DISEASE_USER_TABLE_NAME);

        onCreate(db);
    }

    void addUser(String nickname, int age, String sex, int height, int weight, String activity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_TABLE_COLUMN_NICKNAME, nickname);
        cv.put(USER_TABLE_COLUMN_AGE, age);
        cv.put(USER_TABLE_COLUMN_SEX, sex);
        cv.put(USER_TABLE_COLUMN_HEIGHT, height);
        cv.put(USER_TABLE_COLUMN_WEIGHT, weight);
        cv.put(USER_TABLE_COLUMN_ACTIVITY, activity);

        long result = db.insert(USER_TABLE_NAME, null, cv);

        if (result == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "데이터 추가 성공", Toast.LENGTH_SHORT).show();
        }
    }

    private static void createTable_user_table(SQLiteDatabase db){
        try {
            if(db != null)
            {
                String query = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME
                        + " (" + USER_TABLE_COLUMN_NICKNAME + " TEXT, "
                        + USER_TABLE_COLUMN_AGE + " INTEGER, "
                        + USER_TABLE_COLUMN_SEX + " TEXT, "
                        + USER_TABLE_COLUMN_HEIGHT + " INTEGER, "
                        + USER_TABLE_COLUMN_WEIGHT + " INTEGER, "
                        + USER_TABLE_COLUMN_ACTIVITY + " TEXT, PRIMARY KEY(nickname)); ";

                db.execSQL(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createTable_food_table(SQLiteDatabase db){
        try {
            if(db != null)
            {
                String query = "CREATE TABLE IF NOT EXISTS " + FOOD_TABLE_NAME
                        + " (" + FOOD_TABLE_COLUMN_FOODID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FOOD_TABLE_COLUMN_FOODNAME + " TEXT, "
                        + FOOD_TABLE_COLUMN_RAW_MATERIAL + " TEXT, "
                        + FOOD_TABLE_COLUMN_ALLERGY + " TEXT, "
                        + FOOD_TABLE_COLUMN_BARCODE + " TEXT, "
                        + FOOD_TABLE_COLUMN_FOOD_IMAGE + " TEXT); ";

                db.execSQL(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createTable_nutrition_table(SQLiteDatabase db){
        try {
            if(db != null)
            {
                String query = "CREATE TABLE IF NOT EXISTS " + NUTRITION_TABLE_NAME
                        + " (" + NUTRITION_TABLE_COLUMN_NUTRITIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + NUTRITION_TABLE_COLUMN_FOODID + " INTEGER, "
                        + NUTRITION_TABLE_COLUMN_KCAL + " INTEGER, "
                        + NUTRITION_TABLE_COLUMN_CARBOHYDRATE + " INTEGER, "
                        + NUTRITION_TABLE_COLUMN_PROTEIN + " INTEGER, "
                        + NUTRITION_TABLE_COLUMN_PROVINCE + " INTEGER, "
                        + NUTRITION_TABLE_COLUMN_CHOLESTEROL + " INTEGER, "
                        + NUTRITION_TABLE_COLUMN_TRANS_FAT + " INTEGER, "
                        + NUTRITION_TABLE_COLUMN_SATURATED_FAT + " INTEGER, FOREIGN KEY(foodID) REFERENCES food_table(foodID) ON UPDATE CASCADE); ";

                db.execSQL(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createTable_intake_table(SQLiteDatabase db){
        try {
            if(db != null)
            {
                String query = "CREATE TABLE IF NOT EXISTS " + INTAKE_TABLE_NAME
                        + " (" + INTAKE_TABLE_COLUMN_INTAKEID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + INTAKE_TABLE_COLUMN_NICKNAME + " TEXT, "
                        + INTAKE_TABLE_COLUMN_FOODID + " INTEGER, "
                        + INTAKE_TABLE_COLUMN_DATE + " TEXT NOT NULL DEFAULT (datetime('now', 'localtime')), "
                        + INTAKE_TABLE_COLUMN_TIME + " TEXT, FOREIGN KEY(nickname) REFERENCES user_table(nickname) ON UPDATE CASCADE"
                        + ", FOREIGN KEY(foodID) REFERENCES food_table(foodID) ON UPDATE CASCADE); ";

                db.execSQL(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createTable_allergy_table(SQLiteDatabase db){
        try {
            if(db != null)
            {
                String query = "CREATE TABLE IF NOT EXISTS " + ALLERGY_TABLE_NAME
                        + " (" + ALLERGY_TABLE_COLUMN_ALLERGYID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ALLERGY_TABLE_COLUMN_ALLERGY_NAME + " TEXT); ";

                db.execSQL(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createTable_allergy_user_table(SQLiteDatabase db){
        try {
            if(db != null)
            {
                String query = "CREATE TABLE IF NOT EXISTS " + ALLERGY_USER_TABLE_NAME
                        + " (" + ALLERGY_USER_TABLE_COLUMN_NICKNAME + " TEXT, "
                        + ALLERGY_USER_TABLE_COLUMN_ALLERGYID + " INTEGER, PRIMARY KEY(nickname, allergyID), " +
                        "FOREIGN KEY(nickname) REFERENCES user_table (nickname) ON UPDATE CASCADE, " +
                        "FOREIGN KEY(allergyID) REFERENCES allergy(allergyID) ON UPDATE CASCADE); ";

                db.execSQL(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createTable_disease_table(SQLiteDatabase db){
        try {
            if(db != null)
            {
                String query = "CREATE TABLE IF NOT EXISTS " + DISEASE_TABLE_NAME
                        + " (" + DISEASE_TABLE_COLUMN_DISEASEID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + DISEASE_TABLE_COLUMN_DISEASE_NAME + " TEXT); ";

                db.execSQL(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createTable_disease_user_table(SQLiteDatabase db){
        try {
            if(db != null)
            {
                String query = "CREATE TABLE IF NOT EXISTS " + DISEASE_USER_TABLE_NAME
                        + " (" + DISEASE_USER_TABLE_COLUMN_NICKNAME + " TEXT, "
                        + DISEASE_USER_TABLE_COLUMN_DISEASEID + " INTEGER, PRIMARY KEY(nickname,diseaseID), " +
                        "FOREIGN KEY(nickname) REFERENCES user_table(nickname) ON UPDATE CASCADE, " +
                        "FOREIGN KEY(diseaseID) REFERENCES disease(diseaseID) ON UPDATE CASCADE); ";

                db.execSQL(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
