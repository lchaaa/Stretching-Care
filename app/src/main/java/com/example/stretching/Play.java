package com.example.stretching;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

//비디오 재생
public class Play extends AppCompatActivity {
    private SimpleExoPlayer player1;
    private SimpleExoPlayer player2;
    private SimpleExoPlayer player3;
    private PlayerView playerView1;
    private PlayerView playerView2;
    private PlayerView playerView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        playerView1 = findViewById(R.id.playerView);
        playerView2 = findViewById(R.id.playerView2);
        playerView3 = findViewById(R.id.playerView3);
        player1 = new SimpleExoPlayer.Builder(this).build();
        player2 = new SimpleExoPlayer.Builder(this).build();
        player3 = new SimpleExoPlayer.Builder(this).build();
        playerView1.setPlayer(player1);
        playerView2.setPlayer(player2);
        playerView3.setPlayer(player3);

        // 첫 번째 비디오 파일 설정
        int rawResourceId1 = R.raw.s;
        String videoFilePath1 = "android.resource://" + getPackageName() + "/" + rawResourceId1;
        Uri videoUri1 = Uri.parse(videoFilePath1);
        MediaItem mediaItem1 = MediaItem.fromUri(videoUri1);
        player1.setMediaItem(mediaItem1);

        // 두 번째 비디오 파일 설정
        int rawResourceId2 = R.raw.a; // 두 번째 비디오 파일의 리소스 ID를 입력해주세요.
        String videoFilePath2 = "android.resource://" + getPackageName() + "/" + rawResourceId2;
        Uri videoUri2 = Uri.parse(videoFilePath2);
        MediaItem mediaItem2 = MediaItem.fromUri(videoUri2);
        player2.setMediaItem(mediaItem2);

        // 두 번째 비디오 파일 설정
        int rawResourceId3 = R.raw.s; // 두 번째 비디오 파일의 리소스 ID를 입력해주세요.
        String videoFilePath3 = "android.resource://" + getPackageName() + "/" + rawResourceId3;
        Uri videoUri3 = Uri.parse(videoFilePath3);
        MediaItem mediaItem3 = MediaItem.fromUri(videoUri3);
        player3.setMediaItem(mediaItem3);

        // 비디오 재생 준비 및 시작
        player1.prepare();
        player2.prepare();
        player3.prepare();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player1.release();
        player2.release();
        player3.release();
    }
}
