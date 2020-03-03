package com.example.weightindentifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminLogin extends AppCompatActivity {

    Button submitButton;
    EditText userName_editText, password_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setTitle("Vehicle Overload Detection System");

        submitButton = (Button) findViewById(R.id.submitButton);

        userName_editText = (EditText) findViewById(R.id.userName_editText);
        password_editText = (EditText) findViewById(R.id.password_editText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userName_editText.getText().toString().equals("admin") && password_editText.getText().toString().equals("admin")) {
                    Intent i = new Intent(getApplicationContext(), UserOperation.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }

            }
        });


    }


}
