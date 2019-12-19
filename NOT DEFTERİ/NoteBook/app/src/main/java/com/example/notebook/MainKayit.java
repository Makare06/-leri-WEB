package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainKayit extends AppCompatActivity {

    EditText mail2,sifre2;
    Button kayit,geri;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override

    protected void onCreate(Bundle savedInstanceState){

       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_kayit);

       mail2=(EditText)findViewById(R.id.mail2);
       sifre2=(EditText)findViewById(R.id.sifre2);
       kayit=(Button)findViewById(R.id.kayit);
       geri=(Button)findViewById(R.id.geri);
       mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

       kayit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               final String email = mail2.getText().toString().trim();
               final String pass = sifre2.getText().toString().trim();

               mAuth.createUserWithEmailAndPassword(email, pass)
                       .addOnCompleteListener(MainKayit.this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {
                                   final DatabaseReference newChildRef = mDatabase.push();

                                   String key = newChildRef.getKey();

                                   User user = new User(email, pass);
                                   mDatabase.child("Kullanicilar").child(key).setValue(user);

                                   Toast.makeText(MainKayit.this, "Kullanıcı Kaydı Başarılı", Toast.LENGTH_LONG).show();
                                   Intent yon = new Intent(getApplicationContext(),MainActivity.class);

                                   startActivity(yon);

                               } else {

                                   Toast.makeText(MainKayit.this, "Bilgilerinizi Kontrol Ediniz.", Toast.LENGTH_LONG).show();
                               }
                           }

                       });
           }
       });
       geri.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent geri=new Intent(getApplicationContext(),MainActivity.class);
               startActivity(geri);
           }
       });
    }

    class User{
        String kadi;
        String ksifre;

        public User(){

        }
        public User(String kAdi,String kSifre){
            this.kadi=kAdi;
            this.ksifre=kSifre;
        }
        public String getKadi(){
            return kadi;
        }
        public void setKadi(String kadi){
            this.kadi=kadi;
        }
        public String getKsifre(){
            return ksifre;
        }
        public void setKsifre(String ksifre){
            this.ksifre=ksifre;
        }
    }
}