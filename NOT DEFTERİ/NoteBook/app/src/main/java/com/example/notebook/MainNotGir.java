package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.notebook.MainActivity.kullanici;

public class MainNotGir extends AppCompatActivity {
    EditText Baslik,Icerik;
    Button Not_Kayit;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_not_gir);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Baslik=findViewById(R.id.not_baslik);
        Icerik=findViewById(R.id.not_icerik);
        Not_Kayit=findViewById(R.id.not_kayit);
        final String kullanicim=kullanici.toString();

        Not_Kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference newChildRef = mDatabase.push();

                String key = newChildRef.getKey();

                NotKayit user = new NotKayit(Baslik.getText().toString(), Icerik.getText().toString(),kullanicim.toString());
                mDatabase.child("Notlar").child(key).setValue(user);
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MainNotGir.this, MainMenu.class);
        startActivity(intent);
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
