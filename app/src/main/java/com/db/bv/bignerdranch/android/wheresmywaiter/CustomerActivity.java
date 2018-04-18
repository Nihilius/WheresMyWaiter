package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private ListView restarauntList;
    private List<Restaraunt> mRestarauntList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
    }
}
