package com.example.user.honor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainMenu extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final Button loginButton = (Button) findViewById(R.id.login);
        final Button registerPage = (Button)findViewById(R.id.regPage);
        final SharedPreferences accountInfo = PreferenceManager.getDefaultSharedPreferences(this);

      registerPage.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent registration = new Intent(MainMenu.this, Registration.class);
              startActivity(registration);
          }
      });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameInput = (EditText) findViewById(R.id.username);
                passwordInput = (EditText) findViewById(R.id.password);
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
             //   SharedPreferences.Editor editor = accountInfo.edit();
                if(accountInfo.getString("Username","Username not found").equals(username) && accountInfo.getString("Password","Password is not correct").equals(password)){
                    Intent map = new Intent(MainMenu.this, MapsActivity.class);
                    startActivity(map);
                }
                else{
                    Toast.makeText(getBaseContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
