package com.csci366_2020.jihwanjeong.labtask2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfoActivity extends AppCompatActivity {

    // DECLARE VARIABLES
    private ImageView profileImage;
    private TextView usernameText;
    private TextView emailText;
    private TextView lastLoginText;
    private Intent userInfoIntent;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        // CONNECT TO EACH ID.
        profileImage = findViewById(R.id.profileImage);
        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        lastLoginText = findViewById(R.id.lastLoginText);
        backButton = findViewById(R.id.backButton);

        // GET THE INTENT.
        userInfoIntent = getIntent();

        // RECEIVE EACH VALUE FROM THE INTENT
        String username = userInfoIntent.getStringExtra("username");
        String email = userInfoIntent.getStringExtra("email");
        String lastLogin = userInfoIntent.getStringExtra("lastLogin");

        // SET THE PASSED IMAGE AND TEXTS.
        if (username.equals("spiderman"))
            profileImage.setImageResource(R.drawable.pic1);
        else if (username.equals("delta"))
            profileImage.setImageResource(R.drawable.pic2);
        else if (username.equals("admin"))
            profileImage.setImageResource(R.drawable.pic3);

        // SET TEXTS
        usernameText.setText(username);
        emailText.setText(email);
        lastLoginText.setText(lastLogin);

        // BACK BUTTON TO GO TO THE PREVIOUS PAGE.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
