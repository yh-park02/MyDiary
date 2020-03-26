package com.jeremy94.portfolio_diary_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean saveLoginData;
    private SharedPreferences appData;

    private EditText id_E,pw_E;
    private String id,pwd,id_S,pw_S;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent();
        id_E=findViewById(R.id.idText);
        pw_E=findViewById(R.id.pwText);

        intent = getIntent();
        if(intent.getStringExtra("id") != null) {
            id_E.setText(intent.getStringExtra("id").toString());
            pw_E.setText(intent.getStringExtra("pw").toString());
        }else{
            id_E.setText("");
            pw_E.setText("");
        }
        checkBox=findViewById(R.id.checkBox);

        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        // 이전에 로그인 정보를 저장시킨 기록이 있다면
        if ("".equals(id_E.getText())&&saveLoginData) {
            id_E.setText(id);
            pw_E.setText(pwd);
            checkBox.setChecked(saveLoginData);
        }

        //회원가입 버튼
        TextView registerButton = (TextView)findViewById(R.id.registerButton);
        //로그인 버튼
        Button loginButton = (Button)findViewById(R.id.btnLogin);
        //이미지버튼
        ImageButton btn_facebook = (ImageButton)findViewById(R.id.btn_facebook);
        ImageButton btn_twitter = (ImageButton)findViewById(R.id.btn_twitter);
        ImageButton btn_git = (ImageButton)findViewById(R.id.btn_git);

        //회원가입 버튼 이벤트
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        //로그인 버튼
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_S=id_E.getText().toString();
                pw_S=pw_E.getText().toString();

                //아이디 미입력 상태-미입력 출력 후 리턴
                if(id_S.length()<=0){
                    Toast.makeText(getApplicationContext(),"아이디 미입력",Toast.LENGTH_LONG).show();
                    return;
                }
                //비밀번호 미입력 상태-미입력 출력 후 리턴
                if(pw_S.length()<=0){
                    Toast.makeText(getApplicationContext(),"비밀번호 미입력",Toast.LENGTH_LONG).show();
                    return;
                }
                save();
                //서브 액티비티 띄우기
                Intent inten_sub = new Intent(getApplicationContext(), FirstActivity.class);
                startActivity(inten_sub);
            }
        });

        //이미지버튼 - 페이스북
        btn_facebook.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com")));
            }
        });
        //이미지버튼 - 트위터
        btn_twitter.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.twitter.com")));
            }
        });
        //이미지버튼 - 깃허브
        btn_git.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com")));
            }
        });

    }//onCreate end



    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", id_E.getText().toString().trim());
        editor.putString("PWD", pw_E.getText().toString().trim());

        // apply, commit 을 안하면 .0변경된 내용이 저장되지 않음
        editor.commit();
    }


    private void load() {
// SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
// 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }
}
