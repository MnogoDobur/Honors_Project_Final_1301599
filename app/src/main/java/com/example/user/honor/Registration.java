package com.example.user.honor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class Registration extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private Button easyBut;
    private Button mediumBut;
    private Button hardBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final Button registerButton  =(Button)findViewById(R.id.register);
        final SharedPreferences accountInfo = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = accountInfo.edit();
        easyBut = (Button)findViewById(R.id.easy);
        mediumBut = (Button)findViewById(R.id.medium);
        hardBut = (Button)findViewById(R.id.hard);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameInput = (EditText) findViewById(R.id.username);
                passwordInput = (EditText) findViewById(R.id.password);
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                editor.putString("Username",username);
                editor.putString("Password",password);
                editor.putInt("Credits",2);
                editor.putInt("HighScore",0);
                editor.putInt("LifetimeScore",0);
                editor.apply();

                finish();
                Intent mainMenu = new Intent(Registration.this,MainMenu.class);
                startActivity(mainMenu);
            }
        });
        easyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("Diff",1);// 1 - easy , 2 - medium , 3 - hard
                editor.apply();
            }
        });
        mediumBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("Diff",2);editor.apply();
            }
        });
        hardBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("Diff",3);editor.apply();
            }
        });
    }
}
