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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginEmployeeActivity extends AppCompatActivity {

    EditText mUsername, mPassword, mRestarauntId;
    Button loginButton, registerButton;

    DatabaseReference databaseWaiters, databaseRestaraunts;
    ArrayList<Waiter> mWaiters;
    boolean foundWaiter = false;

    private static final String RESTARAUNT_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.restarauntid";
    private static final String WAITER_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.waiterid";
    private String restarauntId;
    private Waiter checkWaiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employee);
        Intent intent = getIntent();
        restarauntId = intent.getStringExtra(RESTARAUNT_ID);


        //list to store waiters
        mWaiters = new ArrayList<>();

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


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseWaiters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //clearing the previous waiter list
               mWaiters.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.child(restarauntId).getChildren()) {
                    //getting waiter
                    Waiter waiter = postSnapshot.getValue(Waiter.class);
                    //adding waiter to the list
                    mWaiters.add(waiter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkCredentials(String username, String password, String RestarauntId)
    {

        if (mWaiters.size()==0){
            Toast.makeText(getApplicationContext(),"No Waiters Registered at this Restaurant. Please register a waiter.",Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < mWaiters.size(); i++ ) {
                if (mWaiters.get(i) != null) {
                    Waiter waiter = mWaiters.get(i);
                    if (waiter.getWaiterId().equals(username)) {
                        foundWaiter = true;
                        checkWaiter = waiter;
                        break;
                    }
                }
            }
            if (foundWaiter = true){
                if (checkWaiter.getPassword().equals(password)) {
                    Toast.makeText(getApplicationContext(), "Yay! This worked", Toast.LENGTH_SHORT).show();
                    //TODO: Pass waiter and restaraunt ids using intent extras /// DONE
                    Intent waiterTableIntent = new Intent(getApplicationContext(), WaiterTableTracker.class);
                    waiterTableIntent.putExtra(WAITER_ID, checkWaiter.getWaiterId());
                    waiterTableIntent.putExtra(RESTARAUNT_ID, restarauntId);
                    startActivity(waiterTableIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Password, please reenter", Toast.LENGTH_SHORT).show();
                    foundWaiter = false;
                }
            } else {
                Toast.makeText(getApplicationContext(), "waiter does not exist, please Register if new" , Toast.LENGTH_SHORT).show();
            }

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

        dialogBuilder.setTitle("waiter Registration");
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
