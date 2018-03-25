package com.eventory.andriod.eventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static String mUsername;
    private static String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUsername = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //left blanks
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //left blank
            }
        });


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user;
                UserBase userBase = UserBase.get(LoginActivity.this);
                List<User> users = userBase.getUsers();
                boolean usernameTrue = false;
                if (users.size() == 0){
                    Toast.makeText(LoginActivity.this,"Please create an account",Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int counter = 0; counter < users.size(); counter++) {
                        user = users.get(counter);
                        if (user.getUsername().equals(mUsername)) {
                            usernameTrue = true;
                        }
                    }
                    if (!usernameTrue) {
                        Toast.makeText(LoginActivity.this, "Error this username is not used", Toast.LENGTH_SHORT).show();
                    }
                    else if (!userBase.getUser(mUsername).getPassword().equals(mPassword))
                    {
                        Toast.makeText(LoginActivity.this,"Error password does not match username",Toast.LENGTH_SHORT).show();
                    }
                    else if(userBase.getUser(mUsername) != null) {
                        UserBase.setCurrentUser(userBase.getUser(mUsername));
                        Intent registerIntent = new Intent(LoginActivity.this, ItemListActivity.class);
                        LoginActivity.this.startActivity(registerIntent);
                    }
                }
            }
        });
    }
}
