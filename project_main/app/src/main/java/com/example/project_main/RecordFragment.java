package com.example.project_main;


import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecordFragment extends Fragment {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;

    Button recordBtn;
    TextView recordFoodName, recordFoodKcal, recordFoodInfo;
    TextView raw_mtrl;
    ImageButton record_breakfast_btn, record_lunch_btn, record_dinner_btn;
    ImageView foodImg;
    TextView searchedFoodName;
    TextView searchedFoodKcal;
    TextView searchedFoodNutriInfo;
    AllergyList allergyList = new AllergyList();
    TextView todayRecordTextview;
    ArrayList<String> userAllergy = new ArrayList<>();
    ArrayList<NutritionDto> foodNurition = new ArrayList<>();

    boolean morningImgBtn = false;
    boolean lunchImgBtn = false;
    boolean dinnerImgBtn = false;

    private ImageButton dateButton;

    private TextView dateTextView;
    private Calendar selectedDate = Calendar.getInstance();
    static final int TAKE_PICTURE = 2;
    static final int REQUEST_TAKE_PHOTO = 2;
    String mCurrentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        searchedFoodName = view.findViewById(R.id.recordFoodName);
        searchedFoodKcal = view.findViewById(R.id.recordFoodKcal);
        searchedFoodNutriInfo = view.findViewById(R.id.recordFoodInfo);
        recordBtn = view.findViewById(R.id.recordBtn);
        recordFoodName = view.findViewById(R.id.recordFoodName);
        recordFoodKcal = view.findViewById(R.id.recordFoodKcal);
        recordFoodInfo = view.findViewById(R.id.recordFoodInfo);
        raw_mtrl = view.findViewById(R.id.raw_material_text);
        foodImg = view.findViewById(R.id.recordFoodImage);
        todayRecordTextview = view.findViewById(R.id.todayRecordTextview);
        record_breakfast_btn = view.findViewById(R.id.record_breakfast_btn);
        record_lunch_btn = view.findViewById(R.id.record_lunch_btn);
        record_dinner_btn = view.findViewById(R.id.record_dinner_btn);

        String year = "yyyy";
        String month = "MM";
        String day = "dd";

        todayRecordTextview.setText(dateFormat(year) + "년 " + dateFormat(month) + "월 " + dateFormat(day) + "일");

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());


        dateButton = view.findViewById(R.id.dateButton);
        dateTextView = view.findViewById(R.id.dateTextView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
        dateTextView.setText(dateFormat.format(selectedDate.getTime()));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            }
            else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        foodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View customLayout = getLayoutInflater().inflate(R.layout.record_popup, null);
                builder.setView(customLayout);

                AlertDialog dialog = builder.create(); // AlertDialog를 final로 선언

                Button barcodebtn = customLayout.findViewById(R.id.barcodeBtn);
                barcodebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 팝업 닫기
                        dialog.dismiss();
                        if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED) {
                            startBarcodeScanner();
                        } else {
                            ActivityCompat.requestPermissions(requireActivity(),
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                        }
                    }
                });

                Button searchbtn = customLayout.findViewById(R.id.searchbtn);
                searchbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 팝업 닫기
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });

                Button picbtn = customLayout.findViewById(R.id.picbtn);
                picbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photoFile = null;
                        try{photoFile = createImageFile();}
                        catch (IOException ex) { }
                        if(photoFile != null){
                            Uri photoURI = FileProvider.getUriForFile(getActivity(),"com.example.project_main.fileprovider",photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                            startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
                        }
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        record_breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (morningImgBtn) {
                    morningImgBtn = false;
                    record_breakfast_btn.setAlpha(0.3f);
                } else {
                    morningImgBtn = true;
                    record_breakfast_btn.setAlpha(1.0f);
                }
            }
        });
        record_lunch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lunchImgBtn) {
                    lunchImgBtn = false;
                    record_lunch_btn.setAlpha(0.3f);
                } else {
                    lunchImgBtn = true;
                    record_lunch_btn.setAlpha(1.0f);
                }
            }
        });
        record_dinner_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dinnerImgBtn) {
                    dinnerImgBtn = false;
                    record_dinner_btn.setAlpha(0.3f);
                } else {
                    dinnerImgBtn = true;
                    record_dinner_btn.setAlpha(1.0f);
                }
            }
        });


        // 기록하기 버튼을 눌렀을 때
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = dbHelper.getNickname();
                String foodname = recordFoodName.getText().toString();

                // dateTextView에서 날짜 문자열 가져오기
                String dateText = dateTextView.getText().toString();

                // dateText를 "yy년 MM월 dd일" 형식에서 "yyyy-MM-dd" 형식으로 변환
                SimpleDateFormat inputFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                String date = "";
                try {
                    Date parsedDate = inputFormat.parse(dateText);
                    date = outputFormat.format(parsedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 현재 날짜 가져오기
                SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                String currentDate = currentDateFormat.format(new Date());

                // 현재 시간을 "hh:mm:ss" 형식으로 가져오기
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
                String time = timeFormat.format(new Date());

                int count = 0;
                if (morningImgBtn) {
                    count++;
                }
                if (lunchImgBtn) {
                    count++;
                }
                if (dinnerImgBtn) {
                    count++;
                }

                if (foodname.equals("음식 이름")) {
                    Toast.makeText(getActivity(), "음식을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                } else if (date.compareTo(currentDate) > 0) {
                    Toast.makeText(getActivity(), "미래 날짜를 선택할 수 없습니다." + date, Toast.LENGTH_SHORT).show();
                } else {
                    if (count == 0) {
                        Toast.makeText(getActivity(), "아침, 점심, 저녁 중 하나를 선택하세요.", Toast.LENGTH_SHORT).show();
                    } else if (count > 1) {
                        Toast.makeText(getActivity(), "아침, 점심, 저녁 중 하나만 선택하세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        String dateTime = date + " " + time;

                        String mealTime = "";

                        if (morningImgBtn)
                            mealTime = "아침";
                        else if (lunchImgBtn)
                            mealTime = "점심";
                        else
                            mealTime = "저녁";
                        dbHelper.addIntake(nickname, foodname, dateTime, mealTime);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED);

    }

    private void startBarcodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(requireActivity());
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    // 시간을 가지고 오는 함수
    public String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDateTime = new Date();
        return dateFormat.format(currentDateTime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Integer> userAllergyListNum = dbHelper.getUserAllergy();
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            File file = new File(mCurrentPhotoPath);
            Bitmap bitmap;
            ExifInterface exif = null;
            try{
                bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                exif = new ExifInterface(mCurrentPhotoPath);
                int exifOrientation;
                int exifDegree;
                if (exif != null) {
                    exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    exifDegree = exifOrientationToDegrees(exifOrientation);
                } else {
                    exifDegree = 0;
                }
                foodImg.setImageBitmap(rotate(bitmap, exifDegree));
                if(bitmap !=null){
                    //foodImg.setImageBitmap(bitmap);
                    Interpreter tflite = getTfliteInterpreter("food_model.tflite");
                    int imageWidth = 224;
                    int imageHeight = 224;
                    float[][][][] inputData = new float[1][imageWidth][imageHeight][3];
                    float[][] output = new float[1][150];
                    Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, imageWidth,imageHeight,true);
                    for(int y=0; y<imageHeight; y++){
                        for(int x=0; x<imageWidth; x++){
                            int pixelValue = inputBitmap.getPixel(x,y);
                            inputData[0][x][y][0] = (Color.red(pixelValue)/255.0f);
                            inputData[0][x][y][1] = (Color.green(pixelValue)/255.0f);
                            inputData[0][x][y][2] = (Color.blue(pixelValue)/255.0f);
                        }
                    }
                    tflite.run(inputData,output);
                    int foodindex=0;
                    float max = output[0][0];
                    int maxIndex = 0;
                    for(int i=0; i<150; i++){
                        if(output[0][i] > max){
                            max = output[0][i];
                            maxIndex = i;
                        }
                    }
                    foodindex = maxIndex;
                    Foodname f = new Foodname();
                    recordFoodName.setText(f.getFoodName(foodindex));
                    foodNurition = dbHelper.getFoodNutrition(f.getFoodName(foodindex));
                    recordFoodKcal.setText(""+foodNurition.get(0).getKcal()+"kcal");
                    recordFoodInfo.setText("탄수화물 "+ foodNurition.get(0).getCarbohydrate() + "g, 단백질 "+ foodNurition.get(0).getProtein() + "g, 지방 " + foodNurition.get(0).getProvince() + "g");
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return;
        }

//        사용자 알레르기 정보 가져오기
//        //알레르기 정보 가져오기
        for (int i = 0; i < userAllergyListNum.size(); i++) {

            for (int j = 0; j < allergyList.getAllergyArrayList(userAllergyListNum.get(i)).size(); j++) {
                userAllergy.add(allergyList.getAllergyArrayList(userAllergyListNum.get(i)).get(j));
            }
        }

        if (resultCode == RESULT_OK) {
            String fn = data.getStringExtra("fname");
            String kcal = data.getStringExtra("kcal");
            String info = data.getStringExtra("foodinfo");
            String mtrl = data.getStringExtra("rawmtrl");
            String imgurl = data.getStringExtra("imgurl");

            if (imgurl == null && mtrl == null) {
                foodImg.setImageResource(R.drawable.noimg);
                raw_mtrl.setText("알 수 없음");

            } else if (imgurl == null) {
                foodImg.setImageResource(R.drawable.noimg);
                SpannableString spannableString = new SpannableString(mtrl);
                for (int i = 0; i < userAllergy.size(); i++) {
                    int startIndex = mtrl.indexOf(userAllergy.get(i));

                    if (startIndex != -1) {
                        int endIndex = startIndex + userAllergy.get(i).length();
                        spannableString.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, 0);

                    }
                }
                raw_mtrl.setText(spannableString);
            } else if (mtrl == null) {
                raw_mtrl.setText("알 수 없음");
                Glide.with(this).load(imgurl).into(foodImg);
            } else {
                SpannableString spannableString = new SpannableString(mtrl);
                for (int i = 0; i < userAllergy.size(); i++) {
                    int startIndex = mtrl.indexOf(userAllergy.get(i));
                    if (startIndex != -1) {
                        int endIndex = startIndex + userAllergy.get(i).length();
                        spannableString.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, 0);

                    }
                }
                raw_mtrl.setText(spannableString);
                Glide.with(this).load(imgurl).into(foodImg);
            }
            recordFoodName.setText(fn);
            recordFoodKcal.setText(kcal);
            recordFoodInfo.setText(info);
            return;
        }

    }


    // DatePickerDialog를 표시하는 메서드
    private void showDatePickerDialog() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
                String formattedDate = dateFormat.format(selectedDate.getTime());

                dateTextView.setText(formattedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }


    private String dateFormat(String pattern) {
        Date date = new Date();
        return new SimpleDateFormat(pattern).format(date);
    }

    @Override
    public void onResume() {
        super.onResume();

        // 현재 날짜를 가져와서 dateTextView 업데이트
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA);
        String currentDate = currentDateFormat.format(new Date());
        dateTextView.setText(currentDate);
    }

    private Interpreter getTfliteInterpreter(String modelPath) {
        try {
            return new Interpreter(loadModelFile(getActivity(), modelPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}