package com.example.stretching;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Animation extends AppCompatActivity {

    private ImageView gifImageView;
    private Button nextButton;
    private Button prevButton; // 이전 버튼 추가
    private List<Integer> gifList;
    private List<Integer> timerList;
    private List<String> descriptionList;
    private int currentIndex;
    private Handler handler;
    private Runnable runnable;
    private int remainingTime;
    private TextView timerTextView;
    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;

    private Button stopButton;
    private boolean isMuted = false;
    private boolean isTimerRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation);

        gifImageView = findViewById(R.id.gifImageView);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton); // 이전 버튼 참조 가져오기
        timerTextView = findViewById(R.id.timerTextView);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        stopButton = findViewById(R.id.stopButton);

        gifList = new ArrayList<>();
        gifList.add(R.drawable.i);
        gifList.add(R.drawable.i1);
        gifList.add(R.drawable.i2);
        gifList.add(R.drawable.i3);
        gifList.add(R.drawable.i4);

        timerList = new ArrayList<>();
        timerList.add(60);
        timerList.add(90);
        timerList.add(60);
        timerList.add(90);
        timerList.add(60);

        descriptionList = new ArrayList<>();
        descriptionList.add("스트레칭 준비\n\n1) 스트레칭을 올바른 자세로 준비하세요.             \n\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.                         ");
        descriptionList.add("굽은 목과 허리교정\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.\n\n1) 양반다리를 하고 앉은 상태로 양손은 깍지를 껴 머리 뒤에 댄다.\n\n2) 허리를 곧게 편 상태로 양 팔꿈치를 앞으로 당겨 10초간 버틴다. ");
        descriptionList.add("굽은 허리와 다리 교정\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.\n\n1) 허리를 반 접은 후 양손으로 바닥을 짚는다.\n\n2) 상태 그대로 왼발과 오른발의 무릎을 굽혀 허리와 허벅지를 늘려준다. ");
        descriptionList.add("굽은 허리 교정\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.\n\n1) 곧게 선 채로 양손은 마주 보게 잡아 머리 위로 쭉 펴 올린다.\n\n2) 상태 그대로 왼쪽과 오른쪽으로 허리를 굽혀 10초간 버티며 반대편 허리를 펴준다. ");
        descriptionList.add("굽은 허리와 다리 교정2\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.\n\n1) 곧게 선 채로 양손을 마주 보게 잡아 머리 위로 쭉 펴 올린다.\n\n2) 상태 그대로 왼쪽(오른쪽) 발을 접어 10초간 버티며 허리와 허벅지를 늘려준다. ");

        currentIndex = 0;

        loadGifImage();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    remainingTime--;
                    String timerText = "Timer : "+remainingTime + "/" + timerList.get(currentIndex);
                    timerTextView.setText(timerText);

                    if (remainingTime == 5) {
                        playSound();
                    }

                    handler.postDelayed(this, 1000);
                } else {
                    timerTextView.setText("");
                    nextGifImage();
                }
            }
        };

        remainingTime = timerList.get(currentIndex);
        handler.postDelayed(runnable, 1000);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextGifImage();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevGifImage(); // 이전 버튼 클릭 시 이전 GIF 이미지로 돌아가기
            }
        });



        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    stopTimer();
                } else {
                    startTimer();
                }
            }
        });
    }

    private void loadGifImage() {
        int gifResourceId = gifList.get(currentIndex);
        Glide.with(this).asGif().load(gifResourceId).into(gifImageView);

        int timerValue = timerList.get(currentIndex);
        String timerText = "Timer : "+remainingTime + "/" + timerValue;
        timerTextView.setText(timerText);

        String description = descriptionList.get(currentIndex);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(description);
    }

    private void nextGifImage() {
        currentIndex++;
        if (currentIndex >= gifList.size()) {
            finish();
            Intent intent = new Intent(this, MainActivity.class); // OtherActivity에 이동하려는 액티비티 클래스를 넣어주세요
            startActivity(intent);
            return;

        }
        loadGifImage();
        remainingTime = timerList.get(currentIndex);
        handler.removeCallbacks(runnable);
        if (isTimerRunning) {
            handler.postDelayed(runnable, 1000);
        }
    }

    private void prevGifImage() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = gifList.size() - 1;
        }
        loadGifImage();
        remainingTime = timerList.get(currentIndex);
        handler.removeCallbacks(runnable);
        if (isTimerRunning) {
            handler.postDelayed(runnable, 1000);
        }
    }

    private void playSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_file);

        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
        isTimerRunning = false;
    }

    private void startTimer() {
        isTimerRunning = true;
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        handler.removeCallbacks(runnable);
    }
}
