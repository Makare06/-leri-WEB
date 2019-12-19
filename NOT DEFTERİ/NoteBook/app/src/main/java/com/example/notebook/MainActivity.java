package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mail, sifre;
    Button giris_yap, kayit_ol;
    public static String kullanici="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = (EditText) findViewById(R.id.mail);
        sifre = (EditText) findViewById(R.id.sifre);
        giris_yap = (Button) findViewById(R.id.giris_yap);
        kayit_ol = (Button) findViewById(R.id.kayit_ol);
        mAuth = FirebaseAuth.getInstance();


        giris_yap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = mail.getText().toString().trim();
                final String pass = sifre.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                                    kullanici=email.toString();
                                    startActivity(intent);
                                }
                                else {

                                    Toast.makeText(MainActivity.this,"Bilgilerinizi Kontrol Ediniz.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        });
        kayit_ol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kayit=new Intent(getApplicationContext(),MainKayit.class);
                startActivity(kayit);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}
