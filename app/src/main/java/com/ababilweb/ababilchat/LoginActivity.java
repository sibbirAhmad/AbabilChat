package com.ababilweb.ababilchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ababilweb.ababilchat.networking.ServerApi;

public class LoginActivity extends AppCompatActivity {
EditText phone_emailEDT,passwordEDT;
Button loginBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBTN = findViewById(R.id.loginBTN);
        phone_emailEDT = findViewById(R.id.phone_emailEDT);
        passwordEDT = findViewById(R.id.passwordEDT);
        loginBTN.setOnClickListener(v -> {
         String email=   phone_emailEDT.getText().toString();
          String pass=  passwordEDT.getText().toString();
          String data = "{\n" +
                  "    \"request\":\"USER_LOGIN\",\n" +
                  "    \"username\": \""+email+"\",\n" +
                  "    \"password\": \""+pass+"\"\n" +
                  "    \n" +
                  "}";
            ServerApi api = new ServerApi("https://ababilweb.com/chat/req.php", data, new ServerApi.ServerApiListener() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


        });
    }
}