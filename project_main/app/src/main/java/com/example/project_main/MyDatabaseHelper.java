package com.example.project_main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private static final String NUTRITION_TABLE_COLUMN_SUGARS = "sugars";
    private static final String NUTRITION_TABLE_COLUMN_SALT = "salt";
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


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);

        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        dataBaseCheck();
    }


    public void onCreate(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG, "onOpen() : DB Opening!");
    }

    private void dataBaseCheck() {
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            dbCopy();
            Log.d(TAG, "Database is copied.");
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
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("dbcopy", "IOException 발생");
        }

    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //Toast.makeText(mContext,"onOpen()",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onOpen() : DB Opening!");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 테이블 삭제하고 onCreate() 다시 로드시킨다.
        Log.d(TAG, "onUpgrade() : DB Schema Modified and Excuting onCreate()");
    }

    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    //오늘 먹은 음식의 영양 정보
    public ArrayList<RecodeSelectDto> executeQuerySearchIntakeFoodToday(String sql_sentence) {
        ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_sentence, null);

        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            RecodeSelectDto test = new RecodeSelectDto();

            test.setFoodName(cursor.getString(0));
            test.setManufacturer(cursor.getString(1));
            test.setClassification(cursor.getString(2));
            test.setKcal(cursor.getFloat(3));
            test.setCarbohydrate(cursor.getFloat(4));
            test.setProtein(cursor.getFloat(5));
            test.setProvince(cursor.getFloat(6));
            test.setSugars(cursor.getFloat(7));
            test.setSalt(cursor.getFloat(8));
            test.setCholesterol(cursor.getFloat(9));
            test.setSaturated_fat(cursor.getFloat(10));
            test.setTrans_fat(cursor.getFloat(11));
            test.setIntakeTime(cursor.getString(12));
            intake_food.add(test);
        }
        cursor.close();
        db.close();
        return intake_food;
    }

    //음식 정보 검색
    public ArrayList<RecodeSelectDto> executeQuerySearchFood(String sql_sentence) {
        ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_sentence, null);

        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            RecodeSelectDto test = new RecodeSelectDto();

            test.setFoodName(cursor.getString(0));
            test.setManufacturer(cursor.getString(1));
            test.setClassification(cursor.getString(2));
            test.setKcal(cursor.getFloat(3));
            test.setCarbohydrate(cursor.getFloat(4));
            test.setProtein(cursor.getFloat(5));
            test.setProvince(cursor.getFloat(6));
            test.setSugars(cursor.getFloat(7));
            test.setSalt(cursor.getFloat(8));
            test.setCholesterol(cursor.getFloat(9));
            test.setSaturated_fat(cursor.getFloat(10));
            test.setTrans_fat(cursor.getFloat(11));
            intake_food.add(test);
        }
        cursor.close();
        db.close();
        return intake_food;
    }

    //해당 날짜 음식 정보 가져오기
    public ArrayList<RecodeSelectDto> executeQuerySearchIntakeFoodFromSelectedDate(String sql_sentence) {
        ArrayList<RecodeSelectDto> intake_food = new ArrayList<RecodeSelectDto>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_sentence, null);

        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            RecodeSelectDto test = new RecodeSelectDto();

            test.setIntakeID(cursor.getInt(0));
            test.setFoodName(cursor.getString(1));
            test.setKcal(cursor.getFloat(2));
            test.setCarbohydrate(cursor.getFloat(3));
            test.setProtein(cursor.getFloat(4));
            test.setProvince(cursor.getFloat(5));
            test.setSugars(cursor.getFloat(6));
            test.setSalt(cursor.getFloat(7));
            test.setCholesterol(cursor.getFloat(8));
            test.setSaturated_fat(cursor.getFloat(9));
            test.setTrans_fat(cursor.getFloat(10));
            test.setIntakeTime(cursor.getString(11));
            intake_food.add(test);
        }
        cursor.close();
        db.close();
        return intake_food;
    }


    //오늘의 타임라인 정보
    public ArrayList<TimelineSelectDto> executeQuerySearchAlarmToday(String sql_sentence) {
        ArrayList<TimelineSelectDto> alarmAll = new ArrayList<TimelineSelectDto>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_sentence, null);

        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            TimelineSelectDto test = new TimelineSelectDto();

            test.setNickname(cursor.getString(1));
            test.setContext(cursor.getString(2));
            test.setDate(cursor.getString(3));
            test.setAlarm_ing(cursor.getBlob(4));

            alarmAll.add(test);
        }
        cursor.close();
        db.close();
        return alarmAll;
    }

    //타임라인 알림내용 추가
    void addUserTimeline(String nickname, String alarmContext, Drawable alarmIcon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap bitmap = ((BitmapDrawable) alarmIcon).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        cv.put("nickname", nickname);
        cv.put("context", alarmContext);
        cv.put("alarm_img", data);

        long result = db.insert("user_timeline", null, cv);
        if (result == -1) {
            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "데이터 추가 성공", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    //칼로리 과다 섭취한 날짜
    public ArrayList<CalendarDto> ExecuteQueryGetOvereatDay(int suitableKcal) {
        ArrayList<CalendarDto> overeatDay = new ArrayList<CalendarDto>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select substr(date,1,10)\n" +
                "                from intake_table, food_table\n" +
                "                where intake_table.foodname = food_table.foodname\n" +
                "                group by substr(date,1,10)\n" +
                "                having sum(kcal) > " + suitableKcal + ";", null);

        int count = cursor.getCount();

        for (int i = 0; i < count; i++) {
            cursor.moveToNext();
            CalendarDto dto = new CalendarDto();

            dto.setOvereatDate(cursor.getString(0));
            overeatDay.add(dto);
        }

        cursor.close();
        db.close();

        return overeatDay;
    }

    //사용자 정보 가져오기
    public ArrayList<UserDto> ExecuteQueryGetUserInfo() {
        ArrayList<UserDto> userInfo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select nickname, age, sex, height, weight, activity,recommended_kcal from user_table", null);
        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            UserDto user = new UserDto();

            user.setNickname(cursor.getString(0));
            user.setAge(cursor.getInt(1));
            user.setSex(cursor.getString(2));
            user.setHeight(cursor.getFloat(3));
            user.setWeight(cursor.getFloat(4));
            user.setActivity(cursor.getString(5));
            user.setRecommendedKcal(cursor.getInt(6));
            userInfo.add(user);
        }
        cursor.close();
        db.close();

        return userInfo;
    }

    void addUser(String nickname, int age, String sex, int height, int weight, String activity, int recommended_kcal) {
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

        if (result == -1) {
            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "데이터 추가 성공", Toast.LENGTH_SHORT).show();
        }

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

    // 섭취 정보 추가
    void addIntake(String nickname, String foodname, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int maxIntakeID = getMaxIntakeID(); // 섭취 테이블에서 가장 큰 intakeID를 가져옴
        int nextIntakeID = maxIntakeID + 1; // 다음 intakeID를 자동으로 +1해준다.

        // 테이블에 레코드가 없을 경우 intakeID를 0으로 설정
        if (maxIntakeID == -1) {
            nextIntakeID = 0;
        }

        cv.put(INTAKE_TABLE_COLUMN_INTAKEID, nextIntakeID);
        cv.put(INTAKE_TABLE_COLUMN_NICKNAME, nickname);
        cv.put(INTAKE_TABLE_COLUMN_FOODNAME, foodname);
        cv.put(INTAKE_TABLE_COLUMN_DATE, date);
        cv.put(INTAKE_TABLE_COLUMN_TIME, time);

        long result = db.insert(INTAKE_TABLE_NAME, null, cv);
        db.close();
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

    // 사용자 알러지 가져오기
    public ArrayList<Integer> getUserAllergy() {

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> allergyNum = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT allergyID FROM allergy_user", null);
        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            allergyNum.add(cursor.getInt(0));
        }

        cursor.close();
        db.close();

        return allergyNum;
    }

    //사용자 지병 가져오기
    public ArrayList<Integer> getUserDisease() {

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> diseaseNum = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT diseaseID FROM disease_user", null);
        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            diseaseNum.add(cursor.getInt(0));
        }

        cursor.close();
        db.close();

        return diseaseNum;
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
        db.close();
        return result;
    }

    public String getNthMostEatenFoodForWeek(int rank) {
        SQLiteDatabase db = this.getReadableDatabase();

        // 한 주 전의 날짜 구하기
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -7);
        Date startDate = calendar.getTime();

        // 현재 날짜 구하기
        Date currentDate = new Date();

        // 날짜 포맷 설정 (yyyy-MM-dd)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // 이번 주에 rank번째로 많이 먹은 음식을 가져오기 위한 쿼리
        String query = "SELECT foodname, COUNT(foodname) as count FROM intake_table " +
                "WHERE date >= '" + dateFormat.format(startDate) + "' AND date <= '" + dateFormat.format(currentDate) + "' " +
                "GROUP BY foodname " +
                "ORDER BY count DESC " +
                "LIMIT 1 OFFSET " + (rank - 1) + ";";

        Cursor cursor = db.rawQuery(query, null);
        String foodName = "";

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("foodname");
            foodName = cursor.getString(columnIndex);
        }

        cursor.close();
        db.close();

        return foodName;
    }


    // user_table이 비어있는지 확인하는 함수
    public boolean isUserTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + USER_TABLE_NAME, null);
        int recordCount = 0;

        if (cursor != null) {
            cursor.moveToFirst();
            recordCount = cursor.getInt(0);
            cursor.close();
        }

        db.close();
        return recordCount == 0;
    }

    // 칼로리
    public int getTotalCaloriesConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCalories = 0;

        // Prepare the query
        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_KCAL + ") AS total_calories " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalCaloriesIndex = cursor.getColumnIndex("total_calories");
            totalCalories = cursor.getInt(totalCaloriesIndex);
        }

        cursor.close();
        db.close();

        return totalCalories;
    }


    // 탄수화물(day)
    public int getTotalCarbohydratesConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCarbohydrates = 0;

        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_CARBOHYDRATE + ") AS total_carbohydrates " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalCarbohydratesIndex = cursor.getColumnIndex("total_carbohydrates");
            totalCarbohydrates = cursor.getInt(totalCarbohydratesIndex);
        }

        cursor.close();
        db.close();

        return totalCarbohydrates;
    }

    // 단백질(day)
    public int getTotalProteinConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalProtein = 0;

        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_PROTEIN + ") AS total_protein " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalProteinIndex = cursor.getColumnIndex("total_protein");
            totalProtein = cursor.getInt(totalProteinIndex);
        }

        cursor.close();
        db.close();

        return totalProtein;
    }

    // 지방(day)
    public int getTotalFatConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalFat = 0;

        // Prepare the query
        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_PROVINCE + ") AS total_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalFatIndex = cursor.getColumnIndex("total_fat");
            totalFat = cursor.getInt(totalFatIndex);
        }

        cursor.close();
        db.close();

        return totalFat;
    }

    // 당류(day)
    public int getTotalSugarsConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSugars = 0;

        // Prepare the query
        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_SUGARS + ") AS total_sugars " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalSugarsIndex = cursor.getColumnIndex("total_sugars");
            totalSugars = cursor.getInt(totalSugarsIndex);
        }

        cursor.close();
        db.close();

        return totalSugars;
    }

    // 염분(day)
    public int getTotalSodiumConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSodium = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_SALT + ") AS total_sodium " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalSodiumIndex = cursor.getColumnIndex("total_sodium");
            totalSodium = cursor.getInt(totalSodiumIndex);
        }

        cursor.close();
        db.close();

        return totalSodium;
    }

    // 콜레스테롤(day)
    public int getTotalCholesterolConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCholesterol = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_CHOLESTEROL + ") AS total_cholesterol " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalCholesterolIndex = cursor.getColumnIndex("total_cholesterol");
            totalCholesterol = cursor.getInt(totalCholesterolIndex);
        }

        cursor.close();
        db.close();

        return totalCholesterol;
    }

    // 포화지방(day)
    public int getTotalSaturatedFatConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSaturatedFat = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_SATURATED_FAT + ") AS total_saturated_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalSaturatedFatIndex = cursor.getColumnIndex("total_saturated_fat");
            totalSaturatedFat = cursor.getInt(totalSaturatedFatIndex);
        }

        cursor.close();
        db.close();

        return totalSaturatedFat;
    }

    // 트랜스지방(day)
    public int getTotalTransFatConsumedOnDate(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalTransFat = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_date, SUM(" + NUTRITION_TABLE_COLUMN_TRANS_FAT + ") AS total_trans_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_date = ? " +
                "GROUP BY formatted_date;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalTransFatIndex = cursor.getColumnIndex("total_trans_fat");
            totalTransFat = cursor.getInt(totalTransFatIndex);
        }

        cursor.close();
        db.close();

        return totalTransFat;
    }


    // 칼로리 (Week)
    public int getTotalCaloriesForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCalories = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_KCAL + ") AS total_kcal " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalKcalIndex = cursor.getColumnIndex("total_kcal");
            totalCalories = cursor.getInt(totalKcalIndex);
        }

        cursor.close();
        db.close();

        return totalCalories;
    }



    // 탄수화물 (Week)
    public int getTotalCarbohydratesForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCarbohydrates = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_CARBOHYDRATE + ") AS total_carbohydrates " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalCarbohydratesIndex = cursor.getColumnIndex("total_carbohydrates");
            totalCarbohydrates = cursor.getInt(totalCarbohydratesIndex);
        }

        cursor.close();
        db.close();

        return totalCarbohydrates;
    }

    // 단백질 (Week)
    public int getTotalProteinForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalProtein = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_PROTEIN + ") AS total_protein " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalProteinIndex = cursor.getColumnIndex("total_protein");
            totalProtein = cursor.getInt(totalProteinIndex);
        }

        cursor.close();
        db.close();

        return totalProtein;
    }

    // 지방 (Week)
    public int getTotalFatForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalFat = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_PROVINCE + ") AS total_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalFatIndex = cursor.getColumnIndex("total_fat");
            totalFat = cursor.getInt(totalFatIndex);
        }

        cursor.close();
        db.close();

        return totalFat;
    }

    // 당류 (Week)
    public int getTotalSugarsForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSugars = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_SUGARS + ") AS total_sugars " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalSugarsIndex = cursor.getColumnIndex("total_sugars");
            totalSugars = cursor.getInt(totalSugarsIndex);
        }

        cursor.close();
        db.close();

        return totalSugars;
    }

    // 나트륨 (Week)
    public int getTotalSodiumForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSodium = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_SALT + ") AS total_sodium " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalSodiumIndex = cursor.getColumnIndex("total_sodium");
            totalSodium = cursor.getInt(totalSodiumIndex);
        }

        cursor.close();
        db.close();

        return totalSodium;
    }


    // 콜레스테롤 (Week)
    public int getTotalCholesterolForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCholesterol = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_CHOLESTEROL + ") AS total_cholesterol " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalCholesterolIndex = cursor.getColumnIndex("total_cholesterol");
            totalCholesterol = cursor.getInt(totalCholesterolIndex);
        }

        cursor.close();
        db.close();

        return totalCholesterol;
    }

    // 트랜스지방 (Week)
    public int getTotalTransFatForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalTransFat = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_TRANS_FAT + ") AS total_trans_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalTransFatIndex = cursor.getColumnIndex("total_trans_fat");
            totalTransFat = cursor.getInt(totalTransFatIndex);
        }

        cursor.close();
        db.close();

        return totalTransFat;
    }

    // 포화지방산 (Week)
    public int getTotalSaturatedFatForDateRange(String dateRange) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSaturatedFat = 0;

        // 입력된 날짜 범위를 분리
        String[] dateParts = dateRange.split("~");
        if (dateParts.length != 2) {
            // 유효한 날짜 범위가 아닌 경우
            return 0;
        }

        String startDate = dateParts[0].trim();
        String endDate = dateParts[1].trim();

        // 시작 날짜와 끝 날짜를 "MM/DD" 형식으로 분리
        String[] startDateParts = startDate.split("/");
        String[] endDateParts = endDate.split("/");

        if (startDateParts.length != 2 || endDateParts.length != 2) {
            // 유효한 날짜 형식이 아닌 경우
            return 0;
        }

        String startMonth = startDateParts[0].trim();
        String startDay = startDateParts[1].trim();
        String endMonth = endDateParts[0].trim();
        String endDay = endDateParts[1].trim();

        // 쿼리문 준비
        String query = "SELECT SUM(" + NUTRITION_TABLE_COLUMN_SATURATED_FAT + ") AS total_saturated_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE strftime('%m/%d', " + INTAKE_TABLE_COLUMN_DATE + ") BETWEEN ? AND ?;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{startMonth + "/" + startDay, endMonth + "/" + endDay});

        if (cursor.moveToFirst()) {
            int totalSaturatedFatIndex = cursor.getColumnIndex("total_saturated_fat");
            totalSaturatedFat = cursor.getInt(totalSaturatedFatIndex);
        }

        cursor.close();
        db.close();

        return totalSaturatedFat;
    }


    // 칼로리(Month)
    public int getTotalCaloriesForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCalories = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_KCAL + ") AS total_calories " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalCaloriesIndex = cursor.getColumnIndex("total_calories");
            totalCalories = cursor.getInt(totalCaloriesIndex);
        }

        cursor.close();
        db.close();

        return totalCalories;
    }

    // 탄수화물(Month)
    public int getTotalCarbohydratesForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCarbohydrates = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_CARBOHYDRATE + ") AS total_carbohydrates " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalCarbohydratesIndex = cursor.getColumnIndex("total_carbohydrates");
            totalCarbohydrates = cursor.getInt(totalCarbohydratesIndex);
        }

        cursor.close();
        db.close();

        return totalCarbohydrates;
    }

    // 단백질(Month)
    public int getTotalProteinsForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalProteins = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_PROTEIN + ") AS total_proteins " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalProteinsIndex = cursor.getColumnIndex("total_proteins");
            totalProteins = cursor.getInt(totalProteinsIndex);
        }

        cursor.close();
        db.close();

        return totalProteins;
    }

    // 지방(Month)
    public int getTotalFatForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalFat = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_PROVINCE + ") AS total_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalFatIndex = cursor.getColumnIndex("total_fat");
            totalFat = cursor.getInt(totalFatIndex);
        }

        cursor.close();
        db.close();

        return totalFat;
    }

    // 당류(Month)
    public int getTotalSugarsForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSugars = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_SUGARS + ") AS total_sugars " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalSugarsIndex = cursor.getColumnIndex("total_sugars");
            totalSugars = cursor.getInt(totalSugarsIndex);
        }

        cursor.close();
        db.close();

        return totalSugars;
    }

    // 나트륨 (Month)
    public int getTotalSaltForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSalt = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_SALT + ") AS total_salt " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalSaltIndex = cursor.getColumnIndex("total_salt");
            totalSalt = cursor.getInt(totalSaltIndex);
        }

        cursor.close();
        db.close();

        return totalSalt;
    }

    // 콜레스테롤 (Month)
    public int getTotalCholesterolForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCholesterol = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_CHOLESTEROL + ") AS total_cholesterol " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalCholesterolIndex = cursor.getColumnIndex("total_cholesterol");
            totalCholesterol = cursor.getInt(totalCholesterolIndex);
        }

        cursor.close();
        db.close();

        return totalCholesterol;
    }

    // 트랜스지방 (Month)
    public int getTotalTransFatForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalTransFat = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_TRANS_FAT + ") AS total_trans_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalTransFatIndex = cursor.getColumnIndex("total_trans_fat");
            totalTransFat = cursor.getInt(totalTransFatIndex);
        }

        cursor.close();
        db.close();

        return totalTransFat;
    }

    // 포화지방산 (Month)
    public int getTotalSaturatedFatForMonth(String targetDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSaturatedFat = 0;

        // 쿼리문 준비
        String query = "SELECT strftime('%Y-%m', " + INTAKE_TABLE_COLUMN_DATE + ") AS formatted_month, SUM(" + NUTRITION_TABLE_COLUMN_SATURATED_FAT + ") AS total_saturated_fat " +
                "FROM " + INTAKE_TABLE_NAME + " AS i " +
                "JOIN " + FOOD_TABLE_NAME + " AS n ON i." + INTAKE_TABLE_COLUMN_FOODNAME + " = n." + FOOD_TABLE_COLUMN_FOODNAME + " " +
                "WHERE formatted_month = ? " +
                "GROUP BY formatted_month;";

        // 쿼리 실행
        Cursor cursor = db.rawQuery(query, new String[]{targetDate});

        if (cursor.moveToFirst()) {
            int totalSaturatedFatIndex = cursor.getColumnIndex("total_saturated_fat");
            totalSaturatedFat = cursor.getInt(totalSaturatedFatIndex);
        }

        cursor.close();
        db.close();

        return totalSaturatedFat;
    }


    // 테이블의 모든 행 삭제
    void deleteAllRows(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    public String getResult() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM food_table where foodname = '신라면'", null);
        while (cursor.moveToNext()) {
            result += "foodname : " + cursor.getString(1);
        }

        return result;
    }

    void resetUserInfo(String name, int age, String sex, float height, float weight, String activity, int recommendKcal) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE user_table " +
                "SET nickname = '" + name + "'," +
                "age = " + age + ",\n" +
                "sex ='" + sex + "',\n" +
                "height = " + height + ",\n" +
                "weight = " + weight + ",\n" +
                "activity = '" + activity + "',\n" +
                "recommended_kcal = " + recommendKcal + " ;");
    }

    //해당 음식 섭취 정보 삭제
    void deleteFoodIntake(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from intake_table where intakeID = " + id);
        db.close();
    }

    public ArrayList<NutritionDto> getFoodNutrition(String foodName) {

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<NutritionDto> foodNutriInfo = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT kcal, protein, province, carbohydrate, sugars, salt, cholesterol, saturated_fat, trans_fat\n"+
                "from food_table\n"+
                "where foodname='"+foodName + "';", null);

        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            NutritionDto nutri = new NutritionDto();
            nutri.setKcal(cursor.getInt(0));
            nutri.setCarbohydrate(cursor.getFloat(1));
            nutri.setProtein(cursor.getFloat(2));
            nutri.setProvince(cursor.getFloat(3));
            nutri.setSugars(cursor.getFloat(4));
            nutri.setSalt(cursor.getFloat(5));
            nutri.setCholesterol(cursor.getFloat(6));
            nutri.setSaturated_fat(cursor.getFloat(7));
            nutri.setTrans_fat(cursor.getFloat(8));
            foodNutriInfo.add(nutri);
        }

        cursor.close();
        db.close();
        return foodNutriInfo;
    }

}