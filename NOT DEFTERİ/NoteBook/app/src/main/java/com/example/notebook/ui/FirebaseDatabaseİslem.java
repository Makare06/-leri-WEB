package com.example.notebook.ui;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseİslem {

   private FirebaseDatabase mDatabase;
   private DatabaseReference mRefence;

   public interface DataStatus{

       void DataUpdate();
       void DataDelete();
   }
   public FirebaseDatabaseİslem(){

       mDatabase=FirebaseDatabase.getInstance();
       mRefence=mDatabase.getReference("Notlar");

   }
   public void Notlar(final DataStatus dataStatus){

       mRefence.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               List<String> keys=new ArrayList<>();
               for (DataSnapshot keyNode: dataSnapshot.getChildren()){



               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

   }

}
