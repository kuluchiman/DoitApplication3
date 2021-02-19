package com.example.doitapplication3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.github.channguyen.rsv.RangeSliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class Fragment2 extends Fragment {

    private static final String TAG = "Fragment2";

    Context context;
    OnTabItemSelectedListener listener;
    OnRequestListener requestListener;

    ImageView weatherIcon;
    TextView dateTextView;
    TextView locationTextView;

    EditText titleInput;
    EditText contentsInput;
    ImageView pictureImageView;

    boolean isPhotoCaptured;
    boolean isPhotoFileSaved;
    boolean isPhotoCanceled;

    int selectedPhotoMenu;

    File file;
    Bitmap resultPhotoBitmap;

    int mMode = AppConstants.MODE_INSERT;

    int _id = -1;

    int weatherIndex = 0;

    RangeSliderView moodSlider;
    int moodIndex = 2;

    Note item;
    String filePath;



    @Override
    public void onAttach(Context context) { //리싸이클러뷰 연결할때 먼저실행
        //context는 내가 붙는 플래그 먼트를 말함 여기서는 main액티비티다.
        super.onAttach(context);

        this.context = context;

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }

        if (context instanceof OnRequestListener) {
            requestListener = (OnRequestListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context = null;
            listener = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toast.makeText(context, "onCreateView called.", Toast.LENGTH_LONG).show();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false); //형변환

        initUI(rootView);
        // check current location
        if (requestListener != null) {
            requestListener.onRequest("getCurrentLocation");
        }

        applyItem();

        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        weatherIcon = rootView.findViewById(R.id.weatherIcon);
        dateTextView = rootView.findViewById(R.id.dateTextView);
        locationTextView = rootView.findViewById(R.id.locationTextView);

        titleInput = rootView.findViewById(R.id.titleInput);

        contentsInput = rootView.findViewById(R.id.contentsInput);
        pictureImageView = rootView.findViewById(R.id.pictureImageView);

        contentsInput = rootView.findViewById(R.id.contentsInput);
        pictureImageView = rootView.findViewById(R.id.pictureImageView);




        pictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPhotoCaptured || isPhotoFileSaved) {
                    showDialog(AppConstants.CONTENT_PHOTO_EX);
                } else {
                    showDialog(AppConstants.CONTENT_PHOTO); //p_사진 메뉴를 포함한 대화상자 띄우기
                }
            }
        });

        moodSlider = rootView.findViewById(R.id.sliderView);

        Button saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mMode == AppConstants.MODE_INSERT) {
                    saveNote();
                } else if(mMode == AppConstants.MODE_MODIFY) {
                    modifyNote();
                }

                if (listener != null) {
                    listener.onTabSelected(0);// 리스트 화면으로 간다.
                }
            }
        });

        Button deleteButton = rootView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteNote();

                if (listener != null) {
                    listener.onTabSelected(0);
                }
            }
        });

        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (listener != null) {
                    listener.onTabSelected(0); //0번 리스트화면
                }
            }
        });

        RangeSliderView sliderView = rootView.findViewById(R.id.sliderView);
        final RangeSliderView.OnSlideListener listener = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                AppConstants.println("moodIndex changed to " + index);
                moodIndex = index;
                Toast.makeText(context, "moodIndex changed to " + index, Toast.LENGTH_LONG).show();
            }
        };

        sliderView.setOnSlideListener(listener);
        sliderView.setInitialIndex(2);

    }

    /**
     * ① 맑음
     * ② 구름 조금
     * ③ 구름 많음
     * ④ 흐림
     * ⑤ 비
     * ⑥ 눈/비
     * ⑦ 눈
     *
     */
    public void setWeather(String data) {
        if (data != null) {
            if (data.equals("맑음")) {
                weatherIcon.setImageResource(R.drawable.weather_1);
                weatherIndex = 0;
            } else if (data.equals("구름 조금")) {
                weatherIcon.setImageResource(R.drawable.weather_2);
                weatherIndex = 1;
            } else if (data.equals("구름 많음")) {
                weatherIcon.setImageResource(R.drawable.weather_3);
                weatherIndex = 2;
            } else if (data.equals("흐림")) {
                weatherIcon.setImageResource(R.drawable.weather_4);
                weatherIndex = 3;
            } else if (data.equals("비")) {
                weatherIcon.setImageResource(R.drawable.weather_5);
                weatherIndex = 4;
            } else if (data.equals("눈/비")) {
                weatherIcon.setImageResource(R.drawable.weather_6);
                weatherIndex = 5;
            } else if (data.equals("눈")) {
                weatherIcon.setImageResource(R.drawable.weather_7);
                weatherIndex = 6;
            } else {
                Log.d("Fragment2", "Unknown weather string : " + data);
            }
        }
    }

    public void setWeatherIndex(int index) {
        if (index == 0) {
            weatherIcon.setImageResource(R.drawable.weather_1);
            weatherIndex = 0;
        } else if (index == 1) {
            weatherIcon.setImageResource(R.drawable.weather_2);
            weatherIndex = 1;
        } else if (index == 2) {
            weatherIcon.setImageResource(R.drawable.weather_3);
            weatherIndex = 2;
        } else if (index == 3) {
            weatherIcon.setImageResource(R.drawable.weather_4);
            weatherIndex = 3;
        } else if (index == 4) {
            weatherIcon.setImageResource(R.drawable.weather_5);
            weatherIndex = 4;
        } else if (index == 5) {
            weatherIcon.setImageResource(R.drawable.weather_6);
            weatherIndex = 5;
        } else if (index == 6) {
            weatherIcon.setImageResource(R.drawable.weather_7);
            weatherIndex = 6;
        } else {
            Log.d("Fragment2", "Unknown weather index : " + index);
        }

    }



    public void setAddress(String data) {
        locationTextView.setText(data);
    }

    public void setDateString(String dateString) {
        dateTextView.setText(dateString);
    }

    public void setContents(String data) {
        contentsInput.setText(data);
    }

    public void setTitle(String data) {
        titleInput.setText(data);
    }

    public void setPicture(String picturePath, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        Bitmap resultPhotoBitmap = BitmapFactory.decodeFile(picturePath, options);

        pictureImageView.setImageBitmap(resultPhotoBitmap);
    }

    public void setMood(String mood) {
        try {
            moodIndex = Integer.parseInt(mood);
            moodSlider.setInitialIndex(moodIndex);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setItem(Note item) {
        this.item = item;
    }

    public void applyItem() {
        AppConstants.println("applyItem called.");

        if (item != null) {
            mMode = AppConstants.MODE_MODIFY;


            setWeatherIndex(Integer.parseInt(item.getWeather()));
            setAddress(item.getAddress());

            setDateString(item.getWriteDateStr());
            //setDateString(item.getCreateDateStr());

            setContents(item.getContents());
            setTitle(item.getTitle());

            String picturePath = item.getPicture();
            if (picturePath == null || picturePath.equals("")) {
                pictureImageView.setImageResource(R.drawable.no_image);
            } else {
                setPicture(item.getPicture(), 1);
            }

            setMood(item.getMood());
        } else {
            mMode = AppConstants.MODE_INSERT;

            setWeatherIndex(0);
            setAddress("");

            Date currentDate = new Date();
            String currentDateString = AppConstants.dateFormat9.format(currentDate);
            setDateString(currentDateString);

            contentsInput.setText("");
            titleInput.setText("");
            pictureImageView.setImageResource(R.drawable.no_image);
            setMood("2");
        }

    }


    public void showDialog(int id) {
        AlertDialog.Builder builder = null;

        switch(id) {

            case AppConstants.CONTENT_PHOTO:
                builder = new AlertDialog.Builder(context); //대화상자 객체를 만들기 위한 빌더 객체 생성

                builder.setTitle("사진 메뉴 선택");
                builder.setSingleChoiceItems(R.array.array_photo, 0, new DialogInterface.OnClickListener() {//대화상자에 [메뉴] 버튼 설정
                    //setSingleChoiceItems 대화상자 안에 메소드가 보이도록 해준다.
                    //인자값은 메뉴얼로보일 아이템들을 위한 문자값 배열, 이벤트 처리를 위한 리스너
                    public void onClick(DialogInterface dialog, int whichButton) { //메뉴 선택할때 선택된 메뉴의 인덱스 확인
                        selectedPhotoMenu = whichButton;//사용자가 메뉴중 하나를 선택하면 어떤 메뉴를 선택했는지 그 인덱스 값을 selectedPhotoMenu 변수에 할당
                    }
                });
                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) { //선택버튼 눌렀을때 이벤트 처리
                        if(selectedPhotoMenu == 0 ) {
                            showPhotoCaptureActivity(); //사진촬영 메소드 실행
                        } else if(selectedPhotoMenu == 1) {
                            showPhotoSelectionActivity(); //앨범선택 메소드 실행
                        }
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                break;

            case AppConstants.CONTENT_PHOTO_EX:
                builder = new AlertDialog.Builder(context);

                builder.setTitle("사진 메뉴 선택");
                builder.setSingleChoiceItems(R.array.array_photo_ex, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        selectedPhotoMenu = whichButton;
                    }
                });
                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(selectedPhotoMenu == 0) {
                            showPhotoCaptureActivity();
                        } else if(selectedPhotoMenu == 1) {
                            showPhotoSelectionActivity();
                        } else if(selectedPhotoMenu == 2) {
                            isPhotoCanceled = true;
                            isPhotoCaptured = false;

                            pictureImageView.setImageResource(R.drawable.picture1);
                        }
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                break;

            default:
                break;
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showPhotoCaptureActivity() { //사진찍기 선택후 메소드
        if (file == null) {
            file = createFile();
        }

        Uri fileUri = FileProvider.getUriForFile(context,"com.example.doitapplication2.fileprovider", file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(intent, AppConstants.REQ_PHOTO_CAPTURE);//사진찍기는 REQ_PHOTO_CAPTURE 이 상수가 사용됨
        }
    }

    private File createFile() {
        String filename = "capture.jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File outFile = new File(storageDir, filename);

        return outFile;
    }

    public void showPhotoSelectionActivity() { //앨범에서 사진선택 시
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AppConstants.REQ_PHOTO_SELECTION);
    }

    /**
     * 다른 액티비티로부터의 응답 처리
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent != null) {
            switch (requestCode) {
                case AppConstants.REQ_PHOTO_CAPTURE:  // 사진 찍는 경우
                    Log.d(TAG, "onActivityResult() for REQ_PHOTO_CAPTURE.");

                    Log.d(TAG, "resultCode : " + resultCode);

                    //setPicture(file.getAbsolutePath(), 8);
                    resultPhotoBitmap = decodeSampledBitmapFromResource(file, pictureImageView.getWidth(), pictureImageView.getHeight());
                    pictureImageView.setImageBitmap(resultPhotoBitmap);

                    break;

                case AppConstants.REQ_PHOTO_SELECTION:  // 사진을 앨범에서 선택하는 경우
                    Log.d(TAG, "onActivityResult() for REQ_PHOTO_SELECTION.");

                    Uri selectedImage = intent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    Log.i("selecte", filePathColumn[0]+"");
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    Log.i("file", filePath);
                    cursor.close();

                    resultPhotoBitmap = decodeSampledBitmapFromResource(new File(filePath), pictureImageView.getWidth(), pictureImageView.getHeight());
                    pictureImageView.setImageBitmap(resultPhotoBitmap);
                    isPhotoCaptured = true;

                    break;

            }
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(File res, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(res.getAbsolutePath(),options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(res.getAbsolutePath(),options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height;
            final int halfWidth = width;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    private String createFilename() {
        Date curDate = new Date();
        String curDateStr = String.valueOf(curDate.getTime());

        return curDateStr;
    }

    private String savePicture() {
        if (resultPhotoBitmap == null) {
            AppConstants.println("No picture to be saved.");
            return "";
        }

        File photoFolder = new File(AppConstants.FOLDER_PHOTO);

        if(!photoFolder.isDirectory()) {
            Log.d(TAG, "creating photo folder : " + photoFolder);
            photoFolder.mkdirs();
        }

        String photoFilename = createFilename();
        String picturePath = photoFolder + File.separator + photoFilename;
        String picturePath2=picturePath;

        try {
            FileOutputStream outstream = new FileOutputStream(picturePath);
            resultPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return picturePath2;
    }

    /**
     * 데이터베이스 레코드 추가
     */
    private void saveNote() {
        String address = locationTextView.getText().toString();
        String contents = contentsInput.getText().toString();
        String title = titleInput.getText().toString();

        String picturePath = filePath; // savePicture();

        String sql = "insert into " + NoteDatabase.TABLE_NOTE +
                "(WEATHER, ADDRESS, LOCATION_X, LOCATION_Y, TITLE, CONTENTS, MOOD, PICTURE) values(" +
                "'"+ weatherIndex + "', " +
                "'"+ address + "', " +
                "'"+ "" + "', " +
                "'"+ "" + "', " +
                "'"+ title + "', " +
                "'"+ contents + "', " +
                "'"+ moodIndex + "', " +
                "'"+ picturePath + "')";

        Log.d(TAG, "sql : " + sql);
        NoteDatabase database = NoteDatabase.getInstance(context);
        database.execSQL(sql);

    }

    /**
     * 데이터베이스 레코드 수정
     */
    private void modifyNote() {
        if (item != null) {
            String address = locationTextView.getText().toString();
            String contents = contentsInput.getText().toString();
            String title = titleInput.getText().toString();

            String picturePath = filePath; //savePicture();

            // update note
            String sql = "update " + NoteDatabase.TABLE_NOTE +
                    " set " +
                    "   WEATHER = '" + weatherIndex + "'" +
                    "   ,ADDRESS = '" + address + "'" +
                    "   ,LOCATION_X = '" + "" + "'" +
                    "   ,LOCATION_Y = '" + "" + "'" +
                    "   ,TITLE = '" + title + "'" +
                    "   ,CONTENTS = '" + contents + "'" +
                    "   ,MOOD = '" + moodIndex + "'" +
                    "   ,PICTURE = '" + picturePath + "'" +
                    " where " +
                    "   _id = " + item._id;

            Log.d(TAG, "sql : " + sql);
            NoteDatabase database = NoteDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }


    /**
     * 레코드 삭제
     */
    private void deleteNote() {
        AppConstants.println("deleteNote called.");

        if (item != null) {
            // delete note
            String sql = "delete from " + NoteDatabase.TABLE_NOTE +
                    " where " +
                    "   _id = " + item._id;

            Log.d(TAG, "sql : " + sql);
            NoteDatabase database = NoteDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }






}