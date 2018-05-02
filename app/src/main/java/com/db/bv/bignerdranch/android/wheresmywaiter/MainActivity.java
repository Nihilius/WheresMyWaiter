package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button employeeButton, customerButton;
    private List<Restaraunt> mRestarauntList = new ArrayList<>();
    private List<Restaraunt> mRestaraunts;
    DatabaseReference databaseRestaraunt;

    private static final String RESTARAUNT_ID = "com.db.bv.bignerdranch.android.wheresmywaiter.restarauntid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRestaraunts = new ArrayList<>();
        databaseRestaraunt = FirebaseDatabase.getInstance().getReference("Restaraunt");
        employeeButton = (Button) findViewById(R.id.employee_button);
        customerButton = (Button) findViewById(R.id.customer_button);




        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = "employee";
                showRestaurantListDialog(user);
               // startActivity(new Intent(MainActivity.this, LoginEmployeeActivity.class));
            }
        });

        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = "customer";
                showRestaurantListDialog(user);
                // startActivity(new Intent(MainActivity.this, CustomerActivity.class));
            }
        });




    }



    private void showRestaurantListDialog(final String typeOfUser) {


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.pick_restaurant_dialog, null);
        dialogBuilder.setView(dialogView);
        final ListView restarauntListView = (ListView)dialogView.findViewById(R.id.restarauntsListView);


        restarauntListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if (typeOfUser.equals("customer"))
                {
                    Restaraunt restaraunt = mRestaraunts.get(i);
                    Intent customerIntent = new Intent(getApplicationContext(), CustomerActivity.class);
                    customerIntent.putExtra(RESTARAUNT_ID, restaraunt.getId());
                    startActivity(customerIntent);
                    

                }
                else if (typeOfUser.equals("employee"))
                {
                    Restaraunt restaraunt = mRestaraunts.get(i);
                    Intent waiterIntent = new Intent(getApplicationContext(), LoginEmployeeActivity.class);
                    waiterIntent.putExtra(RESTARAUNT_ID, restaraunt.getId());
                    startActivity(waiterIntent);
                }
            }
        });

        //mRestarauntList = initializeRestarauntList();
       final RestarauntAdapter customAdapter = new RestarauntAdapter(getApplicationContext(),mRestarauntList);
       // restarauntListView.setAdapter(customAdapter);


            //attaching value event listener
        databaseRestaraunt.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //clearing the previous restaraunt list
                    mRestaraunts.clear();

                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        Restaraunt restaraunt = postSnapshot.getValue(Restaraunt.class);
                        //adding artist to the list
                        mRestaraunts.add(restaraunt);
                    }

                    //creating adapter
                    RestaurantList restaraunt = new RestaurantList(MainActivity.this, mRestaraunts);
                    //attaching adapter to the listview
                    restarauntListView.setAdapter(restaraunt);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        dialogBuilder.setTitle("Pick a Restaurant");
        final AlertDialog b = dialogBuilder.create();
        b.show();

    }










    private List<Restaraunt> initializeRestarauntList(){
        List<Restaraunt> restaraunts = new ArrayList<>();

//        restaraunts.add(new Restaraunt("Cheesecake Factory","24265 Cedar Rd","44124","OH"));
//        restaraunts.add(new Restaraunt("Bahama Breeze","3900 Orange Pl","44122","OH"));
//        restaraunts.add(new Restaraunt("El Patron","301 Center St","44024","OH"));
//        restaraunts.add(new Restaraunt("Texas Roadhouse","6095 Commerce Cir","44094","OH"));
//        restaraunts.add(new Restaraunt("Red Lobster","7744 Reynolds Rd","44060","OH"));
//        restaraunts.add(new Restaraunt("Olive Garden","7740 Mentor Ave","44060","OH"));



        return restaraunts;
    }
















}
