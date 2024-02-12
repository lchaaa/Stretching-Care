package com.example.stretching;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//굽은목 허리 다리교정

//팝업 창에 표시할 이미지
public class MainActivity extends AppCompatActivity {
    private int[] popupImages = {
            R.drawable.i,
            R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
            R.drawable.i4,
    };

    //팝업 창에 표시할 텍스트
    private String[] popupTexts = {
            "스트레칭 준비\n\n1) 스트레칭을 올바른 자세로 준비하세요.\n\n* 타이머 가 끝나기 5초 전에 사운드가 재생    됩니다.",
            "굽은 목과 허리 교정\n\n1) 양반다리를 하고 앉은 상태로 양손은 깍지를 껴 머리 뒤에 댄다.\n\n2) 허리를 곧게 편 상태로 양 팔꿈치를 앞으로 당겨 10초간 버틴다.",
            "굽은 허리와 다리 교정\n\n1) 허리를 반 접은 후 양손으로 바닥을 짚는다.\n\n2) 상태 그대로 왼발과 오른발의 무릎을 굽혀 허리와 허벅지를 늘려준다.",
            "굽은 허리 교정\n\n1) 곧게 선 채로 양손은 마주 보게 잡아 머리 위로 쭉 펴 올린다.\n\n2) 상태 그대로 왼쪽과 오른쪽으로 허리를 굽혀 10초간 버티며 반대편 허리를 펴준다.",
            "굽은 허리와 다리 교정 2\n\n1) 곧게 선 채로 양손을 마주 보게 잡아 머리 위로 쭉 펴 올린다.\n\n2) 상태 그대로 왼쪽(오른쪽) 발을 접어 10초간 버티며 허리와 허벅지를 늘려준다."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        // 이미지와 설명이 들어있는 리스트 생성
        DataItem[] dataList = {
                new DataItem(R.drawable.i, " 스트레칭 준비  1:00"),
                new DataItem(R.drawable.i1, " 굽은 목과 허리 교정  1:30"),
                new DataItem(R.drawable.i2, " 굽은 허리,다리 교정  1:00"),
                new DataItem(R.drawable.i3, " 굽은 허리 교정  1:30"),
                new DataItem(R.drawable.i4, " 허리,다리 교정 2  1:00 "),
        };

        // 커스텀 어댑터를 사용하여 리스트뷰에 데이터 연결
        ListView listView = findViewById(R.id.listView);
        CustomListAdapter adapter = new CustomListAdapter(this, dataList);
        listView.setAdapter(adapter);

        // ListView 아이템 클릭 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 팝업 창에 표시할 이미지와 텍스트 가져오기
                int popupImageResId = popupImages[position];
                String popupText = popupTexts[position];

                // 커스텀 레이아웃을 사용한 AlertDialog 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);
                builder.setView(dialogView);


                // 커스텀 레이아웃의 뷰 가져오기
                ImageView popupImageView = dialogView.findViewById(R.id.dialogImageView);
                TextView popupTextView = dialogView.findViewById(R.id.dialogTextView);

                // 데이터 설정
                popupImageView.setImageResource(popupImageResId);
                // 이미지뷰 크기 조정
                int desiredWidth = 750;
                int desiredHeight = 600;

                popupImageView.getLayoutParams().width = desiredWidth;
                popupImageView.getLayoutParams().height = desiredHeight;

                String[] lines = popupText.split("\n");
                StringBuilder multiLineText = new StringBuilder();
                for (String line : lines) {
                    multiLineText.append(line).append("\n");
                }
                popupTextView.setText(multiLineText.toString());

                // 확인 버튼 설정
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                        positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    }
                });
                dialog.show();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Animation.class);
                startActivity(intent);
            }
        });
    }
}