package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bwest on 4/23/2018.
 */

public class CustomerTableSession extends AppCompatActivity {

    private static final String RESTARAUNT_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.restarauntid";
    private static final String TABLE_NUMBER = "com.db.bv.bignerdranch.android.wheresmywaiter.tablenumber";
    private EditText customerRequestEditText;
    private TextView waiterStateText;
    private Button pingWaiterButton, leaveSessionButton;
    private String restaurantId, waiterid;
    private DatabaseReference databaseWaiters, databaseTableRef, databaseTableSession, databaseCustomerTable ;
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
        databaseWaiters = FirebaseDatabase.getInstance().getReference("Table_Session").child(restaurantId);
        databaseWaiters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    waiterid = postSnapshot.getKey();
                    databaseTableRef = FirebaseDatabase.getInstance().getReference("Table_Session").child(restaurantId).child(waiterid);
                    databaseTableRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren())
                            {
                                Table tableObject = snapshot.getValue(Table.class);
                                if(tableObject.getTableNumber() == tableNumber)
                                {
                                    table = tableObject;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                databaseTableSession = FirebaseDatabase.getInstance().getReference("Table_Session").child(restaurantId).child(waiterid);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        customerRequestEditText = (EditText) findViewById(R.id.customerRequestEditText);
        pingWaiterButton = (Button) findViewById(R.id.pingWaiterButton);
        leaveSessionButton = (Button) findViewById(R.id.leaveSessionButton);
        waiterStateText = (TextView) findViewById(R.id.waiterStatusState);

        waiterStateText.setTextColor(getResources().getColor(R.color.grey));
        waiterStateText.setText("Awaiting customer request");






        databaseCustomerTable = FirebaseDatabase.getInstance().getReference("Table_Session").child(restaurantId).child(waiterid).child("Table" + table.getTableNumber());

        databaseCustomerTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Table table = dataSnapshot.getValue(Table.class);
                if(table.getIsPinged() == true)
                {
                    if(table.hasAcknolwedged == true)
                    {
                        waiterStateText.setTextColor(getResources().getColor(R.color.green));
                        waiterStateText.setText("Currently fulfilling request");

                    }
                    else
                    {
                        waiterStateText.setTextColor(getResources().getColor(R.color.red));
                        waiterStateText.setText("Has not seen ping");
                    }
                }
                else{
                    waiterStateText.setTextColor(getResources().getColor(R.color.grey));
                    waiterStateText.setText("Awaiting customer request");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        pingWaiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.setIsPinged(true);
                table.setHasAcknolwedged(false);
                if(!customerRequestEditText.getText().toString().isEmpty())
                {
                    table.setHasMessage(true);
                    table.setCustomerRequest(customerRequestEditText.getText().toString());
                }
                databaseTableSession.child("Table"+ table.getTableNumber()).setValue(table);

            }
        });
    }












}
