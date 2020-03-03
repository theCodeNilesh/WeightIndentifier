package com.example.weightindentifier;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weightindentifier.DbHelper.DbHelper;
import com.example.weightindentifier.DbHelper.ProjectContract;

public class DeleteUser extends AppCompatActivity {

    DbHelper mDbHelper;
    Button btnDelete;
    EditText mobileNumber;
    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        getSupportActionBar().setTitle("Vehicle Overload Detection System");
        mobileNumber = findViewById(R.id.mobileNumber);
        btnDelete = findViewById(R.id.deleteUser_Button);

        mDbHelper = new DbHelper(this);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = mobileNumber.getText().toString().trim();
                Log.i("mobileValue", "" + mobile);

                if (mobile.length() > 0)
                {
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();


                    String selection = ProjectContract.Project.MOBILENUMBER + " = ?";
                    String[] selectionArgs = {mobile};

                    int newRowId = db.delete(ProjectContract.Project.TBL_USER, selection, selectionArgs);

                    if (newRowId == 0) {
                        Toast.makeText(DeleteUser.this, "No user Found for This number", Toast.LENGTH_SHORT).show();
                    } else {
                        //  getAllCategory("");
                        Toast.makeText(DeleteUser.this, "User Deleted Sucsessfully..", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
                else
                {

                    Toast.makeText(DeleteUser.this, "Please enter mobile Number", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }
}
