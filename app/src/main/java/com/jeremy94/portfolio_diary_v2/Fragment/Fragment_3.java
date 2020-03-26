
package com.jeremy94.portfolio_diary_v2.Fragment;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;


import android.provider.ContactsContract;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.jeremy94.portfolio_diary_v2.AlbumActivity;
import com.jeremy94.portfolio_diary_v2.CurrentData;
import com.jeremy94.portfolio_diary_v2.R;
import com.jeremy94.portfolio_diary_v2.RetrofitFactory;
import com.jeremy94.portfolio_diary_v2.RetrofitService2;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_3 extends Fragment implements View.OnClickListener{

    private final static String API_KEY="7e150be573ae969360fff718733ed08a";

    Button btn_map, btn_addr,btn_album;
    ImageView symbolView;
    TextView temperatureView;
    TextView upView;
    TextView downView;

    RetrofitService2 networkService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = (ViewGroup)inflater.inflate(R.layout.new1, container, false);

        temperatureView = (rootview).findViewById(R.id.temper_text);
        upView = (rootview).findViewById(R.id.up_text);
        downView = (rootview).findViewById(R.id.down_text);
        symbolView = (rootview).findViewById(R.id.symbol);
        btn_map = (rootview).findViewById(R.id.btn1);
        btn_addr = (rootview).findViewById(R.id.btn2);
        btn_album = (rootview).findViewById(R.id.btn3);

        btn_map.setOnClickListener(this);
        btn_addr.setOnClickListener(this);
        btn_album.setOnClickListener(this);

        networkService = (RetrofitService2)RetrofitFactory.create2();
        networkService.getCurrentData("seoul", "json", "metric", API_KEY)
                .enqueue(new Callback<CurrentData>() {
                    @Override
                    public void onResponse(Call<CurrentData> call, Response<CurrentData> response) {
                        if(response.isSuccessful()){
                            CurrentData data = response.body();
                            temperatureView.setText(String.valueOf(data.main.temp));
                            upView.setText(String.valueOf(data.main.temp_max));
                            downView.setText(String.valueOf(data.main.temp_min));

                            Glide.with(getActivity()).load(
                                    "http://openweathermap.org/img/w/"+
                                            data.weather.get(0).icon+".png").into(symbolView);
                            Log.i("success=====", "success test");
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentData> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

        return rootview;
    }

    @Override
    public void onClick(View v) {
        if(v==btn_addr){
            //주소록 목록 띄우기
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, 10);

        }else if(v==btn_map){
            //지도연동
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:37.5662952,126.9779451"));
            startActivity(intent);
        }else if(v==btn_album){
            Intent intent = new Intent(getActivity(), AlbumActivity.class);
            startActivity(intent);
        }
    }

}

