package com.jeremy94.portfolio_diary_v2.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jeremy94.portfolio_diary_v2.NewsActivity;
import com.jeremy94.portfolio_diary_v2.R;

public class Fragment_2 extends Fragment implements View.OnClickListener{

    ImageButton Ibtn_naver, Ibtn_daum, Ibtn_google, Ibtn_news;
    Button btn_http;
    EditText edt_url;
    WebView webView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = (ViewGroup)inflater.inflate(R.layout.fragment_2, container, false);

        edt_url = (EditText) rootview.findViewById(R.id.editText3);
        webView = (WebView)rootview.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Ibtn_naver = (ImageButton) rootview.findViewById(R.id.button2);
        Ibtn_daum = (ImageButton) rootview.findViewById(R.id.button3);
        Ibtn_google = (ImageButton) rootview.findViewById(R.id.button4);
        Ibtn_news = (ImageButton) rootview.findViewById(R.id.button5);
        btn_http = (Button) rootview.findViewById(R.id.button7);

        Ibtn_naver.setOnClickListener(this);
        Ibtn_daum.setOnClickListener(this);
        Ibtn_google.setOnClickListener(this);
        Ibtn_news.setOnClickListener(this);
        btn_http.setOnClickListener(this);

        return rootview;
    }


    @Override
    public void onClick(View v) {
        if(v==Ibtn_naver){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.naver.com"));
            startActivity(intent);
        }else if(v==Ibtn_daum){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.daum.net"));
            startActivity(intent);
        }else if(v==Ibtn_google){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.kr"));
            startActivity(intent);
        }else if(v==Ibtn_news){
            Intent intent = new Intent(getActivity(), NewsActivity.class);
            startActivity(intent);

        }else if(v==btn_http){
            String url = edt_url.getText().toString();//m.naver.com
            // Toast.makeText(getContext(),url, Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+url));
            // startActivity(intent);
            webView.loadUrl("https://" +url);

        }
    }

}