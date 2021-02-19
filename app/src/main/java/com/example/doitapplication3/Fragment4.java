package com.example.doitapplication3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

public class Fragment4 extends Fragment {

    private static final String TAG = "Fragment4";

    DatePicker datePicker;
    String filename;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false); //형변환

        initUI(rootView);
        // 데이터 로딩
        //loadNoteListData();
        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        datePicker = rootView.findViewById(R.id.dataPicker1);
        Calendar cal=Calendar.getInstance();
        int cYear=cal.get(Calendar.YEAR);
        int cMonth=cal.get(Calendar.MONTH)+1;
        int cDay=cal.get(Calendar.DAY_OF_MONTH);

        datePicker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day_of_month) {
                filename=year+"_"+(month+1)+"_"+day_of_month+".txt";

            }
        });
    }
}