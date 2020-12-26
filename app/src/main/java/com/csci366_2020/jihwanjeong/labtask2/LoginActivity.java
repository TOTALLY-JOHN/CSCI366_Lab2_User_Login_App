package com.csci366_2020.jihwanjeong.labtask2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {

    // DECLARE VARIABLES
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView validationTextView;
    private Intent userInfoIntent;
    private ArrayList<String> loginList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // CONNECT TO EACH ID.
        usernameEditText = findViewById(R.id.usernameInput);
        passwordEditText = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        validationTextView = findViewById(R.id.validationText);
        File file = getDir("login_logs", Activity.MODE_PRIVATE);
        String path = file.getAbsolutePath();

        try {
            // IF THERE IS NO USERNAME OF TEXT FILE TO STORE THE LAST LOGIN, MAKE A NEW TEXT FILE
            FileWriter fw1 = new FileWriter(new File(path + "/spiderman.txt"), true);
            FileWriter fw2 = new FileWriter(new File(path + "/delta.txt"), true);
            FileWriter fw3 = new FileWriter(new File(path + "/admin.txt"), true);
            PrintWriter pw1 = new PrintWriter(fw1);
            PrintWriter pw2 = new PrintWriter(fw2);
            PrintWriter pw3 = new PrintWriter(fw3);

            // PRINT
            pw1.print("");
            pw2.print("");
            pw3.print("");

            // CLOSE
            pw1.close();
            pw2.close();
            pw3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // USER CLICKS A LOGIN BUTTON
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoIntent = new Intent(LoginActivity.this, UserInfoActivity.class);
                String username = usernameEditText.getText().toString();
                String userPwd = passwordEditText.getText().toString();

                // IF BOTH THE USERNAME AND THE PASSWORD ARE CORRECT
                if(validation(username, userPwd))
                {
                    try {
                        // TO RETRIEVE THE PREVIOUS LOGIN HISTORY.
                        Scanner readFile = new Scanner(new File(getDir("login_logs", Activity.MODE_PRIVATE).getAbsolutePath() + "/" + username + ".txt"));
                        while (readFile.hasNextLine()) {
                            String line = readFile.nextLine();
                            loginList.add(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        // WHEN LOGIN IS SUCCESSFUL, IT UPDATES THE LATEST LOGIN DATETIME.
                        FileWriter fw = new FileWriter(new File(getDir("login_logs", Activity.MODE_PRIVATE).getAbsolutePath() + "/" + username + ".txt"), true);
                        PrintWriter pw = new PrintWriter(fw);

                        // GET THE CURRENT DATETIME.
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar now = Calendar.getInstance();

                        // GET THE MALAYSIAN DATETIME PLUS 8 (HOUR).
                        now.add(Calendar.HOUR, 8);

                        // PRINT THE DATETIME IN THE TXT FILE.
                        pw.println(formatter.format(now.getTime()));

                        // CLOSE THE FILE.
                        pw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    validationTextView.setVisibility(View.INVISIBLE);
                    userInfoIntent.putExtra("username", username);
                    if (username.equals("spiderman")) {
                        userInfoIntent.putExtra("email", "spiderman@gmail.com");
                    }
                    else if (username.equals("delta")) {
                        userInfoIntent.putExtra("email", "delta@gmail.com");
                    }
                    else if (username.equals("admin")) {
                        userInfoIntent.putExtra("email", "admin@gmail.com");
                    }


                    if(loginList.size() != 0) // NOT THE FIRST LOGIN
                        userInfoIntent.putExtra("lastLogin", loginList.get(loginList.size() - 1));
                    else // FIRST LOGIN
                        userInfoIntent.putExtra("lastLogin", "-");

                    startActivity(userInfoIntent);
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                }
                // IF EITHER USERNAME OR PASSWORD IS INCORRECT, DISPLAY ERROR MESSAGE.
                else
                {
                    validationTextView.setText("Username or password incorrect!");
                    validationTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // VALIDATE YOUR INPUT FOR LOGIN.
    public boolean validation(String username, String userPwd) {
        if ((username.equals("spiderman") && userPwd.equals("man2spider")) ||
            (username.equals("delta") && userPwd.equals("atled")) ||
            (username.equals("admin") && userPwd.equals("iamsuper")))
            return true;

        return false;
    }
}