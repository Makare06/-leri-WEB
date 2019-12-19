package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.notebook.MainActivity.kullanici;

public class MainMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataBaseRef;
    ListView listem;
    adabter_list adapter;
    private ArrayList<String> notlarimBaslik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerview = navigationView.getHeaderView(0);

        TextView kullanici_adi = headerview.findViewById(R.id.kullanici_adi);

        kullanici_adi.setText(kullanici.toString());

        listem = findViewById(R.id.anasayfa_listem);
        notlarimBaslik = new ArrayList<>();

        veriCekme(kullanici);

        listem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemim = (String) parent.getItemAtPosition(position);

                Intent intent=new Intent(MainMenu.this, MainNotIcerikDuzenle.class);
                intent.putExtra("baslik",itemim);

                startActivity(intent);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void veriCekme(final String kullanici){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("Notlar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String gelenkul=snapshot.child("kkullanici").getValue().toString();
                    if(gelenkul.equals(kullanici.toString())) {
                        String baslik = snapshot.child("kbaslik").getValue().toString();
                        notlarimBaslik.add(baslik);
                    }
                }
                ArrayAdapter<String> adapter = new  ArrayAdapter<>(MainMenu.this, android.R.layout.simple_list_item_1,android.R.id.text1,notlarimBaslik);


                listem.setAdapter(adapter);
                notlarimBaslik=new ArrayList<>();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }

    class NotKayit{
        String kbaslik;
        String knot;
        public NotKayit(){

        }
        public NotKayit(String kbaslik,String knot){
            this.kbaslik=kbaslik;
            this.knot=knot;
        }
        public String getKbaslik(){
            return kbaslik;
        }
        public void setKbaslik(String kbaslik){
            this.kbaslik=kbaslik;
        }
        public String getKnot(){
            return knot;
        }
        public void setKsifre(String knot){
            this.knot=knot;
        }
    }
    @Override
    public void onBackPressed() {

    }


}
