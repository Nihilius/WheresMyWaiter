package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WaiterTableTracker extends AppCompatActivity {

    Button createNewTable;
    ListView tableListview;
    EditText enteredTableNumber;

    ArrayList<Table> mTables;
    DatabaseReference databaseTables;
    private static final String RESTARAUNT_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.restarauntid";
    private static final String WAITER_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.waiterid";

    private String restarauntId,waiterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_table_tracker);
        Intent intent = getIntent();
        restarauntId = intent.getStringExtra(RESTARAUNT_ID);
        waiterId = intent.getStringExtra(WAITER_ID);


        databaseTables = FirebaseDatabase.getInstance().getReference("Table_Session").child(restarauntId).child(waiterId);
        createNewTable = (Button) findViewById(R.id.CreateTableButton);
        tableListview = (ListView) findViewById(R.id.ListViewTable);
        enteredTableNumber = (EditText) findViewById(R.id.editTextTableNumber) ;



        //set up tables array

        mTables = new ArrayList<>();


        createNewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTable(restarauntId, waiterId,Integer.parseInt(enteredTableNumber.getText().toString()) );
            }
        });


        tableListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Table tableObj = mTables.get(i);
                showCustomerRequestDialog(tableObj);
            }
        });

        tableListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Table tableObj = mTables.get(i);
                showExitSessionDialog(tableObj);
                return false;
            }
        });


        
    }





    private void showCustomerRequestDialog(final Table table)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_table_info_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText requestInfo = (EditText) dialogView.findViewById(R.id.requestInfo);
        final Button acknowledgePing = (Button) dialogView.findViewById(R.id.acknowledgePing);
        final Button cancelPing = (Button) dialogView.findViewById(R.id.cancelButton);

        dialogBuilder.setTitle("Request Info");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        acknowledgePing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.setHasAcknolwedged(true);
                table.setIsPinged(false);
                table.setHasMessage(false);
                databaseTables.child("Table" + table.getTableNumber()).setValue(table);
                Toast.makeText(getApplicationContext(), "Ping acknowledged. Please fill the customer's request.", Toast.LENGTH_SHORT).show();

            }
        });


        cancelPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
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
                databaseTables.child("Table" + table.getTableNumber()).removeValue();
                b.dismiss();
                Toast.makeText(getApplicationContext(), "You have removed the table.", Toast.LENGTH_SHORT).show();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });





    }





    private void addTable(String restarauntId, String waiterId, int tableNumber) {



            //creating an Table Object
            Table table = new Table(tableNumber,restarauntId,waiterId,false,false,"");

            //Saving the Table
            databaseTables.child("Table"+ table.getTableNumber()).setValue(table);

            //setting edittext to blank again
          enteredTableNumber.setText("");

            //displaying a success toast
            Toast.makeText(this, "Table added", Toast.LENGTH_LONG).show();



    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseTables.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //clearing the previous waiter list
                mTables.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting waiter
                    Table table = postSnapshot.getValue(Table.class);
                    //adding waiter to the list
                    mTables.add(table);
                }
                //creating adapter
                TableList tableAdapter = new TableList(WaiterTableTracker.this, mTables);
                //attaching adapter to the listview
                tableListview.setAdapter(tableAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




















}
