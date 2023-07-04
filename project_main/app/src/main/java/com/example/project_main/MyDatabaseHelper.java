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
import java.util.ArrayList;

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
    private static final String USER_TABLE_COLUMN_RECOMMENDED_KCAL = "recommended_kcal";
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
    private static final String INTAKE_TABLE_COLUMN_FOODNAME = "foodname";
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

//    public RecodeSelectDto executeQuerySearchFoodByBarcode(String barcodeNum) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select food_image, foodname, kcal, carbohydrate, protein, province, raw_material " +
//                "from food_table, nutrition where food_table.foodID = nutrition.foodID and barcode = '"+barcodeNum+"';",null);
//        cursor.moveToLast();
//        RecodeSelectDto recode_list = new RecodeSelectDto();
//        recode_list.setFoodImage(cursor.getString(0));
//        recode_list.setFoodName(cursor.getString(1));
//        recode_list.setFoodKcal((int) cursor.getFloat(2));
//        recode_list.setFoodCarbohydrate(cursor.getFloat(3));
//        recode_list.setFoodProtein(cursor.getFloat(4));
//        recode_list.setFoodProvince(cursor.getFloat(5));
//        recode_list.setRaw_material(cursor.getString(6));
//
//        cursor.close();
//        db.close();
//
//        return recode_list;
//    }

    //오늘 먹은 음식의 영양 정보
    public ArrayList<RecodeSelectDto> executeQuerySearchIntakeFoodToday(String sql_sentence){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_sentence,null);

//        Cursor cursor = db.rawQuery("select food_table.foodname, manufacturer, classification, kcal, carbohydrate, protein, province, sugars, salt, cholesterol, saturated_fat, trans_fat\n" +
//                "from food_table, intake_table\n" +
//                "where food_table.foodname = intake_table.foodname\n" +
//                "and intakeID in (select intakeID from intake_table where substr(date,1,10) = date('now'));",null);
        int recordCount = cursor.getCount();
        ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();

        for (int i = 0; i < recordCount; i++){
            cursor.moveToNext();
            intake_food.get(i).setFoodName(cursor.getString(0));
            intake_food.get(i).setManufacturer(cursor.getString(1));
            intake_food.get(i).setClassification(cursor.getString(2));
            intake_food.get(i).setKcal(cursor.getFloat(3));
            intake_food.get(i).setCarbohydrate(cursor.getFloat(4));
            intake_food.get(i).setProtein(cursor.getFloat(5));
            intake_food.get(i).setProvince(cursor.getFloat(6));
            intake_food.get(i).setSugars(cursor.getFloat(7));
            intake_food.get(i).setSalt(cursor.getFloat(8));
            intake_food.get(i).setCholesterol(cursor.getFloat(9));
            intake_food.get(i).setSaturated_fat(cursor.getFloat(10));
            intake_food.get(i).setTrans_fat(cursor.getFloat(11));
        }
        cursor.close();
        db.close();

        return intake_food;
    }

    void addUser(String nickname, int age, String sex, int height, int weight, String activity, int recommended_kcal)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_TABLE_COLUMN_NICKNAME, nickname);
        cv.put(USER_TABLE_COLUMN_AGE, age);
        cv.put(USER_TABLE_COLUMN_SEX, sex);
        cv.put(USER_TABLE_COLUMN_HEIGHT, height);
        cv.put(USER_TABLE_COLUMN_WEIGHT, weight);
        cv.put(USER_TABLE_COLUMN_ACTIVITY, activity);
        cv.put(USER_TABLE_COLUMN_RECOMMENDED_KCAL, recommended_kcal);

        long result = db.insert(USER_TABLE_NAME, null, cv);

        if (result == -1)
        {
            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mContext, "데이터 추가 성공", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void addIntake(String nickname, String foodname, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int maxIntakeID = getMaxIntakeID();
        int nextIntakeID = maxIntakeID+1;

        if(maxIntakeID == -1){
            nextIntakeID = 0;
        }

        cv.put(INTAKE_TABLE_COLUMN_INTAKEID, nextIntakeID);
        cv.put(INTAKE_TABLE_COLUMN_NICKNAME, nickname);
        cv.put(INTAKE_TABLE_COLUMN_FOODNAME, foodname);
        cv.put(INTAKE_TABLE_COLUMN_DATE, date);
        cv.put(INTAKE_TABLE_COLUMN_TIME, time);

        long result = db.insert(INTAKE_TABLE_NAME, null, cv);

    }

    // 가장 큰 intakeID 가져오기
    private int getMaxIntakeID() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + INTAKE_TABLE_COLUMN_INTAKEID + ") FROM " + INTAKE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int maxID = -1; // 초기값을 -1로 설정
        if (cursor.moveToFirst()) {
            maxID = cursor.getInt(0);
        }
        cursor.close();
        return maxID;
    }


    // 알러지 추가
    void addAllergies(int allergyID, String allergyName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ALLERGY_TABLE_COLUMN_ALLERGYID, allergyID);
        cv.put(ALLERGY_TABLE_COLUMN_ALLERGY_NAME, allergyName);

        long result = db.insert(ALLERGY_TABLE_NAME, null, cv);
        db.close();

    }


    // 지병 추가
    void addDiseases(int diseaseID, String disease_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DISEASE_TABLE_COLUMN_DISEASEID, diseaseID);
        cv.put(DISEASE_TABLE_COLUMN_DISEASE_NAME, disease_name);

        long result = db.insert(DISEASE_TABLE_NAME, null, cv);
        db.close();

    }

    // 사용자 알러지 추가
    void addUserAllergies(String nickname, int allergyID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ALLERGY_USER_TABLE_COLUMN_ALLERGYID, allergyID);
        cv.put(ALLERGY_USER_TABLE_COLUMN_NICKNAME, nickname);

        long result = db.insert(ALLERGY_USER_TABLE_NAME, null, cv);
        db.close();

    }

    // 사용자 지병 추가
    void addUserDiseases(String nickname, int diseaseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DISEASE_USER_TABLE_COLUMN_NICKNAME, nickname);
        cv.put(DISEASE_USER_TABLE_COLUMN_DISEASEID, diseaseID);

        long result = db.insert(DISEASE_USER_TABLE_NAME, null, cv);
        db.close();

    }


    String getNickname() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT nickname FROM user_table LIMIT 1", null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("nickname"); // 열 이름을 직접 지정
            result = cursor.getString(columnIndex);
        }

        cursor.close();
        return result;
    }


    // 테이블의 모든 행 삭제
    void deleteAllRows(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
        Toast.makeText(mContext, tableName + " 테이블의 모든 행이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
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
