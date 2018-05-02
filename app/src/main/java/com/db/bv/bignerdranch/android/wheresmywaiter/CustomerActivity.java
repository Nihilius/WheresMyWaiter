package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

  private EditText tableSessionBox;
  private Button startSessionButton;


  private static final String RESTARAUNT_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.restarauntid";
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
                Intent tableSessionIntent = new Intent(getApplicationContext(), CustomerTableSession.class);
                tableSessionIntent.putExtra(RESTARAUNT_ID, restarauntId);
                startActivity(tableSessionIntent);
            }
        });


        //enter code and connect to table session






    }


}