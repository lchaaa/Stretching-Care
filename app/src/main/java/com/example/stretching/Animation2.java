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

public class Animation2 extends AppCompatActivity {

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
        gifList.add(R.drawable.s);
        gifList.add(R.drawable.s2);
        gifList.add(R.drawable.s3);
        gifList.add(R.drawable.s4);

        timerList = new ArrayList<>();
        timerList.add(60);
        timerList.add(90);
        timerList.add(60);
        timerList.add(90);
        timerList.add(60);

        descriptionList = new ArrayList<>();
        descriptionList.add("스트레칭 준비\n\n1) 스트레칭을 올바른 자세로 준비하세요.             \n\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.                         ");
        descriptionList.add("옆구리 늘리기\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.\n\n1) 오른팔을 내려서 등 위에 댄다. 왼손으로 오른쪽 팔꿈치를 잡고 스트레칭한다.\n\n2) 한 번 늘릴 때 10초 이상 유지하면 평소 척추를 잡아주는 근육이 이완된다. ");
        descriptionList.add("척추 비틀기\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.\n\n1) 숨을 들이쉬며 제자리로 돌 아온 다음, 내쉬면서 반대쪽으로 반복하며 등의 긴장을 풀어준다\n\n2) 갑자기 비틀면 우드득 소리가 날 수 있으니 호흡에 신경 쓰는 것이 중요하다. ");
        descriptionList.add("척추 펴기\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.\n\n1) 엎드린 상태에서 양팔을 들어올리고 가슴을 젖히면서 상체를 뒤로 일 으킨다\n\n2) 익숙해지면 다리 역시 들어올려 활 모양이 되도록 척추 기립근의 힘을 이 용한다. ");
        descriptionList.add("허리 젖히기\n* 타이머 가 끝나기 5초 전에 사운드가 재생됩니다.\n\n1) 모두 일직선을 이루도록 똑바로 선 상태에 서 허리 높이 의자에 한쪽 발을 뻗어 걸친다.\n\n2) 허리와 엉덩이의 각도가 최대한 90도 를 유지하도록 팔과 상체를 앞으로 쭉 뻗는다. ");

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
