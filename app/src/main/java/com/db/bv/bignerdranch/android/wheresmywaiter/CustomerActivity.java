package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private ListView restarauntListView;
    private List<Restaraunt> mRestarauntList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        restarauntListView = (ListView)findViewById(R.id.restarauntsListView);
        mRestarauntList = initializeRestarauntList();
        RestarauntAdapter customAdapter = new RestarauntAdapter(getApplicationContext(),mRestarauntList);
        restarauntListView.setAdapter(customAdapter);

    }

    private List<Restaraunt> initializeRestarauntList(){
        List<Restaraunt> restaraunts = new ArrayList<>();

        restaraunts.add(new Restaraunt("Cheesecake Factory","24265 Cedar Rd","44124","OH"));
        restaraunts.add(new Restaraunt("Bahama Breeze","3900 Orange Pl","44122","OH"));
        restaraunts.add(new Restaraunt("El Patron","301 Center St","44024","OH"));
        restaraunts.add(new Restaraunt("Texas Roadhouse","6095 Commerce Cir","44094","OH"));
        restaraunts.add(new Restaraunt("Red Lobster","7744 Reynolds Rd","44060","OH"));
        restaraunts.add(new Restaraunt("Olive Garden","7740 Mentor Ave","44060","OH"));

        return restaraunts;
    }
}
