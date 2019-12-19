package com.example.notebook.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notebook.MainNotGir;
import com.example.notebook.R;
import com.google.firebase.database.DatabaseReference;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    EditText Baslik;
    EditText Not;
    Button Kayit;
    private DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        TextView kullanici_adi = root.findViewById(R.id.kullanici_adi);

        Intent intent=new Intent(getContext(), MainNotGir.class);
        //intent.putExtra("kullanici",kullanici_adi.getText());
        startActivity(intent);
        /*mDatabase = FirebaseDatabase.getInstance().getReference();
        Baslik=root.findViewById(R.id.baslik);
        Not=root.findViewById(R.id.not);
        Kayit=root.findViewById(R.id.kaydet);

        Kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference newChildRef = mDatabase.push();

                String key = newChildRef.getKey();

                NotKayit user = new NotKayit(Baslik.getText().toString(), Not.getText().toString());
                mDatabase.child("Notlar").child(key).setValue(user);

               // Toast.makeText(getApplicationContext(), "Kayıt Edildi", Toast.LENGTH_SHORT).show();
            }
        });

*/

        return root;
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
}