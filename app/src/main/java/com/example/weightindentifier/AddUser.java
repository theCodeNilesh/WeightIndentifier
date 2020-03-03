package com.example.weightindentifier;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weightindentifier.DbHelper.DbHelper;
import com.example.weightindentifier.DbHelper.ProjectContract;

public class AddUser extends AppCompatActivity {
    TextView userNameTextView, passwordTextView, emailIDTextView;
    EditText userNameEditText, passwordEditText, emailEditText, mobileNumber;
    Button submitButton;
    // MyDBHelper myDbHandler;
    DbHelper mDbHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        getSupportActionBar().setTitle("Vehicle Overload Detection System");

        //  userNameTextView = findViewById(R.id.userNameTextView);
        userNameEditText = findViewById(R.id.userNameEditText);
        //  passwordTextView = findViewById(R.id.passwordTextView);
        passwordEditText = findViewById(R.id.passwordEditText);
        // emailIDTextView = findViewById(R.id.emailID_TextView);
        emailEditText = findViewById(R.id.emailEditText);
        mobileNumber = findViewById(R.id.mobileNumber);

        submitButton = findViewById(R.id.submitButton);
        listView = findViewById(R.id.listView);

        //  myDbHandler = new MyDBHelper(getApplicationContext());
        mDbHelper = new DbHelper(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String username = userNameEditText.getText().toString();
                String passward = passwordEditText.getText().toString();
                String emailID = emailEditText.getText().toString();

                String mobile = mobileNumber.getText().toString();

                if (username.length() > 0 && passward.length() > 0 && emailID.length() > 0 && mobile.length() > 0) {
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(ProjectContract.Project.USERNAME, username);
                    values.put(ProjectContract.Project.PASSWORD, passward);
                    values.put(ProjectContract.Project.MOBILENUMBER, mobile);
                    values.put(ProjectContract.Project.V_NUMBER, emailID);
                    long newRowId = db.insert(ProjectContract.Project.TBL_USER, null, values);


                    if (newRowId == -1) {

                        Toast.makeText(AddUser.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(AddUser.this, "User Added Sucsessfully...", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                } else {
                    Toast.makeText(AddUser.this, "Please Enter All Field", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
