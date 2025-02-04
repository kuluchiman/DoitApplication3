package com.example.doitapplication3;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;


/**
 * 날씨 아이콘
 *
 * ① 맑음
 * ② 구름 조금
 * ③ 구름 많음
 * ④ 흐림
 * ⑤ 비
 * ⑥ 눈/비
 * ⑦ 눈
 */

public class Fragment1 extends Fragment {

    private static final String TAG = "Fragment1";

    RecyclerView recyclerView;
    NoteAdapter adapter;

    Context context;
    OnTabItemSelectedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        if(context instanceof OnTabItemSelectedListener) { //context가 OnTabItemSelectedListener로 형변환이 가능하면 true를 반환한다.
            listener = (OnTabItemSelectedListener) context; //그리고 context를 OnTabItemSelectedListener 형변환해서 listener 변수에 저장
        }// is == 자바의 instanceof (자료형의 일치)
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

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false); //형변환

        initUI(rootView);
        // 데이터 로딩
        loadNoteListData();
        return rootView;
    }

    private void initUI(ViewGroup rootView) { //인플레이션 후에 xml레이아웃 안에 들어 있는 위젯이나
        //레이아웃을 찾아 변수에 할당하는 코드들을 넣기 위해 만들어 둔 것

        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter();

        adapter.addItem(new Note(0, "0", "강남구 삼성동", "", "", "오늘 너무 행복해!", "0", "capture1.jpg", "2월 10일"));
        adapter.addItem(new Note(1, "1", "강남구 삼성동", "", "", "친구와 재미있게 놀았어", "0", "capture1.jpg", "2월 11일"));
        adapter.addItem(new Note(2, "0", "강남구 역삼동", "", "", "집에 왔는데 너무 피곤해 ㅠㅠ", "0", null, "2월 13일"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnNoteItemClickListener() {
            @Override
            public void onItemClick(NoteAdapter.ViewHolder holder, View view, int position) {
                Note item = adapter.getItem(position);

                Log.d(TAG, "아이템 선택됨 : " + item.get_id());

                if (listener != null) {
                    listener.showFragment2(item); //작성화면과 보여주는 화면이 같다.
                } //아이템이 선택됐을때 호출될 함수
            }
        });

    }



    /*public int picNoteData(Timestamp timestamp) {

        AppConstants.println("loadNoteListData called.");
        String sql = "select _id, WEATHER, ADDRESS, LOCATION_X, LOCATION_Y, CONTENTS, MOOD, PICTURE, CREATE_DATE, MODIFY_DATE from "
                + NoteDatabase.TABLE_NOTE + " where CREATE_DATE ="+ timestamp  +" order by CREATE_DATE desc";

        int recordCount = -1;
        NoteDatabase database = NoteDatabase.getInstance(context);
        if (database != null) {
            Log.i("aaaa","pic");
            Cursor outCursor = database.rawQuery(sql);

            recordCount = outCursor.getCount();
            AppConstants.println("record count : " + recordCount + "\n");

            ArrayList<Note> items = new ArrayList<Note>();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String weather = outCursor.getString(1);
                Log.i("aaa",weather);
                String address = outCursor.getString(2);
                String locationX = outCursor.getString(3);
                String locationY = outCursor.getString(4);
                String contents = outCursor.getString(5);
                String mood = outCursor.getString(6);
                String picture = outCursor.getString(7);
                String dateStr = outCursor.getString(8);
                String createDateStr = null;
                if (dateStr != null && dateStr.length() > 10) {
                    try {
                        Date inDate = AppConstants.dateFormat4.parse(dateStr);
                        createDateStr = AppConstants.dateFormat3.format(inDate);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    createDateStr = "";
                }

                AppConstants.println("#" + i + " -> " + _id + ", " + weather + ", " +
                        address + ", " + locationX + ", " + locationY + ", " + contents + ", " +
                        mood + ", " + picture + ", " + createDateStr);

                items.add(new Note(_id, weather, address, locationX, locationY, contents, mood, picture, createDateStr, createWeek ));
            }

            outCursor.close();

            adapter.setItems(items);
            adapter.notifyDataSetChanged();

        }

        return recordCount;
    }*/




    /**
     * 리스트 데이터 로딩
     */
    public int loadNoteListData(){
        AppConstants.println("loadNoteListData called.");

        String sql = "select _id, WEATHER, ADDRESS, LOCATION_X, LOCATION_Y, TITLE, CONTENTS, MOOD, PICTURE, CREATE_DATE, MODIFY_DATE from " + NoteDatabase.TABLE_NOTE + " order by CREATE_DATE desc";

        int recordCount = -1;
        NoteDatabase database = NoteDatabase.getInstance(context);
        if (database != null) {
            Cursor outCursor = database.rawQuery(sql);

            recordCount = outCursor.getCount();
            AppConstants.println("record count : " + recordCount + "\n");

            ArrayList<Note> items = new ArrayList<Note>();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String weather = outCursor.getString(1);
                String address = outCursor.getString(2);
                String locationX = outCursor.getString(3);
                String locationY = outCursor.getString(4);
                String title = outCursor.getString(5);
                String contents = outCursor.getString(6);
                String mood = outCursor.getString(7);
                String picture = outCursor.getString(8);
                String dateStr = outCursor.getString(9);
                String createDateStr = null;
                String createWeekStr = null;
                String writeDateStr = null;
                if (dateStr != null && dateStr.length() > 10) {
                    try {
                        Date inDate = AppConstants.dateFormat4.parse(dateStr);
                        createDateStr = AppConstants.dateFormat6.format(inDate);
                        createWeekStr=AppConstants.dateFormat7.format(inDate);
                        writeDateStr=AppConstants.dateFormat9.format(inDate);

                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    createDateStr = "";
                    createWeekStr = "";
                    writeDateStr = "";
                }




                AppConstants.println("#" + i + " -> " + _id + ", " + weather + ", " +
                        address + ", " + locationX + ", " + locationY + ", " + title + ", "+ contents + ", " +
                        mood + ", " + picture + ", " + createDateStr);

                items.add(new Note(_id, weather, address, locationX, locationY, title, contents, mood, picture, createDateStr, createWeekStr, writeDateStr));
            }

            outCursor.close();

            adapter.setItems(items);
            adapter.notifyDataSetChanged();

        }

        return recordCount;
    }


}