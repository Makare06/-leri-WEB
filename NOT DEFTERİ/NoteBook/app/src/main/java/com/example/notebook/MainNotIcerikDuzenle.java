package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.notebook.MainActivity.kullanici;

public class MainNotIcerikDuzenle<V> extends AppCompatActivity {
    String gelenBaslik="",kullanicim="",tutulacakNot="",tutulacakBaslik="";
    EditText baslik,not;
    EditText gonder_text;
    Button guncelle;
    Button sil;
    Button gonder;
    FirebaseDatabase db;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataBaseRef;
    private DatabaseReference mDatabase2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_not_icerik);
         db=FirebaseDatabase.getInstance();
         baslik=findViewById(R.id.editText2);
         not=findViewById(R.id.editText3);
         guncelle=findViewById(R.id.duzenle_kayit);
         sil=findViewById(R.id.not_sil);
         gonder=findViewById(R.id.gonder_button);
         gonder_text=findViewById(R.id.gonder_text);
         mDatabase=FirebaseDatabase.getInstance();
         mDataBaseRef=mDatabase.getReference();

        mDatabase2 = FirebaseDatabase.getInstance().getReference();


        gelenBaslik=getIntent().getStringExtra("baslik");

        kullanicim=kullanici.toString();

        icerik(); //Notun içeriğini getiriyor.


        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotGuncelle(not.getText().toString(),baslik.getText().toString(),kullanicim);
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotSil();
            }
        });
        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VeriGonder(baslik.getText().toString(),not.getText().toString(),gonder_text.getText().toString());
            }
        });



    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MainNotIcerikDuzenle.this, MainMenu.class);
        startActivity(intent);
    }

    public void VeriGonder(String GBaslik,String GNot,String GKullanici){
        if(!GKullanici.equals("")) {
            final DatabaseReference newChildRef = mDatabase2.push();

            String key = newChildRef.getKey();

            NotKayit data = new NotKayit(GBaslik, GNot, GKullanici);
            mDatabase2.child("Notlar").child(key).setValue(data);
            Toast.makeText(getApplicationContext(),"Gönderildi",Toast.LENGTH_SHORT).show();
            gonder_text.setText("");
        }else{
            Toast.makeText(getApplicationContext(),"Kullanıcı Giriniz!",Toast.LENGTH_SHORT).show();
        }
    }

    public void icerik(){

        mDatabase = FirebaseDatabase.getInstance();
        mDataBaseRef = mDatabase.getReference().child("Notlar");

        mDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                   String gelenbaslikim=ds.child("kbaslik").getValue().toString();
                   String gelenkullanicim=ds.child("kkullanici").getValue().toString();
                   if(gelenbaslikim.equals(gelenBaslik.toString()) && gelenkullanicim.equals(kullanicim.toString())){
                       baslik.setText(gelenbaslikim.toString());
                       not.setText(ds.child("knot").getValue().toString());
                       tutulacakNot=not.getText().toString();
                       tutulacakBaslik=baslik.getText().toString();
                   }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

    }


    public void NotGuncelle(final String yeniNot, final String yeniBaslik, final String gelenkullanici){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        myRef.child("Notlar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String gelenkul=snapshot.child("kkullanici").getValue().toString();
                    String gelennot=snapshot.child("knot").getValue().toString();
                    String gelenbaslik=snapshot.child("kbaslik").getValue().toString();
                    if(gelenkul.equals(kullanicim.toString()) && gelennot.equals(tutulacakNot.toString()) && gelenbaslik.equals(tutulacakBaslik.toString())) {
                       String id=snapshot.getKey().toString();
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Notlar").child(id);
                        NotKayit yeniveri = new NotKayit(yeniBaslik.toString(),yeniNot.toString(),gelenkullanici);
                        dR.setValue(yeniveri);
                        Intent intent=new Intent(MainNotIcerikDuzenle.this,MainMenu.class);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }

    public void NotSil(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        myRef.child("Notlar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String gelenkul=snapshot.child("kkullanici").getValue().toString();
                    String gelennot=snapshot.child("knot").getValue().toString();
                    String gelenbaslik=snapshot.child("kbaslik").getValue().toString();
                    if(gelenkul.equals(kullanicim.toString()) && gelennot.equals(tutulacakNot.toString()) && gelenbaslik.equals(tutulacakBaslik.toString())) {
                        String id=snapshot.getKey().toString();
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Notlar").child(id);
                        dR.removeValue();
                        Intent intent=new Intent(MainNotIcerikDuzenle.this,MainMenu.class);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }


    class NotKayit{
        String kbaslik;
        String knot;
        String kkullanici;
        public NotKayit(){

        }
        public NotKayit(String kbaslik,String knot,String kkullanici){
            this.kbaslik=kbaslik;
            this.knot=knot;
            this.kkullanici=kkullanici;
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
        public String getKkullanici(){
            return kkullanici;
        }
        public void setKkullanici(String kkullanici){
            this.kkullanici=kkullanici;
        }
    }


}
