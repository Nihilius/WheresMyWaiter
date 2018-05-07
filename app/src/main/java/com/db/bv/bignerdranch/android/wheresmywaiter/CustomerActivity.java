package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

  private EditText tableSessionBox;
  private Button startSessionButton;
  private DatabaseReference dbRef, dbTableRef;
  private String waiterId;
  private Table table;
  private int tableNumber;
  private Boolean foundTableNumber = false;


  private static final String RESTARAUNT_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.restarauntid";
  private static final String TABLE_NUMBER = "com.db.bv.bignerdranch.android.wheresmywaiter.tablenumber";

    private String restarauntId;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Intent intent = getIntent();
        restarauntId = intent.getStringExtra(RESTARAUNT_ID);
        tableSessionBox = (EditText) findViewById(R.id.TableCode);
        startSessionButton = (Button) findViewById(R.id.startSessionButton);

        startSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tableSessionBox.getText().toString().isEmpty())
                {
                    if(checkTableInput() == true)
                    {
                        Intent tableSessionIntent = new Intent(getApplicationContext(), CustomerTableSession.class);
                        tableSessionIntent.putExtra(RESTARAUNT_ID, restarauntId);
                        tableSessionIntent.putExtra(TABLE_NUMBER, Integer.parseInt(tableSessionBox.getText().toString()));
                        startActivity(tableSessionIntent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please enter a valid table number", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter a valid table number", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private Boolean checkTableInput() {
        dbRef =FirebaseDatabase.getInstance().getReference("Table_Session").child(restarauntId);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    waiterId = postSnapshot.getKey();
                    dbTableRef = FirebaseDatabase.getInstance().getReference("Table_Session").child(restarauntId).child(waiterId);
                    dbTableRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren())
                            {
                                Table tableObject = snapshot.getValue(Table.class);
                                if(tableObject.getTableNumber() == Integer.parseInt(tableSessionBox.getText().toString()))
                                {
                                    tableNumber = tableObject.getTableNumber();
                                    foundTableNumber = true;

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(foundTableNumber)
        {
            return true;
        }
        else{
            return false;
        }
    }


}