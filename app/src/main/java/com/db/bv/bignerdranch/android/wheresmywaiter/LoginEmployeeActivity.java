package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginEmployeeActivity extends AppCompatActivity {

    EditText mUsername, mPassword, mRestarauntId;
    Button loginButton, registerButton;

    DatabaseReference databaseWaiters, databaseRestaraunts;

    private static final String RESTARAUNT_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.restarauntid";
    private String restarauntId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employee);
        Intent intent = getIntent();
        restarauntId = intent.getStringExtra(RESTARAUNT_ID);

        databaseRestaraunts = FirebaseDatabase.getInstance().getReference("Restaraunt");
        databaseWaiters = FirebaseDatabase.getInstance().getReference("Waiter");

        mUsername = (EditText) findViewById(R.id.AccountID);
        mPassword = (EditText) findViewById(R.id.Password);

        loginButton = (Button) findViewById(R.id.LoginButton);
        registerButton = (Button) findViewById(R.id.RegisterButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials(mUsername.getText().toString(), mPassword.getText().toString(),restarauntId);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWaiterDialog();
            }
        });
    }

    public void checkCredentials(String username, String password, String RestarauntId)
    {

        DatabaseReference DR = FirebaseDatabase.getInstance().getReference("Waiter").child(RestarauntId).child(username);
            if (true)
            {
                Toast.makeText(getApplicationContext(), "Yay! This worked", Toast.LENGTH_SHORT).show();
                //TODO: Pass waiter and restaraunt ids using intent extras
                startActivity(new Intent(getApplicationContext(), WaiterTableTracker.class));
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Error, please try again",Toast.LENGTH_SHORT).show();
            }
    }

    private void showRegisterWaiterDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register_waiter_dialog, null);
        dialogBuilder.setView(dialogView);


        final EditText editTextRestarauntId = (EditText) dialogView.findViewById(R.id.editTextRestarauntId);
        final EditText editTextWaiterId = (EditText) dialogView.findViewById(R.id.editTextWaiterId);

        final EditText editTextPassword = (EditText) dialogView.findViewById(R.id.editTextPassword);
        final Button buttonRegister = (Button) dialogView.findViewById(R.id.buttonRegisterWaiter);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancelRegistration);

        dialogBuilder.setTitle("Waiter Registration");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String restarauntId = editTextRestarauntId.getText().toString();
                String waiterId = editTextWaiterId.getText().toString();

                String password = editTextPassword.getText().toString();
                if (!TextUtils.isEmpty(restarauntId) && !TextUtils.isEmpty(waiterId) &&
                        !TextUtils.isEmpty(password)) {
                    registerWaiter(restarauntId,waiterId,password);
                    b.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields",Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    public void registerWaiter(String restarauntId, String waiterId, String password){

        Waiter waiter = new Waiter(restarauntId,waiterId,password);
        databaseWaiters.child(restarauntId).child(waiterId).setValue(waiter);
    }
}
