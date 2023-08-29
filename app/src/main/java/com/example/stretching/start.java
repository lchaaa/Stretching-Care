package com.example.stretching;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        Button button = findViewById(R.id.Button);
        Button button2 = findViewById(R.id.Button2);
        Button button3 = findViewById(R.id.Button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this, MainActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // 각 메뉴의 ID를 비교하여 처리
                    if (item.getItemId() == R.id.menu_chart) {
                        // menu_chart를 클릭했을 때 처리할
                        Intent intent = new Intent(start.this, Calendar.class);
                        startActivity(intent);

                        return true;
                    } else if (item.getItemId() == R.id.menu_search) {
                        // menu_search를 클릭했을 때 처리할 내용

                        return true;
                    } else if (item.getItemId() == R.id.menu_more) {
                        Intent intent = new Intent(start.this, Play.class);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            };
}