package com.jeremy94.portfolio_diary_v2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;



import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

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





public class RegisterActivity extends AppCompatActivity {

    EditText id_email,pw,pw_re,name,nickname;
    RadioGroup RG;
    RadioButton man,woman;
    Button btn1,btn2;
    String id_S,pw_S,name_S,nickname_S,gender_S;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        id_email=findViewById(R.id.editText2);
        pw=findViewById(R.id.editText5);
        pw_re=findViewById(R.id.editText);
        name=findViewById(R.id.editText6);
        nickname=findViewById(R.id.editText9);

        RG=findViewById(R.id.radiogroup);
        RG.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        man=findViewById(R.id.radioButton);
        woman=findViewById(R.id.radioButton2);

        btn1=findViewById(R.id.button7);
        btn2=findViewById(R.id.button8);

        id_S="";
        pw_S="";
        name_S="";
        nickname_S="";
        gender_S="남자";



        intent = new Intent(RegisterActivity.this,MainActivity.class);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_email.length()<=0){
                    Toast.makeText(getApplicationContext(),"아이디 미입력",Toast.LENGTH_LONG).show();
                    return;
                }
                if(pw.length()<=0){
                    Toast.makeText(getApplicationContext(),"비밀번호 미입력",Toast.LENGTH_LONG).show();
                    return;
                }
                if(pw_re.length()<=0){
                    Toast.makeText(getApplicationContext(),"비밀번호 확인 미입력",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!pw.getText().toString().equals(pw_re.getText().toString())){
                    Toast.makeText(getApplicationContext(),"비밀번호 틀림",Toast.LENGTH_LONG).show();
                    return;
                }
                if(name.length()<=0){
                    Toast.makeText(getApplicationContext(),"이름 미입력",Toast.LENGTH_LONG).show();
                    return;
                }
                if(nickname.length()<=0){
                    Toast.makeText(getApplicationContext(),"닉네임 미입력",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!checkEmail(id_email.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"이메일 주소가 올바르지 않습니다",Toast.LENGTH_LONG).show();
                    return;
                }

                id_S=id_email.getText().toString();
                pw_S=pw.getText().toString();
                name_S=name.getText().toString();
                nickname_S=nickname.getText().toString();



                new SendPost().execute();
                intent.putExtra("id",id_S);
                intent.putExtra("pw",pw_S);

                startActivity(intent);
                finish();
            }
        });



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






        //전송

    }


    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.radioButton){
                gender_S="남자";
            }
            else if(i == R.id.radioButton2){
                gender_S="여자";
            }
        }
    };

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
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

            //post.add(new BasicNameValuePair("title", "title"));
            post.add(new BasicNameValuePair("email", id_S));
            post.add(new BasicNameValuePair("pw", pw_S));
            post.add(new BasicNameValuePair("name", name_S));
            post.add(new BasicNameValuePair("nickname", nickname_S));
            //post.add(new BasicNameValuePair("gender", gender_S));



            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params =  client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            // Post객체 생성
            //HttpPost httpPost = new HttpPost("http://192.168.0.171/FO/3.php");
            HttpPost httpPost = new HttpPost("http://192.168.0.171/c.php");

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
