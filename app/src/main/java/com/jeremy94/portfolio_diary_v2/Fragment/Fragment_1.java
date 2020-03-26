package com.jeremy94.portfolio_diary_v2.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jeremy94.portfolio_diary_v2.CalendarDecorate.oneDayDecorator;
import com.jeremy94.portfolio_diary_v2.CalendarDecorate.SaturdayDecorator;
import com.jeremy94.portfolio_diary_v2.CalendarDecorate.SundayDecorator;
import com.jeremy94.portfolio_diary_v2.MyScheduleData;
import com.jeremy94.portfolio_diary_v2.R;
import com.jeremy94.portfolio_diary_v2.addSchedule;
import com.jeremy94.portfolio_diary_v2.modifySchedule;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Fragment_1 extends Fragment {

    private final com.jeremy94.portfolio_diary_v2.CalendarDecorate.oneDayDecorator oneDayDecorator = new oneDayDecorator();

    MaterialCalendarView McalendarView;
    private TextView tv;
    private Button btn_add;

    private ListView mListView;
    private ArrayAdapter mAdapter;
    private ArrayList<MyScheduleData> mList;

    //activity 호출을 구분하기 위한 상수 설정
    public final static  int SUB_ACTIVITY_1 = 100;
    public final static  int SUB_ACTIVITY_2 = 101;

    MyScheduleData selectedData;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = (ViewGroup)inflater.inflate(R.layout.fragment_1, container, false);

        //데이터 초기화
        mList = new ArrayList<MyScheduleData>();
        //mList.add(new MyScheduleData("치과예약","2월20일", "7시", "종각", "사랑니발치"));

        //어댑터생성
        mAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mList);

        //어댑터 설정, 리스트뷰 연결
        mListView= (ListView) rootview.findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();


        //리스트뷰 항목 클릭햇을 때
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedData = mList.get(i);

                Intent intent = new Intent(getActivity(), modifySchedule.class);
                //롱클릭하여 선택한 데이터값을 저장하여 intent로 넘겨줌
                intent.putExtra("title", selectedData.getTitle());
                intent.putExtra("date", selectedData.getDate());
                intent.putExtra("time", selectedData.getTime());
                intent.putExtra("position", selectedData.getPosition());
                intent.putExtra("memo", selectedData.getMemo());

                startActivityForResult(intent, SUB_ACTIVITY_2);
            }
        });



        //롱클릭했을 때 AlertDialog 띄우기(삭제 여부)
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                selectedData = mList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        mList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            }
        });

        //일정추가버튼
        btn_add = rootview.findViewById(R.id.button);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), addSchedule.class);
                startActivityForResult(intent, SUB_ACTIVITY_1);
            }
        });


        tv = rootview.findViewById(R.id.textView);
        //텍스트뷰에 오늘 날짜 띄우기
        tv.setText(Calendar.getInstance().get(Calendar.YEAR)+
                "년 "+(Calendar.getInstance().get(Calendar.MONTH)+1)+
                "월 "+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"일");

        McalendarView = (MaterialCalendarView)rootview.findViewById(R.id.calendarView);

        //MaterialCalanderView의 기본 설정
        McalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 1, 1))
                .setMaximumDate(CalendarDay.from(3000, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        //달력 꾸미기
        McalendarView.addDecorators(
                new SaturdayDecorator(),
                new SundayDecorator(),
                oneDayDecorator);

        //날짜가 변경될 때 이벤트를 받기 위한 리스너(달력에 날짜 클릭시 텍스트뷰에 해당 날짜 띄우기)
        McalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();
                int y=date.getYear();
                int m=date.getMonth()+1;
                int d=date.getDay();
                String date2=y+"년 "+m+"월 "+d +"일";
                tv.setText(date2);
            }
        });

        return  rootview;
    }


    //onActivityResult : A(activity)에서 B(activity)로 갔다가 다시 A로 넘어올 때 사용하는, 안드로이드에서 제공하는 기본 메소드
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUB_ACTIVITY_1) {
            switch (resultCode) {
                case RESULT_OK:
                    String title = data.getStringExtra("title");
                    String date = data.getStringExtra("date");
                    String time = data.getStringExtra("time");
                    String position = data.getStringExtra("position");
                    String memo = data.getStringExtra("memo");

                    mList.add(new MyScheduleData(title, date, time, position, memo));
                    mAdapter.notifyDataSetChanged();
                    break;

                case RESULT_CANCELED:
                    //Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else if (requestCode == SUB_ACTIVITY_2) {
            switch (resultCode) {
                case RESULT_OK:
                    selectedData.setTitle(data.getStringExtra("title"));
                    selectedData.setDate(data.getStringExtra("date"));
                    selectedData.setTime(data.getStringExtra("time"));
                    selectedData.setPosition(data.getStringExtra("position"));
                    selectedData.setMemo(data.getStringExtra("memo"));
                    mAdapter.notifyDataSetChanged();
                    break;

                case RESULT_CANCELED:
                    //Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
