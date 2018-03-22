package com.example.prem.pgapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPG extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    RecyclerView recyclerView;
    PGAdapter pgAdapter;
    private ImageView imageViewCust;
    private NavigationView navigationView;
    private TextView textViewEmail,textViewName;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    public String user;
    List<com.example.prem.pgapp.PGs> pGsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pg);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        user=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        Toast.makeText(this,user,Toast.LENGTH_SHORT).show();
        View header=navigationView.getHeaderView(0);
        imageViewCust=(ImageView)findViewById(R.id.imageViewCust);
        //imageViewCust.setImageBitmap();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        textViewEmail=(TextView)header.findViewById(R.id.textViewEmail1);
        textViewName=(TextView)header.findViewById(R.id.textViewName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        pGsList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.RecyclerViewCust);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pGsList.add(
                new com.example.prem.pgapp.PGs(
                1,
                "Durga Boys PG",
                "Civil Lines,Allahabad",
                5.5,
                6000,
                R.drawable.pg1));

        pGsList.add(
                new com.example.prem.pgapp.PGs(
                        1,
                        "Dreamsquare Girls PG",
                        "Katra,Allahabad",
                        4.3,
                        4000,
                        R.drawable.pg1));

        pGsList.add(
                new com.example.prem.pgapp.PGs(
                        1,
                        "Homestay Boys PG",
                        "Rambagh,Allahabad",
                        4.3,
                        60000,
                        R.drawable.pg1));
        pGsList.add(
                new com.example.prem.pgapp.PGs(
                        1,
                        "Nestaway Boys PG",
                        "Govindpur,Allahabad",
                        4.3,
                        60000,
                        R.drawable.pg1));
        pGsList.add(
                new com.example.prem.pgapp.PGs(
                        1,
                        "Sector 24 Boys PG",
                        "Teliarganj,Allahabad",
                        4.3,
                        60000,
                        R.drawable.pg1));
        pGsList.add(
                new com.example.prem.pgapp.PGs(
                        1,
                        "Sector 18 Boys PG",
                        "Civil Lines,Allahabad",
                        4.3,
                        60000,
                        R.drawable.pg1));
        pGsList.add(
                new com.example.prem.pgapp.PGs(
                        1,
                        "Sector 22 Boys PG",
                        "Mutthiganj,Allahabad",
                        4.3,
                        60000,
                        R.drawable.pg1));
        pGsList.add(
                new com.example.prem.pgapp.PGs(
                        1,
                        "Saket Boys PG",
                        "Teliarganj,Allahabad",
                        4.3,
                        60000,
                        R.drawable.pg1));

        //creating recyclerview adapter
        PGAdapter adapter = new PGAdapter(this, pGsList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds:dataSnapshot.getChildren())
        {
            CustomerInformation customerInformation=new CustomerInformation();
            customerInformation.setEmail(ds.child(user).getValue(CustomerInformation.class).getEmail());
            customerInformation.setName(ds.child(user).getValue(CustomerInformation.class).getName());
            textViewEmail.setText(customerInformation.getEmail());
            textViewName.setText(customerInformation.getName());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_pg, menu);
        menu.findItem(R.id.buttonAdd).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.buttonAdd) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_boys) {


        } else if (id == R.id.nav_girls) {

        } else if (id == R.id.nav_logout) {
            finish();
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(new Intent(intent));
            FirebaseAuth.getInstance().signOut();

        }/* else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}
