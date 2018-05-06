package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bwest on 4/23/2018.
 */

public class CustomerTableSession extends AppCompatActivity {

    private static final String RESTARAUNT_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.restarauntid";
    private static final String TABLE_NUMBER = "com.db.bv.bignerdranch.android.wheresmywaiter.tablenumber";
    private EditText customerRequestEditText;
    private Button pingWaiterButton, leaveSessionButton;
    private String restaurantId;
    private DatabaseReference databaseTableSession, databaseTable2;
    private int tableNumber;
    private Table table;
    private List <Object> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_table_session);

        list = new ArrayList<>();

        Intent intent = getIntent();
        tableNumber = intent.getIntExtra(TABLE_NUMBER, 0);
        restaurantId = intent.getStringExtra(RESTARAUNT_ID);
        databaseTableSession = FirebaseDatabase.getInstance().getReference("Table_Session").child(restaurantId);
        databaseTableSession.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    list.add(postSnapshot.getValue());
                    if (postSnapshot.child("Table" + tableNumber).equals(tableNumber))
                    {
                        String customerRequest = (String) postSnapshot.child("tableNumber").child("customerRequest").getValue();
                        Boolean hasMessage = (Boolean) postSnapshot.child("tableNumber").child("hasMessage").getValue();
                        Boolean isPinged = (Boolean) postSnapshot.child("tableNumber").child("isPinged").getValue();
                        String restaurantId = (String) postSnapshot.child("tableNumber").child("restarauntId").getValue();
                        int tableNumber = (int) postSnapshot.child("tableNumber").child("tableNumber").getValue();
                        String waiterId = (String) postSnapshot.child("tableNumber").child("waiterId").getValue();


                        table.setCustomerRequest(customerRequest);
                        table.setHasMessage(hasMessage);
                        table.setIsPinged(isPinged);
                        table.setRestarauntId(restaurantId);
                        table.setTableNumber(tableNumber);
                        table.setWaiterId(waiterId);


                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        customerRequestEditText = (EditText) findViewById(R.id.customerRequestEditText);
        pingWaiterButton = (Button) findViewById(R.id.pingWaiterButton);
        leaveSessionButton = (Button) findViewById(R.id.leaveSessionButton);




        pingWaiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.setIsPinged(true);
                databaseTableSession.child(table.getWaiterId()).child("Table" + table.getTableNumber()).setValue(table);
            }
        });
    }












}
