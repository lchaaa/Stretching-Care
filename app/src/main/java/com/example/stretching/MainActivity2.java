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

//등근육 척추교정

//팝업 창에 표시할 이미지
public class MainActivity2 extends AppCompatActivity {
    private int[] popupImages = {
            R.drawable.i,
            R.drawable.s,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
    };
    // 팝업 창에 표시할 텍스트
    private String[] popupTexts = {
            "스트레칭 준비\n\n1) 스트레칭을 올바른 자세로 준비하세요.\n\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.",
            "옆구리 늘리기\n\n1) 오른팔을 내려서 등 위에 댄다. 왼손으로 오른쪽 팔꿈치를 잡고, 몸의 중심을 실어 옆구리를 최대한 늘리면서 스트레칭한다.\n\n2) 한 번 늘릴 때 10초 이상 유지하면 평소 척추를 잡아주는 근육이 이완된다.",
            "척추 비틀기\n\n1) 숨을 들이쉬며 제자리로 돌 아온 다음, 내쉬면서 반대쪽으로 반복하며 등의 긴장을 풀어준다.\n\n2) 갑자기 비틀면 우드득 소리가 날 수 있으니 호흡에 신경 쓰는 것이 중요하다.",
            "척추 펴기\n\n1) 엎드린 상태에서 양팔을 들어올리고 가슴을 젖히면서 상체를 뒤로 일 으킨다. \n\n2) 익숙해지면 다리 역시 들어올려 활 모양이 되도록 척추 기립근의 힘을 이 용한다",
            "허리 젖히기\n\n1) 머리, 골반과 무릎, 발목이 모두 일직선을 이루도록 똑바로 선 상태에 서 허리 높이 의자에 한쪽 발을 뻗어 걸친다.\n\n2) 허리와 엉덩이의 각도가 최대한 90도 를 유지하도록 팔과 상체를 앞으로 쭉 뻗는다."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button1 = findViewById(R.id.button1);
        // 이미지와 설명이 들어있는 리스트 생성
        DataItem[] dataList = {
                new DataItem(R.drawable.i, " 스트레칭 준비  1:00"),
                new DataItem(R.drawable.s, " 옆구리 늘리기  1:30"),
                new DataItem(R.drawable.s2, " 척추 비틀기  1:00"),
                new DataItem(R.drawable.s3, " 척추 펴기  1:30"),
                new DataItem(R.drawable.s4, " 허리 젖히기  1:00 "),
                // 다른 이미지와 설명을 추가로 넣으세요.
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
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
                Intent intent = new Intent(MainActivity2.this, Animation2.class);
                startActivity(intent);
            }
        });
    }
}