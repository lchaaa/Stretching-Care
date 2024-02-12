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

//등근육 척추 교정 시작
public class Animation2 extends AppCompatActivity {
    private ImageView gifImageView;
    private Button nextButton;
    private Button prevButton;
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
        prevButton = findViewById(R.id.prevButton);
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

        //타이머를 카운트 다운하고, 남은 시간을 화면에 업데이트하며 특정 시간에 사운드를 재생
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

        //다음
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextGifImage();
            }
        });

        //이전
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevGifImage();
            }
        });

        //정지
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

    //현재 운동에 대한 GIF 이미지, 타이머 정보, 설명 등을 화면에 로드
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

    //다음 운동으로 이동
    private void nextGifImage() {
        currentIndex++;
        if (currentIndex >= gifList.size()) {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
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

    //이전 운동으로 이동
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

   //타이머가 특정 시간에 도달할 때 사운드를 재생
    private void playSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_file);

        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    //정지
    private void stopTimer() {
        handler.removeCallbacks(runnable);
        isTimerRunning = false;
    }

    //시작
    private void startTimer() {
        isTimerRunning = true;
        handler.post(runnable);
    }

    //액티비티 종료
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
