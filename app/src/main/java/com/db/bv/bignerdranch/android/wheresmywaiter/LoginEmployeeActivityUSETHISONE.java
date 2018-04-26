package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginEmployeeActivityUSETHISONE extends AppCompatActivity {

    EditText mUsername, mPassword, mRestarauntId;
    Button loginRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employee_usethisone);

        mUsername = (EditText) findViewById(R.id.AccountID);
        mPassword = (EditText) findViewById(R.id.Password);
        mRestarauntId = (EditText) findViewById(R.id.RestarauntId);

        loginRegistration = (Button) findViewById(R.id.LoginButton);



        loginRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials(mUsername.getText().toString(), mPassword.getText().toString(),mRestarauntId.getText().toString());
            }
        });




    }

    public void checkCredentials(String username, String password, String RestarauntId)
    {

    }




}
