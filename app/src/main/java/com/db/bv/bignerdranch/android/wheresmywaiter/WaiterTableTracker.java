package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    private static final String WAITER_ID = "com.bd.bv.bignerdranch.android.wheresmtwaiter.waiterid";

    private String restarauntId,waiterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_table_tracker);
        Intent intent = getIntent();
        restarauntId = intent.getStringExtra(RESTARAUNT_ID);
        waiterId = intent.getStringExtra(WAITER_ID);


        databaseTables = FirebaseDatabase.getInstance().getReference("Table_Session");
        createNewTable = (Button) findViewById(R.id.CreateTableButton);
        tableListview = (ListView) findViewById(R.id.ListViewTable);
        enteredTableNumber = (EditText) findViewById(R.id.editTextTableNumber) ;

        //set up tables array

        mTables = new ArrayList<>();


        createNewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTable(restarauntId, waiterId,Integer.getInteger(enteredTableNumber.getText().toString()) );
            }
        });


    }

    private void addTable(String restarauntId, String waiterId, int tableNumber) {



            //creating an Table Object
            Table table = new Table(tableNumber,restarauntId,waiterId,false,false);

            //Saving the Artist
            databaseTables.child(restarauntId).child(waiterId).setValue(table);

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
                for (DataSnapshot postSnapshot : dataSnapshot.child(restarauntId).child(waiterId).getChildren()) {
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
