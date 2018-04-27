package com.db.bv.bignerdranch.android.wheresmywaiter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employee);

        databaseRestaraunts = FirebaseDatabase.getInstance().getReference("Restaraunt");
        databaseWaiters = FirebaseDatabase.getInstance().getReference("Waiter");

        mUsername = (EditText) findViewById(R.id.AccountID);
        mPassword = (EditText) findViewById(R.id.Password);
        mRestarauntId = (EditText) findViewById(R.id.RestarauntId);

        loginButton = (Button) findViewById(R.id.LoginButton);
        registerButton = (Button) findViewById(R.id.RegisterButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials(mUsername.getText().toString(), mPassword.getText().toString(),mRestarauntId.getText().toString());
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

    }

    private void showRegisterWaiterDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register_waiter_dialog, null);
        dialogBuilder.setView(dialogView);


        final EditText editTextRestarauntId = (EditText) dialogView.findViewById(R.id.editTextRestarauntId);
        final EditText editTextWaiterId = (EditText) dialogView.findViewById(R.id.editTextWaiterId);
        final EditText editTextFirstName = (EditText) dialogView.findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = (EditText) dialogView.findViewById(R.id.editTextLastName);
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
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String password = editTextPassword.getText().toString();
                if (!TextUtils.isEmpty(restarauntId) && !TextUtils.isEmpty(waiterId) &&
                        !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) &&
                        !TextUtils.isEmpty(password)) {
                    registerWaiter(restarauntId,waiterId,firstName,lastName,password);
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

    public void registerWaiter(String restarauntId, String waiterId, String firstName, String lastName, String password){

        Waiter waiter = new Waiter(restarauntId,waiterId,firstName,lastName,password);
        databaseWaiters.child(restarauntId).child(waiterId).setValue(waiter);
    }
}
