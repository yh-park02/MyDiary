package com.jeremy94.portfolio_diary_v2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.EntityUtils;

public class addSchedule extends AppCompatActivity {

    //Button btn_cancel, btn_save;
    Calendar myCalendar = Calendar.getInstance();

    EditText editTitle,editDate,editTime,editPosition,editMemo;
    String et,ed,et2,ep,em;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

         editTitle = findViewById(R.id.editTitle);
         editDate = findViewById(R.id.editDate);
         editTime = findViewById(R.id.editTime);
         editPosition = findViewById(R.id.editPosition);
         editMemo = findViewById(R.id.editMemo);

        EditText et_Date = (EditText) findViewById(R.id.editDate);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(addSchedule.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                mTimePicker = new TimePickerDialog(addSchedule.this, new TimePickerDialog.OnTimeSetListener() {
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

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    //출력형식 2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.editDate);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    //버튼이벤트
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_S:



                setText();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", et);
                resultIntent.putExtra("date", ed);
                resultIntent.putExtra("time", et2);
                resultIntent.putExtra("position", ep);
                resultIntent.putExtra("memo", em);

                setResult(RESULT_OK, resultIntent);
                new SendPost().execute();

                break;

            //case R.id.btn_C:
            //   finish();
            //    break;

            case RESULT_CANCELED:
                setResult(RESULT_CANCELED); //취소 결과 값 설정
                break;
        }
        finish();
    }

    public void setText(){
        et=editTitle.getText().toString();
        ed=editDate.getText().toString();
        et2=editTime.getText().toString();
        ep=editPosition.getText().toString();
        em=editMemo.getText().toString();
    }

    private class SendPost extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... unused) {
            String content = executeClient();
            return content;
        }


        public String executeClient() {
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            //post.add(new BasicNameValuePair("id", myId));
            //post.add(new BasicNameValuePair("pass", pass));

            setText();
            //post.add(new BasicNameValuePair("title", "title"));
            //post.add(new BasicNameValuePair("detail", et));
            post.add(new BasicNameValuePair("startDate", ed));
            post.add(new BasicNameValuePair("endDate", et2));
            post.add(new BasicNameValuePair("loc", ep));
            //post.add(new BasicNameValuePair("nickname", em));
            //post.add(new BasicNameValuePair("gender", gender_S));



            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params =  client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            // Post객체 생성
            //HttpPost httpPost = new HttpPost("http://192.168.0.171/FO/3.php");
            HttpPost httpPost = new HttpPost("http://192.168.0.171/d.php");

            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
                httpPost.setEntity(entity);
                client.execute(httpPost);
                return EntityUtils.getContentCharSet(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
