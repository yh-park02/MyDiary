package com.jeremy94.portfolio_diary_v2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class modifySchedule extends AppCompatActivity {

    EditText editTitle;
    EditText editDate;
    EditText editTime;
    EditText editPosition;
    EditText editMemo;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_schedule);

        Intent receivedIntent = getIntent();

        String title = receivedIntent.getStringExtra("title");
        String date = receivedIntent.getStringExtra("date");
        String time = receivedIntent.getStringExtra("time");
        String position = receivedIntent.getStringExtra("position");
        String memo = receivedIntent.getStringExtra("memo");

        editTitle = (EditText) findViewById(R.id.editTitle);
        editDate = (EditText) findViewById(R.id.editDate);
        editTime = (EditText) findViewById(R.id.editTime);
        editPosition = (EditText) findViewById(R.id.editPosition);
        editMemo = (EditText) findViewById(R.id.editMemo);

        //새로 입력 수정된 editText 를 set
        editTitle.setText(title);
        editDate.setText(date);
        editTime.setText(time);
        editPosition.setText(position);
        editMemo.setText(memo);

        EditText et_Date = (EditText) findViewById(R.id.editDate);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(modifySchedule.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final EditText et_time = (EditText) findViewById(R.id.editTime);
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(modifySchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        et_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });



    }//onCreate end

    private void updateLabel(){
        String myFormat = "yyyy/MM/dd";    //출력형식 2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.editDate);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    //버튼 이벤트
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_S:
                Intent resultIntent = new Intent();

                resultIntent.putExtra("title", editTitle.getText().toString());
                resultIntent.putExtra("date", editDate.getText().toString());
                resultIntent.putExtra("time", editTime.getText().toString());
                resultIntent.putExtra("position", editPosition.getText().toString());
                resultIntent.putExtra("memo", editMemo.getText().toString());
                setResult(RESULT_OK, resultIntent);
                break;

            case R.id.btn_C:
                setResult(RESULT_CANCELED); //취소 결과 값 설정
                break;

        }
        finish();
    }
}
