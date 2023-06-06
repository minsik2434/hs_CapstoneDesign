package com.example.project_main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = "DataBaseHelper";
    private static String DB_PATH = "";
    private static String DB_NAME = "eatdatabase.db";
    private SQLiteDatabase mDataBase;
    private Context mContext;
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
        super(context,DB_NAME,null,1);

        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        dataBaseCheck();
    }


    public void onCreate(SQLiteDatabase db){
        super.onOpen(db);
        Log.d(TAG,"onOpen() : DB Opening!");
    }
    private void dataBaseCheck() {
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            dbCopy();
            Log.d(TAG,"Database is copied.");
        }
    }

    private void dbCopy() {

        try {
            File folder = new File(DB_PATH);
            if (!folder.exists()) {
                folder.mkdir();
            }


            InputStream inputStream = mContext.getAssets().open(DB_NAME);
            String out_filename = DB_PATH + DB_NAME;
            OutputStream outputStream = new FileOutputStream(out_filename);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = inputStream.read(mBuffer)) > 0) {
                outputStream.write(mBuffer, 0, mLength);
            }
            outputStream.flush();
            ;
            outputStream.close();
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
            Log.d("dbcopy","IOException 발생");
        }

    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //Toast.makeText(mContext,"onOpen()",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onOpen() : DB Opening!");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 테이블 삭제하고 onCreate() 다시 로드시킨다.
        Log.d(TAG,"onUpgrade() : DB Schema Modified and Excuting onCreate()");
    }
    public synchronized void close(){
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
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
            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mContext, "데이터 추가 성공", Toast.LENGTH_SHORT).show();
        }
    }

    public String getResult(){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM food_table where foodname = '신라면'",null);
        while(cursor.moveToNext()){
            result += "foodname : " +cursor.getString(1);
        }

        return result;
    }

}
