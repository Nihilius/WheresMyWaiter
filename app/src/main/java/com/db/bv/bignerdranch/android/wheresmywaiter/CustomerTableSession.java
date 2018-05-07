package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button pingWaiterButton, leaveSessionButton, pingFulfilledButton;
    private String restaurantId, waiterid;
    private DatabaseReference databaseWaiters, databaseTableRef, databaseTableSession, databaseCustomerTable ;
    private int tableNumber;
    private Table table;
    private List <Object> list;
    private Boolean foundTable = false;


    //tested the if statement block here, but not removed just in case for some reason we need an onStart method
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(foundTable)
//        {
//            databaseCustomerTable = FirebaseDatabase.getInstance().getReference("Table_Session").child(restaurantId).child(waiterid).child("Table" + table.getTableNumber());
//            databaseCustomerTable.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Table table = dataSnapshot.getValue(Table.class);
//                    if(table.getIsPinged() == true)
//                    {
//                        if(table.hasAcknolwedged == true)
//                        {
//                            waiterStateText.setTextColor(getResources().getColor(R.color.green));
//                            waiterStateText.setText("Currently fulfilling request");
//
//                        }
//                        else
//                        {
//                            waiterStateText.setTextColor(getResources().getColor(R.color.red));
//                            waiterStateText.setText("Has not seen ping");
//                        }
//                    }
//                    else{
//                        waiterStateText.setTextColor(getResources().getColor(R.color.grey));
//                        waiterStateText.setText("Awaiting customer request");
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_table_session);

        list = new ArrayList<>();

        Intent intent = getIntent();
        tableNumber = intent.getIntExtra(TABLE_NUMBER, 0);
        restaurantId = intent.getStringExtra(RESTARAUNT_ID);
        databaseWaiters = FirebaseDatabase.getInstance().getReference("Table_Session").child(restaurantId);

        customerRequestEditText = (EditText) findViewById(R.id.customerRequestEditText);
        pingWaiterButton = (Button) findViewById(R.id.pingWaiterButton);
        leaveSessionButton = (Button) findViewById(R.id.leaveSessionButton);
        waiterStateText = (TextView) findViewById(R.id.waiterStatusState);
        pingFulfilledButton = (Button) findViewById(R.id.pingFulfilledButton);

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
                                    //foundTable = true;
                                    if(table.hasAcknowledged == true)
                                    {
                                        waiterStateText.setTextColor(getResources().getColor(R.color.green));
                                        waiterStateText.setText("Currently fulfilling request");
                                        pingFulfilledButton.setVisibility(View.VISIBLE);
                                    }
                                    else if(table.getIsPinged() == true)
                                    {
                                        waiterStateText.setTextColor(getResources().getColor(R.color.red));
                                        waiterStateText.setText("Has not seen ping");
                                    }
                                    else{
                                        waiterStateText.setTextColor(getResources().getColor(R.color.grey));
                                        waiterStateText.setText("Awaiting customer request");
                                    }
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



        waiterStateText.setTextColor(getResources().getColor(R.color.grey));
        waiterStateText.setText("Awaiting customer request");




        pingFulfilledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.setIsPinged(false);
                table.setHasAcknolwedged(false);
                table.setHasMessage(false);
                table.setCustomerRequest("");
                pingFulfilledButton.setVisibility(View.INVISIBLE);
                databaseTableSession.child("Table"+ table.getTableNumber()).setValue(table);

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

        leaveSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitSessionDialog(table);
            }
        });
    }




    private void showExitSessionDialog(final Table table)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.terminate_session_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView confirmationTextView = (TextView) dialogView.findViewById(R.id.confirmationTextView);
        final Button yesButton = (Button) dialogView.findViewById(R.id.yesButton);
        final Button noButton = (Button) dialogView.findViewById(R.id.noButton);

        dialogBuilder.setTitle("Terminate Session?");
        final AlertDialog b = dialogBuilder.create();
        b.show();



        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseTableSession.child("Table" + table.getTableNumber()).removeValue();
                b.dismiss();
                Toast.makeText(getApplicationContext(), "You have left the table.", Toast.LENGTH_SHORT).show();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }








}
