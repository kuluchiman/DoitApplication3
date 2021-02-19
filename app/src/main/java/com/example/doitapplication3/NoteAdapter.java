package com.example.doitapplication3;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements OnNoteItemClickListener {

    ArrayList<Note> items = new ArrayList<Note>();
    OnNoteItemClickListener listener;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.note_item, viewGroup, false);

        return new ViewHolder(itemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Note item = items.get(position);
        viewHolder.setItem(item);
        Log.i("55", String.valueOf(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Note item) {
        items.add(item);
    }

    public void setItems(ArrayList<Note> items) {
        this.items = items;
    }

    public Note getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnNoteItemClickListener listener) {
        this.listener = listener; //클릭을 하면 listener에 값이 들어감
    }


    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) { //클릭이 됐으면
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;

        ImageView moodImageView;

        ImageView pictureExistsImageView; //사진이 있는지 표시 되는 조그만 이미지
        ImageView pictureImageView;

        ImageView weatherImageView;

        //TextView contentsTextView;

        //TextView locationTextView;

        TextView dateTextView;
        TextView week;



        public ViewHolder(View itemView, final OnNoteItemClickListener listener) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);

            moodImageView = itemView.findViewById(R.id.moodImageView);


            //pictureExistsImageView = itemView.findViewById(R.id.pictureExistsImageView);
            pictureImageView = itemView.findViewById(R.id.pictureImageView);

            weatherImageView = itemView.findViewById(R.id.weatherImageView);

            //contentsTextView = itemView.findViewById(R.id.contentsTextView);

            //locationTextView = itemView.findViewById(R.id.locationTextView);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            week = itemView.findViewById(R.id.week);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

        }

        public void setItem(Note item) {//item은 클릭된 애
            String mood = item.getMood(); //클릭한 애에 해당하는 무드 note에서 가져오기
            int moodIndex = Integer.parseInt(mood); //정수 값으로 변경해서 변수에 넣기
            setMoodImage(moodIndex);

            String picturePath = item.getPicture(); // 사진이 있으면
            Log.i("222222222",picturePath);
            if (picturePath != null && !picturePath.equals("")) { //사진 이미지 보이도록
                pictureImageView.setVisibility(View.VISIBLE);
                pictureImageView.setImageURI(Uri.parse("file://" + picturePath));


            } else {
                pictureImageView.setVisibility(View.VISIBLE);
                pictureImageView.setImageResource(R.drawable.noimagefound);
            }

            // set weather
            String weather = item.getWeather();
            int weatherIndex = Integer.parseInt(weather);
            setWeatherImage(weatherIndex);

            // contentsTextView.setText(item.getContents());

            //locationTextView.setText(item.getAddress());


            dateTextView.setText(item.getCreateDateStr());

            week.setText(item.getCreateWeekStr());

            //dateTextView.setText(item.getDayStr());

            /*String weekStr = AppConstants.dateFormat7.format(item.getCreateDateStr());
            week.setText(weekStr);
            Log.i("weekStr",weekStr);*/
        }

        private void setWeatherImage(int weatherIndex) { // 정수값으로 받아서 해당하는 값의 이미지를 보여준다.
            switch(weatherIndex) { //weatherIndex따라서 이미지 변경
                case 0:
                    weatherImageView.setImageResource(R.drawable.weather_icon_1);
                    break;
                case 1:
                    weatherImageView.setImageResource(R.drawable.weather_icon_2);
                    break;
                case 2:
                    weatherImageView.setImageResource(R.drawable.weather_icon_3);
                    break;
                case 3:
                    weatherImageView.setImageResource(R.drawable.weather_icon_4);
                    break;
                case 4:
                    weatherImageView.setImageResource(R.drawable.weather_icon_5);
                    break;
                case 5:
                    weatherImageView.setImageResource(R.drawable.weather_icon_6);
                    break;
                case 6:
                    weatherImageView.setImageResource(R.drawable.weather_icon_7);
                    break;
                default:
                    weatherImageView.setImageResource(R.drawable.weather_icon_1);
                    break;
            }
        }

        private void setMoodImage(int moodIndex) {
            switch(moodIndex) { //moodIndex값에 따라서 이미지를 변경함
                case 0:
                    moodImageView.setImageResource(R.drawable.smile1_48);
                    break;
                case 1:
                    moodImageView.setImageResource(R.drawable.smile2_48);
                    break;
                case 2:
                    moodImageView.setImageResource(R.drawable.smile3_48);
                    break;
                case 3:
                    moodImageView.setImageResource(R.drawable.smile4_48);
                    break;
                case 4:
                    moodImageView.setImageResource(R.drawable.smile5_48);
                    break;
                default:
                    moodImageView.setImageResource(R.drawable.smile3_48);
                    break;
            }
        }



    }
}

